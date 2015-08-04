package com.rewufu.superlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rewufu.superlist.adapter.SideListAdapter;
import com.rewufu.superlist.dao.ListDao;
import com.rewufu.superlist.entities.Side_List_Item;
import com.rewufu.superlist.fragments.ContentFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ListView mSideList = (ListView) findViewById(R.id.sideList);
        mToolbar.setTitle("My Lists");
        mToolbar.setLogo(R.mipmap.ic_toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        contentFragment = new ContentFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentLayout, contentFragment).commit();
        List<Side_List_Item> side_list = new ArrayList<>();
        side_list.add(new Side_List_Item(R.mipmap.ic_side_list, " My Lists"));
        side_list.add(new Side_List_Item(R.mipmap.ic_side_settings, " Settings"));
        ArrayAdapter adapter = new SideListAdapter(this, R.layout.side_list_item, side_list);
        mSideList.setAdapter(adapter);
        mSideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add) {
            final EditText text = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Input list name")
                    .setView(text)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ArrayList<String> lists = new ListDao(getApplicationContext()).queryLists();
                            if (TextUtils.equals(text.getText().toString().trim(), "") ||
                                    (lists != null && lists.contains(text.getText().toString()))) {
                                //keep dialog always on
                                try {
                                    Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialogInterface, false);
                                } catch (Exception e) {
                                    Log.d("", e.getMessage());
                                }
                                Toast.makeText(getApplicationContext(), "Input is empty or this is already used.", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                                    field.setAccessible(true);
                                    field.set(dialogInterface, true);
                                } catch (Exception e) {
                                    Log.d("", e.getMessage());
                                }
                                new ListDao(getApplicationContext()).insertLists(text.getText().toString().trim());
                                contentFragment = new ContentFragment();
                                fragmentManager.beginTransaction().replace(R.id.contentLayout, contentFragment).commit();
                                Toast.makeText(getApplicationContext(), "Add success.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close dialog
                    try {
                        Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");
                        field.setAccessible(true);
                        field.set(dialogInterface, true);
                    } catch (Exception e) {
                        Log.d("", e.getMessage());
                    }
                }
            })
                    .setCancelable(false)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

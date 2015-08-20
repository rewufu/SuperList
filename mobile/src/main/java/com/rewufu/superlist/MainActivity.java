package com.rewufu.superlist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.EditText;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rewufu.superlist.dao.ListDao;
import com.rewufu.superlist.fragments.ContentFragment;
import com.rewufu.superlist.fragments.Settings_Fragment;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private Fragment contentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        initView();
    }

    private void initView() {
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar.setTitle("My Lists");
        mToolbar.setLogo(R.mipmap.ic_toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.drawer_mylists:
                                fragmentManager.beginTransaction().replace(R.id.contentLayout, contentFragment).commit();
                                return true;
                            case R.id.drawer_settings:
                                //settings
                                mToolbar.setTitle("Settings");
                                fragmentManager.beginTransaction().replace(R.id.contentLayout, new Settings_Fragment()).commit();
                                return true;
                            case R.id.drawer_about:
                                //show info in a dialog
                                return true;
                        }
                        return false;
                    }
                }
        );

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
                R.string.close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        contentFragment = new ContentFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentLayout, contentFragment).commit();
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

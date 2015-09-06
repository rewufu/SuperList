package com.rewufu.superlist;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rewufu.superlist.dao.ListDao;
import com.rewufu.superlist.dao.ListItemDao;
import com.rewufu.superlist.entity.Goods;
import com.rewufu.superlist.fragments.ContentFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String LIST_PATH = "/list";
    private static final String LIST_KEY = "LIST_KEY";

    private FragmentManager fragmentManager;
    private Fragment contentFragment;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        initView();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

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
                            case R.id.drawer_sync:
                                if (mGoogleApiClient.isConnected() && (null != new ListDao(getApplicationContext()).queryLists())) {
                                    PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(LIST_PATH);
                                    String json = getJson();
                                    putDataMapRequest.getDataMap().putString(LIST_KEY, json);
                                    PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();
                                    PendingResult<DataApi.DataItemResult> pendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest);
                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.sync_failed), Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.drawer_about:
                                ContentDialog about = ContentDialog.newInstance("About", getString(R.string.about_content));
                                about.show(getSupportFragmentManager(), "about");
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

    private String getJson(){
        Gson gson = new Gson();
        List<Goods> goodsList = new ListItemDao(this).queryAll();
        String json = gson.toJson(goodsList);
        return json;
    }

    private void sendNotification() {
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_list)
                .setContentTitle("Hello SuperList")
                .setContentText("List send to wearable.")
                .extend(wearableExtender)
                .build();
        NotificationManagerCompat notificationManagerCompat =
                NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


}

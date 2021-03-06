package com.rewufu.superlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rewufu.superlist.Adapter.Adapter;
import com.rewufu.superlist.dao.ListItemDao;
import com.rewufu.superlist.entity.Goods;
import com.rewufu.superlist.interfaces.MyItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, DataApi.DataListener, MyItemClickListener {

    private static final String LIST_PATH = "/list";
    private static final String LIST_KEY = "LIST_KEY";

    private Adapter adapter;

    ArrayList<String> elements;
    private ListItemDao itemDao;
    GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        itemDao = new ListItemDao(this);
        elements = itemDao.queryList();
        adapter = new Adapter(this, elements);
        adapter.setOnItemClickListener(this);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Wearable.DataApi.addListener(googleApiClient, this);
        Log.i("google", "onConnected");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.i("google", "onDataChanged");
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo(LIST_PATH) == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    final String json = dataMap.getString(LIST_KEY);
                    Log.i("google", json);
                    Gson gson = new Gson();
                    List<Goods> goodsList = gson.fromJson(json, new TypeToken<List<Goods>>() {
                    }.getType());
                    for (int i = 0; i < goodsList.size(); i++) {
                        Goods goods = goodsList.get(i);
                        if (itemDao.queryGoods().contains(goods.getName())
                                && itemDao.queryListByName(goods.getName()).contains(goods.getListName())){
                                itemDao.updateItemBought(goods.getBought(), goods.getName(), goods.getListName());
                            Log.i("google", goods.getName() + " update bought");
                        } else {
                            itemDao.insertListItem(goodsList.get(i));
                            Log.i("google", goods.getName() + " insert to database");
                        }
                    }
                    elements.clear();
                    elements = itemDao.queryList();
                    adapter.updateData(elements);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("google", elements.get(position) + " clicked");
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra("listName", elements.get(position));
        startActivity(intent);
    }
}

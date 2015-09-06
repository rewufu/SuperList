package com.rewufu.superlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rewufu.superlist.Adapter.GoodsAdapter;
import com.rewufu.superlist.dao.ListItemDao;
import com.rewufu.superlist.entity.Goods;
import com.rewufu.superlist.interfaces.MyItemClickListener;

import java.util.ArrayList;

public class DetailActivity extends Activity implements MyItemClickListener {

    private ArrayList<Goods> goodsList;
    private ListItemDao listItemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
        Intent intent = getIntent();
        String listName = intent.getStringExtra("listName");
        listItemDao = new ListItemDao(this);
        goodsList = listItemDao.queryItemByList(listName);
        WearableListView listView = (WearableListView) findViewById(R.id.wearable_list);
        GoodsAdapter goodsAdapter = new GoodsAdapter(this, goodsList);
        goodsAdapter.setOnItemClickListener(this);
        listView.setAdapter(goodsAdapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Log.i("DetailActivity", goodsList.get(position).getName());
        TextView textView = (TextView) view.findViewById(R.id.detail_item_name);
        if (TextUtils.equals("true", goodsList.get(position).getBought())) {
            listItemDao.updateItemBought("false", goodsList.get(position).getName(), goodsList.get(position).getListName());
            goodsList.get(position).setBought("false");
            textView.setText(goodsList.get(position).getName());
            textView.getPaint().setFlags(0);
            textView.getPaint().setAntiAlias(true);
        } else {
            listItemDao.updateItemBought("true", goodsList.get(position).getName(), goodsList.get(position).getListName());
            goodsList.get(position).setBought("true");
            textView.setText(goodsList.get(position).getName());
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);
        }
    }
}

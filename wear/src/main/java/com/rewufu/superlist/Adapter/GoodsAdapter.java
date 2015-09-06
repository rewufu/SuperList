package com.rewufu.superlist.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.wearable.view.WearableListView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.rewufu.superlist.R;
import com.rewufu.superlist.entity.Goods;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;
import com.rewufu.superlist.viewholder.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by Bell on 9/5/15.
 */

public class GoodsAdapter extends WearableListView.Adapter {
    private ArrayList<Goods> mDataSet;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;


    public GoodsAdapter(Context context, ArrayList<Goods> dataSet) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }


    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, myItemClickListener, myItemLongClickListener);
        return myViewHolder;
    }

    // Replace the contents of a list item
    // Instead of creating new views, the list tries to recycle existing ones
    // (invoked by the WearableListView's layout manager)
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        MyViewHolder itemHolder = (MyViewHolder) holder;
        TextView textView = itemHolder.name;
        ImageView imageView = itemHolder.image;
        textView.setText(mDataSet.get(position).getName());
        holder.itemView.setTag(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.ASSETS.wrap("goods/" + mDataSet.get(position).getName() + ".jpg"), imageView, options);
        if(TextUtils.equals("true", mDataSet.get(position).getBought())){
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);
        }
    }

    // Return the size of your dataSet
    // (invoked by the WearableListView's layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener) {
        this.myItemLongClickListener = listener;
    }
}

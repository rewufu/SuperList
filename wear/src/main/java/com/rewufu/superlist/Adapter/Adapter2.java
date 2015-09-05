package com.rewufu.superlist.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.rewufu.superlist.R;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;
import com.rewufu.superlist.viewholder.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by Bell on 9/5/15.
 */

public class Adapter2 extends WearableListView.Adapter {
    private ArrayList<String> mDataSet;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;


    public Adapter2(Context context, ArrayList<String> dataSet) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }

    public void updateData(ArrayList<String> newData){
        mDataSet = newData;
    }

    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, myItemClickListener, myItemLongClickListener);
        return myViewHolder;
    }

    // Replace the contents of a list item
    // Instead of creating new views, the list tries to recycle existing ones
    // (invoked by the WearableListView's layout manager)
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        // retrieve the text view
        MyViewHolder itemHolder = (MyViewHolder) holder;
        TextView view = itemHolder.name;
        view.setText(mDataSet.get(position));
        holder.itemView.setTag(position);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.ASSETS.wrap("goods/" + mDataSet.get(position) + ".jpg"), itemHolder.image, options);
    }

    // Return the size of your dataSet
    // (invoked by the WearableListView's layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}

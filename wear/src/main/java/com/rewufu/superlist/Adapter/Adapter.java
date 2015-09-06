package com.rewufu.superlist.Adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rewufu.superlist.R;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by Bell on 9/1/15.
 */
public class Adapter extends WearableListView.Adapter {
    private ArrayList<String> mDataSet;
    private final Context mContext;
    private final LayoutInflater mInflater;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    // Provide a suitable constructor (depends on the kind of dataSet)
    public Adapter(Context context, ArrayList<String> dataSet) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }

    public void updateData(ArrayList<String> newData){
        mDataSet = newData;
    }

    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        private TextView textView;
        private MyItemClickListener myItemClickListener;
        private MyItemLongClickListener myItemLongClickListener;
        public ItemViewHolder(View itemView, MyItemClickListener itemClickListener, MyItemLongClickListener itemLongClickListener) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.detail_item_name);
            myItemClickListener = itemClickListener;
            myItemLongClickListener = itemLongClickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(myItemClickListener != null){
                myItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(myItemLongClickListener != null){
                myItemLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    }

    // Create new views for list items
    // (invoked by the WearableListView's layout manager)
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // Inflate our custom layout for list items
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null), myItemClickListener, myItemLongClickListener);
    }

    // Replace the contents of a list item
    // Instead of creating new views, the list tries to recycle existing ones
    // (invoked by the WearableListView's layout manager)
    @Override
    public void onBindViewHolder(WearableListView.ViewHolder holder,
                                 int position) {
        // retrieve the text view
        ItemViewHolder itemHolder = (ItemViewHolder) holder;
        TextView view = itemHolder.textView;
        // replace text contents
        view.setText(mDataSet.get(position));
        // replace list item's metadata
        holder.itemView.setTag(position);
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

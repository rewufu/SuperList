package com.rewufu.superlist.Adapter;

import android.content.Context;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rewufu.superlist.R;

import java.util.ArrayList;

/**
 * Created by Bell on 9/1/15.
 */
public class Adapter extends WearableListView.Adapter {
    private ArrayList<String> mDataSet;
    private final Context mContext;
    private final LayoutInflater mInflater;

    // Provide a suitable constructor (depends on the kind of dataSet)
    public Adapter(Context context, ArrayList<String> dataSet) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDataSet = dataSet;
    }

    // Provide a reference to the type of views you're using
    public static class ItemViewHolder extends WearableListView.ViewHolder {
        private TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            // find the text view within the custom item's layout
            textView = (TextView) itemView.findViewById(R.id.name);
        }
    }

    // Create new views for list items
    // (invoked by the WearableListView's layout manager)
    @Override
    public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // Inflate our custom layout for list items
        return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
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
}

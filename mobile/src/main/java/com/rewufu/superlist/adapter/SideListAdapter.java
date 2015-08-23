package com.rewufu.superlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rewufu.superlist.R;
import com.rewufu.superlist.entity.Side_List_Item;

import java.util.List;

/**
 * Created by Bell on 7/14/15.
 */
public class SideListAdapter extends ArrayAdapter<Side_List_Item> {
    private int resourceId;
    public SideListAdapter(Context context, int resource, List<Side_List_Item> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Side_List_Item item = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ((ImageView)view.findViewById(R.id.list_item_icon)).setImageResource(item.getIcon());
        ((TextView)view.findViewById(R.id.list_item_text)).setText(item.getName());
        return view;
    }
}

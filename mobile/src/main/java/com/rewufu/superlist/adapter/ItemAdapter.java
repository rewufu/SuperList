package com.rewufu.superlist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.rewufu.superlist.R;
import com.rewufu.superlist.dao.ListItemDao;

import java.util.ArrayList;

/**
 * Created by Bell on 8/12/15.
 */
public class ItemAdapter extends ArrayAdapter<String> {
    private Context context;
    private String list;
    private int resource;
    private int clickTemp = -1;
    private int unClickTemp = -1;

    public ItemAdapter(Context context, int resource, String list) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    public void setClickTemp(int position){
        clickTemp = position;
    }
    public void setUnClickTemp(int position){
        unClickTemp = position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resource, null);
        ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);
        TextView itemText = (TextView) view.findViewById(R.id.itemText);
        itemText.setText(item);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.ASSETS.wrap("goods/" + item + ".jpg"), itemImage, options);
        ArrayList<String> itemList = new ListItemDao(context).queryItemByList(list);
        if((itemList != null) &&(itemList.contains(item)) || clickTemp == position){
            itemText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            itemText.getPaint().setAntiAlias(true);
            itemImage.setAlpha(0.5f);
            clickTemp = -1;
        }
        if(unClickTemp == position){
            itemText.getPaint().setFlags(0);
            itemImage.setAlpha(1f);
            unClickTemp = -1;
        }
        return view;
    }
}

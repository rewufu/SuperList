package com.rewufu.superlist.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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

/**
 * Created by Bell on 8/12/15.
 */
public class ItemAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resourse;
    public ItemAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resourse = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resourse, null);
        ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);
        TextView itemText = (TextView) view.findViewById(R.id.itemText);
        itemText.setText(item);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.ASSETS.wrap("goods/" + item + ".jpg"), itemImage, options);
        return view;
    }
}

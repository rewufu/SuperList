package com.rewufu.superlist.adapter;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.rewufu.superlist.R;
import com.rewufu.superlist.entity.ListItem;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;
import com.rewufu.superlist.viewholder.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by Bell on 7/30/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<ListItem> items;
    private ArrayList<String> lists;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;
    private int type;

    public RecyclerAdapter(ArrayList<ListItem> list, int type) {
        this.items = list;
        this.type = type;
    }

    public RecyclerAdapter(ArrayList<String> list) {
        lists = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, myItemClickListener, myItemLongClickListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (1 == type) {
            holder.title.setText(items.get(position).getName());
            if (items.get(position).isBought()) {
                holder.title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.title.getPaint().setAntiAlias(true);
            }
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.ASSETS.wrap("goods/" + items.get(position).getName() + ".jpg"), holder.image, options);
        } else {
            holder.title.setText(lists.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (1 == type) {
            return items.size();
        }
        return lists.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener) {
        this.myItemLongClickListener = listener;
    }

}

package com.rewufu.superlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rewufu.superlist.R;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;
import com.rewufu.superlist.viewholder.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by Bell on 7/30/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<String> teams;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public RecyclerAdapter(ArrayList<String> list) {
        teams = list;
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
        holder.title.setText(teams.get(position));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.myItemClickListener = listener;
    }
    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.myItemLongClickListener = listener;
    }



}

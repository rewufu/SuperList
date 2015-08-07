package com.rewufu.superlist.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rewufu.superlist.R;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;

/**
 * Created by Bell on 8/3/15.
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public TextView title;
    private MyItemClickListener myItemClickListener;
    private MyItemLongClickListener myItemLongClickListener;

    public MyViewHolder(View itemView, MyItemClickListener itemClickListener, MyItemLongClickListener itemLongClickListener) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_team_title);
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

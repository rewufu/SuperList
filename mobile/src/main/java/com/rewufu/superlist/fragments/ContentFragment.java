package com.rewufu.superlist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rewufu.superlist.R;
import com.rewufu.superlist.adapter.RecyclerAdapter;
import com.rewufu.superlist.dao.ListDao;
import com.rewufu.superlist.interfaces.MyItemClickListener;
import com.rewufu.superlist.interfaces.MyItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by Bell on 7/14/15.
 */
public class ContentFragment extends Fragment  implements MyItemClickListener, MyItemLongClickListener {
    private ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list = new ListDao(getActivity()).queryLists();
        if (list == null) {
            //show empty info
            View view = inflater.inflate(R.layout.empty_content, container, false);
            return view;
        }
        //show lists
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list);
        recyclerAdapter.setOnItemClickListener(this);
        recyclerAdapter.setOnItemLongClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), list.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(getActivity(), list.get(position), Toast.LENGTH_SHORT).show();
    }
}

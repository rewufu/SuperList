package com.rewufu.superlist.fragments;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rewufu.superlist.R;
import com.rewufu.superlist.adapter.RecyclerAdapter;
import com.rewufu.superlist.dao.ListItemDao;
import com.rewufu.superlist.entity.ListItem;
import com.rewufu.superlist.interfaces.MyItemClickListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragmentList extends Fragment implements MyItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private String listName;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<ListItem> list;
    private ListItemDao listItemDao;

    public static DetailFragmentList newInstance(String list) {
        DetailFragmentList fragment = new DetailFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, list);
        fragment.setArguments(args);
        return fragment;
    }


    public DetailFragmentList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listName = getArguments().getString(ARG_PARAM1);
            listItemDao = new ListItemDao(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // when list is empty
        list = listItemDao.queryItemByList(listName);
        if (list == null) {
            return inflater.inflate(R.layout.empty_list, container, false);
        }
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(list, 1);
        recyclerAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        TextView textView = (TextView) view.findViewById(R.id.item_name);
        if (listItemDao.queryItemByList(listName).get(position).isBought()) {
            listItemDao.updateItemBought("f", list.get(position).getName(), listName);
            textView.setText(list.get(position).getName());
            textView.getPaint().setFlags(0);
            textView.getPaint().setAntiAlias(true);
        } else {
            listItemDao.updateItemBought("t", list.get(position).getName(), listName);
            textView.setText(list.get(position).getName());
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);
        }
    }
}

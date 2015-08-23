package com.rewufu.superlist.fragments;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rewufu.superlist.DetailActivity;
import com.rewufu.superlist.R;
import com.rewufu.superlist.adapter.ItemAdapter;
import com.rewufu.superlist.dao.GoodsDao;
import com.rewufu.superlist.dao.ListItemDao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragmentItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragmentItem extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ItemAdapter adapter;
    private ItemAdapter secondAdapter;
    private List<String> secondList;
    private GridView gridView;
    private String listName;
    private ListItemDao listItemDao;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DetailFragmentItem.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragmentItem newInstance(String param1) {
        DetailFragmentItem fragment = new DetailFragmentItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragmentItem() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new ItemAdapter(getActivity(), R.layout.grid_item, listName);
        final List<String> list = new GoodsDao(getActivity()).queryKinds();
        adapter.addAll(list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(gridView.getAdapter() == adapter){
                    secondAdapter = new ItemAdapter(getActivity(), R.layout.grid_item, listName);
                    secondList = new GoodsDao(getActivity()).queryGoodsByKind(list.get(position));
                    secondAdapter.addAll(secondList);
                    gridView.setAdapter(secondAdapter);
                }else {
                    ArrayList<String> itemList = listItemDao.queryNameByList(listName);
                    if(itemList != null && itemList.contains(secondList.get(position))){
                        listItemDao.deleteItem(secondList.get(position));
                        View view1 = gridView.getChildAt(position - gridView.getFirstVisiblePosition());
                        ImageView itemImage = (ImageView) view1.findViewById(R.id.itemImage);
                        TextView itemText = (TextView) view1.findViewById(R.id.itemText);
                        itemText.setText(secondList.get(position));
                        itemText.getPaint().setFlags(0);
                        itemText.getPaint().setAntiAlias(true);
                        itemImage.setAlpha(1f);
                        DetailActivity.change();
                    }else {
                        listItemDao.insertListItem(secondList.get(position), listName);
                        View view1 = gridView.getChildAt(position - gridView.getFirstVisiblePosition());
                        ImageView itemImage = (ImageView) view1.findViewById(R.id.itemImage);
                        TextView itemText = (TextView) view1.findViewById(R.id.itemText);
                        itemText.setText(secondList.get(position));
                        itemText.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        itemText.getPaint().setAntiAlias(true);
                        itemImage.setAlpha(0.5f);
                        DetailActivity.change();
                    }
                }
            }
        });
        return view;
    }

    public void onBackKeyDown(){
        if(gridView.getAdapter() != adapter){
            gridView.setAdapter(adapter);
        }else {
            getActivity().finish();
        }
    }

}

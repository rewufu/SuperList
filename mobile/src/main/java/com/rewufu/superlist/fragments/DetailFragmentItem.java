package com.rewufu.superlist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.rewufu.superlist.R;
import com.rewufu.superlist.adapter.ItemAdapter;
import com.rewufu.superlist.dao.GoodsDao;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragmentItem#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragmentItem extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ItemAdapter adapter;
    private GridView gridView;

    // TODO: Rename and change types of parameters
    private String listName;


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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_item, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        adapter = new ItemAdapter(getActivity(), R.layout.grid_item);
        final List<String> list = new GoodsDao(getActivity()).queryKinds();
        adapter.addAll(list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemAdapter mAdapter = new ItemAdapter(getActivity(), R.layout.grid_item);
                List<String> mList = new GoodsDao(getActivity()).queryGoodsByKind(list.get(position));
                mAdapter.addAll(mList);
                gridView.setAdapter(mAdapter);
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

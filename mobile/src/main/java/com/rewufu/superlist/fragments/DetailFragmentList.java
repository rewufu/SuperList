package com.rewufu.superlist.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rewufu.superlist.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragmentList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String listName;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragmentList.
     */
    // TODO: Rename and change types and number of parameters
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_list, container, false);
    }


}

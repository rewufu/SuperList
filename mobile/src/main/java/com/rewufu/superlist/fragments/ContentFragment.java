package com.rewufu.superlist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rewufu.superlist.R;
import com.rewufu.superlist.dao.ListDao;

/**
 * Created by Bell on 7/14/15.
 */
public class ContentFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (new ListDao(getActivity()).queryLists() == null) {
            //show empty info
            View view = inflater.inflate(R.layout.empty_content, container, false);
            return view;
        }
        //show lists
        //View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        //init data

        return null;
    }
}

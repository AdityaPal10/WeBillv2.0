package com.teamblue.WeBillv2.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamblue.WeBillv2.R;


/**
 * spending fragments subclass.
 */
public class SpendingActivityFragment extends Fragment {

    public SpendingActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spending_activity, container, false);
    }
}
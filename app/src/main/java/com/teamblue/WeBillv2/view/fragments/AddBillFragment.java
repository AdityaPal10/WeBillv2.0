package com.teamblue.WeBillv2.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamblue.WeBillv2.R;


/**
 *  addbill fragment subclass.
 */
public class AddBillFragment extends Fragment {

    public AddBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_bill, container, false);
    }
}
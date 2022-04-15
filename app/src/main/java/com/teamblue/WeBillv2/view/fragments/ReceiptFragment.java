package com.teamblue.WeBillv2.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;

import java.lang.reflect.Array;


/**
 * receipts fragment subclass.
 */
public class ReceiptFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private Button btnFilterMonth;
    private Spinner spinnerMonths;
    Dialog myDialog;


    public ReceiptFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        myDialog = new Dialog(this.getContext());
        myDialog.setContentView(R.layout.popup_receipt_filter);

        btnFilterMonth = (Button) view.findViewById(R.id.btnFilterMonth);
        btnFilterMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.show();
            }
        });
        //Basic settings for spinner inside a Fragment
        spinnerMonths = (Spinner) myDialog.findViewById(R.id.spinnerMonths);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.months,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapter);
        spinnerMonths.setOnItemSelectedListener(this);




        return view;
    }

    // Do spinner's functions here, now a demo of showing Toast text when select a month
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textMonth = spinnerMonths.getSelectedItem().toString();
        Toast.makeText(this.getContext(), textMonth, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}

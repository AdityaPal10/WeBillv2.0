package com.teamblue.WeBillv2.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;

import java.lang.reflect.Array;


/**
 * receipts fragment subclass.
 */
public class ReceiptFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private Button btnFilterMonth,btnAddCard;
    private Spinner spinnerMonths;
    private Dialog filterDialog;
    private Dialog addReceiptDialog;

    LinearLayout receiptContainer;


    public ReceiptFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

        //1 handling filter button and its pop up window
        filterDialog = new Dialog(this.getContext());
        filterDialog.setContentView(R.layout.popup_receipt_filter);
        btnFilterMonth = (Button) view.findViewById(R.id.btnFilterMonth);
        btnFilterMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.show();
            }
        });

        //2 Basic settings for spinner inside a Fragment
        spinnerMonths = (Spinner) filterDialog.findViewById(R.id.spinnerMonths);// since spinner is inside myDialog, don't use view.getContext()
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.months,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapter);
        spinnerMonths.setOnItemSelectedListener(this);

        //3 handling the receiptContainer
        btnAddCard = (Button) view.findViewById(R.id.btnAddCard);
        receiptContainer = (LinearLayout) view.findViewById(R.id.receiptContainer);
        buildDialog();
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReceiptDialog.show();
            }
        });

        return view;
    }

    //2.1 Do spinner's functions here, now a demo of showing Toast text when select a month
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textMonth = spinnerMonths.getSelectedItem().toString();
        Toast.makeText(this.getContext(), textMonth, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //3.1 handling the addReceiptDialog function
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.popup_receipt_bill_info, null);

        final EditText edtBillAmount = view.findViewById(R.id.edtBillAmount);
        final EditText edtActivityName = view.findViewById(R.id.edtActivityName);
        final EditText edtDate = view.findViewById(R.id.edtDate);

        builder.setView(view);
        builder.setTitle("Enter Bills")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(edtBillAmount.getText().toString(),
                                edtActivityName.getText().toString(),
                                edtDate.getText().toString());

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        addReceiptDialog = builder.create();
    }
    //3.2 once finished
    private void addCard(String BillAmount, String ActivityName, String Date) {
        final View view = getLayoutInflater().inflate(R.layout.cardview_receipt, null);

        TextView tvReceiptAmount = view.findViewById(R.id.tvReceiptAmount);
        TextView tvActivityName = view.findViewById(R.id.tvActivityName);
        TextView tvReceiptDate = view.findViewById(R.id.tvReceiptDate);
        Button btnRemoveCard = view.findViewById(R.id.btnRemoveCard);

        tvReceiptAmount.setText(BillAmount);
        tvActivityName.setText(ActivityName);
        tvReceiptDate.setText(Date);

        btnRemoveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiptContainer.removeView(view);
            }
        });

        receiptContainer.addView(view);
    }

}

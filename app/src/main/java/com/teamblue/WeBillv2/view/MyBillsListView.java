package com.teamblue.WeBillv2.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.teamblue.WeBillv2.R;

public class MyBillsListView extends ArrayAdapter<Integer> {

    private final Activity activity;
    private Integer[] billIds;
    private String[] billAmounts;
    private String[] billNames;
    private String[] billDates;
    private String[] latitudes;
    private String[] longitudes;
    private String[] paidBy;

    public MyBillsListView(Activity activity, Integer[] billIds, String[] billAmounts, String[] billNames, String[] billDates,String[]latitudes,String[] longitudes,String[] paidBy) {
        super(activity, R.layout.receipt_list_view, billIds);
        this.activity = activity;
        this.billIds = billIds;
        this.billAmounts = billAmounts;
        this.billNames = billNames;
        this.billDates = billDates;
        this.latitudes = latitudes;
        this.longitudes = longitudes;
        this.paidBy = paidBy;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.receipt_list_view, null,true);

        TextView tvBillAmount = (TextView) rowView.findViewById(R.id.tvBillAmountTextListView);
        TextView tvBillName = (TextView) rowView.findViewById(R.id.tvActivityNameListView);
        TextView tvBillDate = (TextView) rowView.findViewById(R.id.tvReceiptDateListView);
//        Button detailsButton = (Button) rowView.findViewById(R.id.btnReceiptDetailsListView);

        tvBillAmount.setText(tvBillAmount.getText().toString()+billAmounts[position]);
        tvBillName.setText(billNames[position]);
        tvBillDate.setText(billDates[position]);

//        detailsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                View viewReceiptDetails = inflater.inflate(R.layout.popup_receipt_click_details, null);
//
//                TextView tvTotal = viewReceiptDetails.findViewById(R.id.tvTotal);
//                TextView tvActivityText = viewReceiptDetails.findViewById(R.id.tvActivityText);
//                TextView tvDate = viewReceiptDetails.findViewById(R.id.tvDate);
//
//                builder.setView(viewReceiptDetails);
//
//                tvTotal.setText(billAmounts[position]);
//                tvActivityText.setText(billNames[position]);
//                tvDate.setText(billDates[position]);
//                Dialog receiptDetailsDialog;
//                receiptDetailsDialog = builder.create();
//                receiptDetailsDialog.show();
//            }
//        });

        return rowView;
    }
}

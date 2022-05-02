package com.teamblue.WeBillv2.model.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.teamblue.WeBillv2.R;

public class LineItemsAdapter extends ArrayAdapter<String> {

    private final Activity activity;
    private String[] itemDescriptions;
    private double[] itemQuantities;
    private double[] itemTotals;

    public LineItemsAdapter(Activity activity, String[] itemDescriptions, double[] itemQuantities, double[] itemTotals) {
        super(activity, R.layout.listitem_bill_item,itemDescriptions);
        this.activity = activity;
        this.itemDescriptions = itemDescriptions;
        this.itemQuantities = itemQuantities;
        this.itemTotals = itemTotals;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listitem_bill_item, null, true);

        TextView tvItemName = rowView.findViewById(R.id.tvItemName);
        TextView tvItemQuantity = rowView.findViewById(R.id.tvItemQuantity);
        TextView tvItemPrice = rowView.findViewById(R.id.tvItemPrice);

        tvItemName.setText(itemDescriptions[position]);
        tvItemQuantity.setText(String.valueOf(itemQuantities[position]));
        tvItemPrice.setText(String.valueOf(itemTotals[position]));

        return rowView;
    }
}

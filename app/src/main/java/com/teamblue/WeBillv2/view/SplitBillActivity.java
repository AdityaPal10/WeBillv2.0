package com.teamblue.WeBillv2.view;

import android.content.DialogInterface;
import android.icu.number.Precision;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.teamblue.WeBillv2.R;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SplitBillActivity extends AppCompatActivity {

    private EditText edtActivityNameSplitBill,edtTotalAmountSplitBill,edtDateSplitBill,edtAddressSplitBill;
    private TextView tvRemainAmount, tvTotalAmountSplitBill;
    private String getActivityName, getTotalAmount, getDate, getAddress;
    private Button btnAddSplitFriend;
    private Double RemainAmount, CurrentAmount,TotalAmount;
    LinearLayout LinearFriendSplit;
    AlertDialog AddSplitFriendDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        edtActivityNameSplitBill = (EditText) findViewById(R.id.edtActivityNameSplitBill);
        tvTotalAmountSplitBill = (TextView) findViewById(R.id.tvTotalAmountSplitBill);
        edtDateSplitBill = (EditText) findViewById(R.id.edtDateSplitBill);
        edtAddressSplitBill = (EditText) findViewById(R.id.edtAddressSplitBill);
        tvRemainAmount = (TextView) findViewById(R.id.tvRemainAmount);
        LinearFriendSplit = findViewById(R.id.LinearFriendSplit);

        /************Get Bundles from AddBillFragment Here************/
        getActivityName = (String) getIntent().getExtras().get("BILL_ACTIVITY_NAME");
        getTotalAmount = (String) getIntent().getExtras().get("BILL_TOTAL_AMOUNT");
        getDate = (String) getIntent().getExtras().get("BILL_DATE");
        getAddress = (String) getIntent().getExtras().get("BILL_ADDRESS");

        /************Put Bundles from AddBillFragment into SplitBillActivity************/
        edtActivityNameSplitBill.setText(getActivityName);
        tvTotalAmountSplitBill.setText(getTotalAmount);
        edtDateSplitBill.setText(getDate);
        edtAddressSplitBill.setText(getAddress);

        /************Handle Dialog of Adding Split Friends************/
        TotalAmount = Double.valueOf(tvTotalAmountSplitBill.getText().toString());
        RemainAmount = Double.valueOf(tvTotalAmountSplitBill.getText().toString()); // Grabbing initial Total Amount
        tvRemainAmount.setText(RemainAmount.toString());

        /*******Right now not support Change Money of Bill Consumption********/
//        edtTotalAmountSplitBill.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals("")) {
//                    edtTotalAmountSplitBill.setText("0");
//                }
//                RemainAmount = Double.valueOf(edtTotalAmountSplitBill.getText().toString());
//                tvRemainAmount.setText(RemainAmount.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        BigDecimal amount3 = new BigDecimal("2.15");
        buildAddSplitFriendDialog();
        btnAddSplitFriend = findViewById(R.id.btnAddSplitFriend);
        btnAddSplitFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSplitFriendDialog.show();
            }
        });
    }

    /************Build Dialog For Adding Split Friends Card************/
    private void buildAddSplitFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_add_split_friends, null);

        EditText edtAddFriendName = view.findViewById(R.id.edtAddFriendName);
        EditText edtAddFriendAmount = view.findViewById(R.id.edtAddFriendAmount);



        builder.setView(view);
        builder.setTitle("Enter Friend and Amount")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double CheckRemainAmount = Double.valueOf(tvRemainAmount.getText().toString());
                        if(TextUtils.isEmpty(edtAddFriendName.getText().toString()) ||
                                TextUtils.isEmpty(edtAddFriendAmount.getText().toString())){
                            edtAddFriendName.setError("Must Not Be Empty!");
                            edtAddFriendAmount.setError("Must Not Be Empty!");
                            return;
                        }
                        if (Double.valueOf(edtAddFriendAmount.getText().toString()) > CheckRemainAmount){
                            edtAddFriendAmount.setError("Too much!");
                            Toast.makeText(SplitBillActivity.this, "Too Much!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        addCard(edtAddFriendName.getText().toString(),
                                edtAddFriendAmount.getText().toString());
                        RemainAmount = RemainAmount - Double.valueOf(edtAddFriendAmount.getText().toString());
                        DecimalFormat df = new DecimalFormat("###.##");
                        tvRemainAmount.setText(df.format(RemainAmount));
//                        tvRemainAmount.setText(RemainAmount.toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AddSplitFriendDialog = builder.create();
    }

    private void addCard(String Username, String PayAmount){
        final View cardview = getLayoutInflater().inflate(R.layout.cardview_add_split_friends, null);


        TextView tvFriendSplitUsername = cardview.findViewById(R.id.tvFriendSplitUsername);
        TextView tvFriendSplitAmount = cardview.findViewById(R.id.tvFriendSplitAmount);
        Button btnRemoveFriendCard = cardview.findViewById(R.id.btnRemoveFriendCard);

        tvFriendSplitUsername.setText(Username);
        tvFriendSplitAmount.setText(PayAmount);
        CurrentAmount = Double.valueOf(tvFriendSplitAmount.getText().toString()); // First time amount

        btnRemoveFriendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearFriendSplit.removeView(cardview);
                CurrentAmount = Double.valueOf(tvFriendSplitAmount.getText().toString());
                RemainAmount = RemainAmount + CurrentAmount;
                DecimalFormat df = new DecimalFormat("###.##");
                tvRemainAmount.setText(df.format(RemainAmount));
            }
        });
        

/********Buggy function (Not Able to change amount from cards directly*********/
//        edtFriendSplitAmount.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals("")) {
//                    edtFriendSplitAmount.setText("0");
////                    edtFriendSplitAmount.setError("Must Not Be Empty! Or Delete This Card!");
////                    Double NewAmount = Double.valueOf(edtFriendSplitAmount.getText().toString());
////                    RemainAmount = RemainAmount + CurrentAmount - NewAmount;
////                    CurrentAmount = NewAmount;
////                    tvRemainAmount.setText(RemainAmount.toString());
////                    return;
//                }
////                if(TextUtils.isEmpty(charSequence.toString()) ){
////                    edtFriendSplitAmount.setError("Must Not Be Empty! Or Delete This Card!");
////                    return;
////                }
//
//                /*******Working Logic for Changing Amount*******/
//                Double NewAmount = Double.valueOf(edtFriendSplitAmount.getText().toString());
//                RemainAmount = RemainAmount + CurrentAmount - NewAmount;
//                CurrentAmount = NewAmount;
//                tvRemainAmount.setText(RemainAmount.toString());
//
////                if (Double.parseDouble(charSequence.toString()) > RemainAmount){
////                    edtFriendSplitAmount.setError("Too much money!");
////                    return;
////                }
//                if ( RemainAmount < 0){
//                    edtFriendSplitAmount.setError("Too much money!");
//                    return;
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (editable.toString().equals("")) {
//                    edtFriendSplitAmount.setText("0");
////                    edtFriendSplitAmount.setError("Must Not Be Empty! Or Delete This Card!");
////                    Double NewAmount = Double.valueOf(edtFriendSplitAmount.getText().toString());
////                    RemainAmount = RemainAmount + CurrentAmount - NewAmount;
////                    CurrentAmount = NewAmount;
////                    tvRemainAmount.setText(RemainAmount.toString());
////                    return;
//                }
////                if(TextUtils.isEmpty(charSequence.toString()) ){
////                    edtFriendSplitAmount.setError("Must Not Be Empty! Or Delete This Card!");
////                    return;
////                }
//
//                /*******Working Logic for Changing Amount*******/
//                Double NewAmount = Double.valueOf(edtFriendSplitAmount.getText().toString());
//                RemainAmount = RemainAmount + CurrentAmount - NewAmount;
//                CurrentAmount = NewAmount;
//                tvRemainAmount.setText(RemainAmount.toString());
//
////                if (Double.parseDouble(charSequence.toString()) > RemainAmount){
////                    edtFriendSplitAmount.setError("Too much money!");
////                    return;
////                }
//                if ( RemainAmount < 0){
//                    edtFriendSplitAmount.setError("Too much money!");
//                    return;
//                }
//            }
//        });

        LinearFriendSplit.addView(cardview);

    }
}

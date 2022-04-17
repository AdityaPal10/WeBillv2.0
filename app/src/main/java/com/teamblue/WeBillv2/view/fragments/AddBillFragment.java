package com.teamblue.WeBillv2.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.view.SplitBillActivity;


/**
 *  addbill fragment subclass.
 */
public class AddBillFragment extends Fragment {

    private EditText edtActivityNameAddBill,edtTotalAmountAddBill,edtDateAddBill,edtAddressAddBill;
    private Button btnEnterAddBill,btnScanBill;

    public AddBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//prevent keyboard pushing my view
        View view = inflater.inflate(R.layout.fragment_add_bill, container, false);

        edtActivityNameAddBill = (EditText) view.findViewById(R.id.edtActivityNameAddBill);
        edtTotalAmountAddBill = (EditText) view.findViewById(R.id.edtTotalAmountAddBill);
        edtDateAddBill = (EditText) view.findViewById(R.id.edtDateAddBill);
        edtAddressAddBill = (EditText) view.findViewById(R.id.edtAddressAddBill);
        btnEnterAddBill = (Button) view.findViewById(R.id.btnEnterAddBill);
        btnScanBill = (Button) view.findViewById(R.id.btnScanBill);

        btnEnterAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking for no total amount empty error
                if(TextUtils.isEmpty(edtActivityNameAddBill.getText().toString())) {
                    edtActivityNameAddBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtTotalAmountAddBill.getText().toString())) {
                    edtTotalAmountAddBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtDateAddBill.getText().toString())){
                    edtDateAddBill.setError("Must Not Be Empty!");
                        return;
                }else if(TextUtils.isEmpty(edtAddressAddBill.getText().toString())){
                    edtAddressAddBill.setError("Must Not Be Empty!");
                    return;
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("BILL_ACTIVITY_NAME",edtActivityNameAddBill.getText().toString());
                    bundle.putString("BILL_TOTAL_AMOUNT",edtTotalAmountAddBill.getText().toString());
                    bundle.putString("BILL_DATE",edtDateAddBill.getText().toString());
                    bundle.putString("BILL_ADDRESS",edtAddressAddBill.getText().toString());
                    Intent gotoSplitBillActivity = new Intent(view.getContext(), SplitBillActivity.class);
                    gotoSplitBillActivity.putExtras(bundle);
                    startActivity(gotoSplitBillActivity);
                }

            }
        });

        btnScanBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /********TODO: Start Scan Bill Activity here (not fragment!)********/
            }
        });

//        edtTotalAmountAddBill.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals("")){
//                    edtTotalAmountAddBill.setText("0");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


        return view;
    }
}

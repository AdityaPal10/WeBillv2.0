package com.teamblue.WeBillv2.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.teamblue.WeBillv2.R;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView txtPaymentDetails;
    private Button btnPaymentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtPaymentDetails = (TextView) findViewById(R.id.txtPaymentDetails);
        btnPaymentDetails = (Button) findViewById(R.id.btnPaymentDetails);

    }

}
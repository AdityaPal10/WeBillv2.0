package com.teamblue.WeBillv2.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.StripeController;

public class StripeAccountsActivity extends AppCompatActivity {

    private TextView txtStripeAccounts;
    private Button btnStripeAccounts;
    StripeController stripeController = new StripeController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_accounts);

        txtStripeAccounts = (TextView) findViewById(R.id.txtStripeAccounts);
        btnStripeAccounts = (Button) findViewById(R.id.btnStripeAccounts);

        // get data from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String message = txtStripeAccounts.getText().toString().replace("{user}", username);
        txtStripeAccounts.setText(message);

        btnStripeAccounts.setOnClickListener(view -> {
            stripeController.createStripeAccounts(view.getContext(), username);
        });
    }
}
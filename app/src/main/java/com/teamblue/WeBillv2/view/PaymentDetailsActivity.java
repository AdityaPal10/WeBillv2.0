package com.teamblue.WeBillv2.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.StripeController;
import com.teamblue.WeBillv2.model.pojo.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailsActivity extends AppCompatActivity {

    // TODO: Finish coding Stripe Payment Sheet logic

    private String TAG = "STRIPE";
    private TextView txtPaymentDetails;
    private Button btnPaymentDetails;
    PaymentSheet paymentSheet;
    StripeController stripeController = new StripeController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // inflate the views
        txtPaymentDetails = (TextView) findViewById(R.id.txtPaymentDetails);
        btnPaymentDetails = (Button) findViewById(R.id.btnPaymentDetails);

        // get data from intent
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String message = String.format("Hello %s! Thanks for signing up. " +
                "Before you continue, please add a payment method.", username);
        txtPaymentDetails.setText(message);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

//        btnPaymentDetails.setOnClickListener(view -> {
//            stripeController.initializePaymentSheet(getApplicationContext(), paymentSheet);
//        });
    }


    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getApplicationContext(),TAG+" Canceled",Toast.LENGTH_LONG).show();
            Log.d(TAG,"Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(getApplicationContext(),TAG+"Got error: " + ((PaymentSheetResult.Failed) paymentSheetResult).getError(),Toast.LENGTH_LONG).show();
            Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(getApplicationContext(),"Success!",Toast.LENGTH_LONG).show();
            Log.d(TAG,"Completed");

            // After successfully getting card details, go to the Menu/Friend View
            Intent intent = new Intent(this, MenuView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
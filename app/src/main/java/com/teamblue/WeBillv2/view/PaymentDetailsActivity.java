package com.teamblue.WeBillv2.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.teamblue.WeBillv2.R;

public class PaymentDetailsActivity extends AppCompatActivity {

    private String TAG = "STRIPE";
    private TextView txtPaymentDetails;
    private Button btnPaymentDetails;

    // needed to setup payment sheet
    private String setupIntentClientSecret;
    private String customerId;
    private String ephKey;
    private String pubKey;
    PaymentSheet paymentSheet;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        // inflate the views
        txtPaymentDetails = (TextView) findViewById(R.id.txtPaymentDetails);
        btnPaymentDetails = (Button) findViewById(R.id.btnPaymentDetails);

        Intent intent = getIntent();
        setupIntentClientSecret = intent.getStringExtra("setupIntent");
        customerId = intent.getStringExtra("customerId");
        ephKey = intent.getStringExtra("ephKey");
        pubKey = intent.getStringExtra("pubKey");

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        btnPaymentDetails.setOnClickListener(view -> {
            initPaymentSheet();
        });
    }

    private void initPaymentSheet() {
        try {
            customerConfig = new PaymentSheet.CustomerConfiguration(customerId, ephKey);
            PaymentConfiguration.init(getApplicationContext(), pubKey);
            final PaymentSheet.Configuration config = new PaymentSheet.Configuration
                    .Builder("WeBill")
                    .customer(customerConfig)
                    .allowsDelayedPaymentMethods(true)
                    .build();
            paymentSheet.presentWithSetupIntent(setupIntentClientSecret, config);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
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
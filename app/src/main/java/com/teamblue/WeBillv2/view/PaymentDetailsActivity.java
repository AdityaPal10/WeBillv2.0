package com.teamblue.WeBillv2.view;

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
import com.teamblue.WeBillv2.model.pojo.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailsActivity extends AppCompatActivity {

    // TODO: Finish coding Stripe Payment Sheet logic
    // TODO: upon successful collection of user's card details... intent to Friends/MenuView

    private String TAG = "STRIPE";
    private TextView txtPaymentDetails;
    private Button btnPaymentDetails;
    PaymentSheet paymentSheet;
    String setupIntentClientSecret;
    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        txtPaymentDetails = (TextView) findViewById(R.id.txtPaymentDetails);
        btnPaymentDetails = (Button) findViewById(R.id.btnPaymentDetails);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        btnPaymentDetails.setOnClickListener(view -> {
            initStripePaymentSheet();
        });
    }

    // TODO: Add Heroku/Retrofit client to handle API calls
    // TODO: Add and update StripeService and PaymentSheetModel to reflect SetupIntent NOT PaymentIntent
    private void initStripePaymentSheet() {
//        StripeService stripeService = HerokuRetrofitClient.getRetrofitInstance().create(StripeService.class);
//        Call<PaymentSheetModel> call = stripeService.stripePaymentSheet();
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) {
//                if(response.code() == Constants.RESPONSE_OK) {
//                    try {
//                        PaymentSheetModel result = (PaymentSheetModel) response.body();
//                        customerConfig = new PaymentSheet.CustomerConfiguration(
//                            result.getString("customer"),
//                            result.getString("ephemeralKey")
//                        );
//                        setupIntentClientSecret = result.getSetupIntent();
//                        PaymentConfiguration.init(getApplicationContext(), result.getPublishableKey());
//
//                        presentStripePaymentSheet();
//                    } catch (Exception e) { /* handle error */ }
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void presentStripePaymentSheet() {
        final PaymentSheet.Configuration config = new PaymentSheet.Configuration
                .Builder("WeBill")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithSetupIntent(setupIntentClientSecret,config);
    }

    void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(getApplicationContext(),TAG+" Canceled",Toast.LENGTH_LONG).show();
            Log.d(TAG,"Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(getApplicationContext(),TAG+"Got error: " + ((PaymentSheetResult.Failed) paymentSheetResult).getError(),Toast.LENGTH_LONG).show();
            Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(getApplicationContext(),TAG+" Completed",Toast.LENGTH_LONG).show();
            Log.d(TAG,"Completed");
        }
    }

}
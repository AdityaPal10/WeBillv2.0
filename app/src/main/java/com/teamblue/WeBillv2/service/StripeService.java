package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.widget.Toast;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.teamblue.WeBillv2.model.api.StripeMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.PaymentSheetModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripeService {

    public void initStripePaymentSheet(Context context, PaymentSheet paymentSheet) {
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<PaymentSheetModel> call = stripeMethods.stripePaymentSheet();
        call.enqueue(new Callback<PaymentSheetModel>() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == Constants.RESPONSE_OK) {
                    try {
                        PaymentSheetModel result = (PaymentSheetModel) response.body();
                        PaymentSheet.CustomerConfiguration customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getCustomerID(), result.getEphemeralKey());
                        String setupIntentClientSecret = result.getSetupIntent();
                        PaymentConfiguration.init(context, result.getPublishableKey());

                        final PaymentSheet.Configuration config = new PaymentSheet.Configuration
                                .Builder("WeBill")
                                .customer(customerConfig)
                                .allowsDelayedPaymentMethods(true)
                                .build();
                        paymentSheet.presentWithSetupIntent(setupIntentClientSecret,config);
                    } catch (Exception e) { /* handle error */ }
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}

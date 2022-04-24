package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.widget.Toast;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.teamblue.WeBillv2.model.api.StripeMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.PayFriendModel;
import com.teamblue.WeBillv2.model.pojo.PaymentSheetModel;
import com.teamblue.WeBillv2.model.pojo.User;
import com.teamblue.WeBillv2.model.pojo.UserStripeAccount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripeService {

    public void createAccount(Context context, User user) {
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.createStripeAccounts(user);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if(response.code() == Constants.RESPONSE_OK) {
                    UserStripeAccount result = (UserStripeAccount) response.body();
                    // TODO call payment sheet
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                // TODO handle failure case
            }
        });
    }

    public void getAccount(Context context, String username) {
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.getStripeAccounts(username);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    UserStripeAccount result = (UserStripeAccount) response.body();
                    // TODO handle OK response
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                // TODO handle fail response
            }
        });
    }

    public void getPaymentSheet(Context context, PaymentSheet paymentSheet, String username) {
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<PaymentSheetModel> call = stripeMethods.stripePaymentSheet(username);
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

    public void payFriend(Context context, PayFriendModel payFriendModel) {
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<LoginModel> call = stripeMethods.payFriendTransaction(payFriendModel);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                // TODO handle OK response
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                // TODO handle fail response
            }
        });
    }
}

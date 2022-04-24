package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.content.Intent;
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
import com.teamblue.WeBillv2.view.PaymentDetailsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StripeService {

    /**
     * makes a network call to our backend api (which in turn calls Stripe) to create a Stripe
     * Account and Customer object for our user
     * If the call is successful, starts an intent to the PaymentDetailsActivity
     * @param context the activity where this function was called (StripeAccountsActivity.class)
     * @param user a User object that holds the user's username
     */
    public void createAccount(Context context, User user) {
        Toast.makeText(context, "Just a moment", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.createStripeAccounts(user);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if(response.code() == Constants.RESPONSE_OK) {
                    // we don't need the response, but we can create an intent to PaymentDetails now
                    Intent intent = new Intent(context, PaymentDetailsActivity.class);
                    intent.putExtra("username", user.getUsername());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // since we're calling from another activity
                } else {
                    Toast.makeText(context, "Sorry, we've got an error.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                Toast.makeText(context, "Sorry, please try again.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * makes network call to our backend, fetches the user's Stripe account_id and customer_id
     * @param context the activity from which this function is called
     * @param username the user's username (for which we are running this query)
     */
    public void getAccount(Context context, String username) {
        StripeMethods stripeMethods = RetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.getStripeAccounts(username);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    UserStripeAccount result = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                // TODO handle fail response
            }
        });
    }

    /**
     * makes a network call to our backend api, populates a Stripe payment sheet with metadata
     * necessary to save the user's payment method details to Stripe
     * @param context the activity from which we call this (PaymentDetailsActivity.class)
     * @param paymentSheet Stripe payment sheet to configure with the necessary metadata
     * @param username the user's username, needed to fetch their details
     */
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

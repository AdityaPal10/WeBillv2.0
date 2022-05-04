package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

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

    private String TAG = "STRIPE";

    /**
     * makes a network call to our backend api (which in turn calls Stripe) to create a Stripe
     * Account and Customer object for our user
     * @param context the activity from which this is called (StripeAccountsActivity.class)
     * @param user a User object that holds the user's username
     */
    public void createAccount(Context context, User user) {
        // Toast to let users know this might take a while
        Toast.makeText(context, "Just a moment please", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        // make the call to backend and do something based on response
        Call<UserStripeAccount> call = stripeMethods.createStripeAccounts(user);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    Toast.makeText(context, "successfully created wallet", Toast.LENGTH_SHORT).show();
                    UserStripeAccount result = (UserStripeAccount) response.body();
                    String customerID = result.getCustomer_id();
                    // successful response, so now we pass this info to populate a payment sheet
                    getPaymentSheet(context, user.getUsername(), customerID);
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                Toast.makeText(context, "Sorry, we've got an error.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * makes network call to our backend, fetches the user's Stripe account_id and customer_id
     * @param context the activity from which this function is called
     * @param username the user's username (for which we are running this query)
     * @param mode either "account" or "customer" to signify which value is saved in preferences
     */
    public void getAccount(Context context, String username, String mode) {
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.getStripeAccounts(username);
        // call to the backend, do something based on the response
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    UserStripeAccount result = response.body();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    // depending on the mode, the key and value we add to shared prefs is different
                    if (mode.equals("account")) {
                        editor.putString("acc_" + username, result.getAccount_id());
                        Log.d(TAG, "acc_" + username + ": " + result.getAccount_id());
                    } else if (mode.equals("customer")) {
                        editor.putString("cus_" + username, result.getCustomer_id());
                        Log.d(TAG, "cus_" + username + ": " + result.getCustomer_id());
                    }
                    editor.apply();
                } else {
                    Log.d(TAG, "couldn't find accounts for " + username);
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                Log.d(TAG, "couldn't find accounts for " + username);
            }
        });
    }

    /**
     * makes a network call to our backend api, populates a Stripe payment sheet with metadata
     * necessary to save the user's payment method details to Stripe
     * @param context the activity from which we call this (PaymentDetailsActivity.class)
     * @param username the user's username, needed to fetch their details
     */
    public void getPaymentSheet(Context context, String username, String cusID) {
        // toast to let the user know this might take a bit of time
        Toast.makeText(context, "Another moment please", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<PaymentSheetModel> call = stripeMethods.stripePaymentSheet(username);
        // call to the backend, do something based on the response
        call.enqueue(new Callback<PaymentSheetModel>() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == Constants.RESPONSE_OK) {
                    PaymentSheetModel result = (PaymentSheetModel) response.body();
                    // create an intent to PaymentDetailsActivity (where we get card details)
                    // and grab info we need as metadata for the Stripe payment sheet
                    Intent intent = new Intent(context, PaymentDetailsActivity.class);
                    intent.putExtra("setupIntent", result.getSetupIntent());
                    intent.putExtra("customerId", cusID);
                    intent.putExtra("ephKey", result.getEphemeralKey());
                    intent.putExtra("pubKey", result.getPublishableKey());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(context,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * makes a network call to our backend api, calling the Stripe Api to initiate a payment
     * @param context the context from which we call this (Friend Fragment)
     * @param payFriendModel the model representing who pays who and how much
     */
    public void payFriend(Context context, PayFriendModel payFriendModel) {
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<LoginModel> call = stripeMethods.payFriendTransaction(payFriendModel);
        // call to backend, do something based on response
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    LoginModel payResponse = (LoginModel) response.body();
                    // toast for payment status
                    Toast.makeText(context, "Payment " + payResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "Payment failed. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

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

    /**
     * makes a network call to our backend api (which in turn calls Stripe) to create a Stripe
     * Account and Customer object for our user
     * @param context the activity from which this is called (StripeAccountsActivity.class)
     * @param user a User object that holds the user's username
     */
    public void createAccount(Context context, User user) {
        Toast.makeText(context, "Just a moment please", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
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
        Toast.makeText(context, "Processing...", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<UserStripeAccount> call = stripeMethods.getStripeAccounts(username);
        call.enqueue(new Callback<UserStripeAccount>() {
            @Override
            public void onResponse(Call<UserStripeAccount> call, Response<UserStripeAccount> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    UserStripeAccount result = response.body();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (mode.equals("account")) {
                        editor.putString(username, result.getAccount_id());
                    } else if (mode.equals("customer")) {
                        editor.putString(username, result.getCustomer_id());
                    }
                    editor.apply();
                } else {
                    Toast.makeText(context, "Could not find " + username, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserStripeAccount> call, Throwable t) {
                Toast.makeText(context, "Could not find " + username, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(context, "Another moment please", Toast.LENGTH_SHORT).show();
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<PaymentSheetModel> call = stripeMethods.stripePaymentSheet(username);
        call.enqueue(new Callback<PaymentSheetModel>() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == Constants.RESPONSE_OK) {
                    PaymentSheetModel result = (PaymentSheetModel) response.body();
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

    public void payFriend(Context context, PayFriendModel payFriendModel) {
        StripeMethods stripeMethods = LoginRetrofitClient.getRetrofitInstance().create(StripeMethods.class);
        Call<LoginModel> call = stripeMethods.payFriendTransaction(payFriendModel);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    LoginModel payResponse = (LoginModel) response.body();
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

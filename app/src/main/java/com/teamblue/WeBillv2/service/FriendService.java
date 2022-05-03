package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.api.FriendRequest;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendService {
    private String TAG = "Friend";
    ArrayList<FriendBalanceModel> friendBalanceList= new ArrayList<>();

    Context context;
    /*
    making network call to our backend api to add friend.
    parameters :
       context - to pass the activity where its being called, in this case its the MainActivity.class
       username - our users username
       friendUsername - the edit text widget where the user enters their friends username to add

       Return type:
       void - the function if successful will start an intent to the friends activity.
     */
    public void addFriend(Context context, String username, String friendUsername) {
        String friendName = friendUsername.toString().trim();

        //1. create an instance of friend methods interface defined in our FriendMethods class
        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username and friendName as paramaters
        Call<LoginModel> call = friendMethods.addFriend(new FriendRequest(username, friendName));
        Log.d(TAG, friendUsername.toString().trim());

        /*3. create a callback for our call object, once its finished the network call, it will use this callback to further
           process whether the network call was successful or not.
         */
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d(TAG, String.valueOf(response.code()));
                //getting response body if call was successful
                if (response.code() == Constants.RESPONSE_OK) {
                    LoginModel apiResponse = (LoginModel) response.body();

                    //case 1: when successfully added friend
                    if (apiResponse.getStatus() == Constants.RESPONSE_OK) {
                        Toast.makeText(context, "successfully added friend", Toast.LENGTH_LONG).show();
                    }
                } else {
                    switch (response.code()) {
                        case Constants.FRIEND_EXISTS:
                            Toast.makeText(context, "Friend already exists", Toast.LENGTH_LONG).show();
                            break;
                        case Constants.NO_FRIEND_EXISTS:
                            Toast.makeText(context, "Friend doesn't have an accout", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context, "Error adding friend, please try again", Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG, "Error adding friend,please try again");
                    //friendUsername.setText("");
                    //Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG, "Error adding friend,please try again");
                Toast.makeText(context, "Error adding friend,please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getBalance(View view, String username, TextView tvToPay,TextView tvToTakeBack,TextView tvMyBalanceNumber) {
        Context context = view.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        Call<Object> call = friendMethods.getUserBalance(username);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d(TAG,String.valueOf(response.code()));
                Log.d(TAG,response.toString());
                editor.putBoolean("FriendCardFinished",true);
                //getting response body if successful
                if(response.code()==Constants.RESPONSE_OK){
                    //set response to shared preferences
                    Object responseObject = response.body();

                    //get amounts owed from response object
                    Log.d("Friend",responseObject.toString());
                    String objectStr = responseObject.toString();
                    String[] entries = objectStr.split(",");

                    double amountOwed = Double.parseDouble(entries[0].split("=")[1]);
                    String amountToPayStr = entries[1].split("=")[1];
                    double amountToPay = Double.parseDouble(amountToPayStr.substring(0,amountToPayStr.length()-1));


                    editor.putString(Constants.BALANCE_TO_TAKE, String.format(Locale.US, "%.2f", amountOwed));
                    editor.putString(Constants.BALANCE_TO_PAY, String.format(Locale.US, "%.2f", amountToPay));
                    editor.apply();

                }

                tvToPay.setText(sharedPreferences.getString(Constants.BALANCE_TO_PAY,"0.0"));
                tvToTakeBack.setText(sharedPreferences.getString(Constants.BALANCE_TO_TAKE,"0.0"));
                DecimalFormat df = new DecimalFormat("###.00");
                Double Balance = Double.valueOf(tvToTakeBack.getText().toString()) - Double.valueOf(tvToPay.getText().toString());
                tvMyBalanceNumber.setText(df.format(Balance));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                editor.putBoolean("FriendCardFinished",true);
                editor.putString(Constants.BALANCE_TO_PAY,"0.0");
                editor.putString(Constants.BALANCE_TO_TAKE,"0.0");
                editor.apply();

                tvToPay.setText(sharedPreferences.getString(Constants.BALANCE_TO_PAY,"0.0"));
                tvToTakeBack.setText(sharedPreferences.getString(Constants.BALANCE_TO_TAKE,"0.0"));
            }
        });
    }

}
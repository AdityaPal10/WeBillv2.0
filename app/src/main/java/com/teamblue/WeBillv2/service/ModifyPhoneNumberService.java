package com.teamblue.WeBillv2.service;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.teamblue.WeBillv2.model.api.AccountsMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPhoneNumberModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPhoneNumberService {

    public void updatePhoneNumber(Context context, String username, String oldPhoneNumber, String newPhoneNumber){

    AccountsMethods accountsMethods = LoginRetrofitClient.getRetrofitInstance().create(AccountsMethods.class);
    ModifyPhoneNumberModel modifyPhoneNumberModel = new ModifyPhoneNumberModel(username,newPhoneNumber);
    //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
    Call<LoginModel> call = accountsMethods.setNewPhoneNumber(modifyPhoneNumberModel);
    call.enqueue(new Callback<LoginModel>() {

        @Override
        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
            Log.d(TAG,String.valueOf(response.code()));
            Log.d(TAG,response.toString());
            if(response.code()==Constants.RESPONSE_OK){
                //ModifyPhoneNumberModel apiResponse = (ModifyPhoneNumberModel) response.body();
                Toast.makeText(context, "number is updated", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onFailure(Call<LoginModel> call, Throwable t) {
            Toast.makeText(context, "number is not updated", Toast.LENGTH_LONG).show();

        }
    });
    }

    public void getPhoneNumber(Context context, String username) {
        AccountsMethods accountsMethods = LoginRetrofitClient.getRetrofitInstance().create(AccountsMethods.class);
        Call<ModifyPhoneNumberModel> call = accountsMethods.getPhoneNumber(username);
        call.enqueue(new Callback<ModifyPhoneNumberModel>() {
            @Override
            public void onResponse(Call<ModifyPhoneNumberModel> call, Response<ModifyPhoneNumberModel> response) {
                if (response.code() == Constants.RESPONSE_OK) {
                    ModifyPhoneNumberModel result = response.body();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("tel_" + username, result.getNewPhoneNumber());
                    editor.apply();
                } else {
                    Log.d("Phone", "couldn't find # for " + username);
                }
            }

            @Override
            public void onFailure(Call<ModifyPhoneNumberModel> call, Throwable t) {
                Log.d("Phone", "couldn't find # for " + username);
            }
        });
    }
}

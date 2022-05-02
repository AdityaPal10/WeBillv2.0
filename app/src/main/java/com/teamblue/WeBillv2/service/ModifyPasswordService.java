package com.teamblue.WeBillv2.service;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.teamblue.WeBillv2.model.api.AccountsMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPasswordModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPhoneNumberModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPasswordService {
    public void updatePassword(Context context, String username, String newPassword){

        AccountsMethods accountsMethods = LoginRetrofitClient.getRetrofitInstance().create(AccountsMethods.class);
        ModifyPasswordModel modifyPasswordModel = new ModifyPasswordModel(username,newPassword);
        //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
        Call<LoginModel> call = accountsMethods.setNewPassword(modifyPasswordModel);
        call.enqueue(new Callback<LoginModel>() {

            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d(TAG,String.valueOf(response.code()));
                Log.d(TAG,response.toString());
                if(response.code()== Constants.RESPONSE_OK){
                    Toast.makeText(context, "password is updated", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.PASSWORD_KEY,newPassword);
                    editor.apply();
                }

            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(context, "password is not updated", Toast.LENGTH_LONG).show();
            }


        });
    }
}

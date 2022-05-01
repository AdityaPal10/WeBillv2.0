package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPasswordModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPhoneNumberModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface AccountsMethods {

    @GET("accounts/getOldPassword")
    Call<ModifyPasswordModel> getOldPassword(@Query("username")String username);

    @GET("accounts/getOldPhoneNumber")
    Call<ModifyPhoneNumberModel> getOldPhoneNumber(@Query("username")String username);

    @POST("accounts/setNewPhoneNumber")
    Call<LoginModel> setNewPhoneNumber(@Body String newPhoneNumber);

    @POST("accounts/setNewPassword")
    Call<LoginModel> setNewPassword(@Body String newPassword);

}
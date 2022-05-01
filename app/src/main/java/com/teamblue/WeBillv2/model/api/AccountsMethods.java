package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.PayFriendModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface AccountsMethods {

    @GET("accounts/getOldPassword")
    Call<LoginModel> getOldPassword(@Query("username")String username);

    @GET("accounts/getOldPhoneNumber")
    Call<LoginModel> getOldPhoneNumber(@Query("username")String username);

    @POST("accounts/setNewPhoneNumber")
    Call<LoginModel> setNewPhoneNumber(@Body String newPhoneNumber);

    @POST("accounts/setNewPassword")
    Call<LoginModel> setNewPassword(@Body String newPassword);

}
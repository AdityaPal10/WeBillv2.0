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


    @POST("login/changePhone")
    Call<LoginModel> setNewPhoneNumber(@Body ModifyPhoneNumberModel modifyPhoneNumberModel);

    @POST("login/changePassword")
    Call<LoginModel> setNewPassword(@Body ModifyPasswordModel modifyPasswordModel);

}
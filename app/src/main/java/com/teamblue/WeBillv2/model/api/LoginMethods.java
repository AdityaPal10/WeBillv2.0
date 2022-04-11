package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.SignUpUser;
import com.teamblue.WeBillv2.model.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginMethods {


    @POST("login")
    Call<LoginModel> login(@Body User user);

    @POST("register")
    Call<LoginModel> signup(@Body SignUpUser signUpUser);
}
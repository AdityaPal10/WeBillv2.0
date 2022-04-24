package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.SignUpUser;
import com.teamblue.WeBillv2.model.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginMethods {


    @GET("login/getUsers")
    Call<User> getUser();

    @POST("login/signin")
    Call<LoginModel> login(@Body User user);

    @POST("login/register")
    Call<LoginModel> signup(@Body SignUpUser signUpUser);
}
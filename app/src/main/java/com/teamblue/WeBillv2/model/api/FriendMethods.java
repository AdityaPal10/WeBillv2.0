package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.Friend;
import com.teamblue.WeBillv2.model.pojo.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FriendMethods {

    @POST("friend/add")
    Call<LoginModel> addFriend(@Body FriendRequest friend);

}

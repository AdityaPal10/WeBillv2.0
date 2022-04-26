package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LocationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FriendBalanceMethods {

    @GET("friend/getFriendBalance")
    Call<ArrayList<FriendBalanceModel>> getFriendBalanceInfo(@Query("username") String username);

}
package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.BillModel;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BillMethods {

    @GET("bill/getBillsForUser")
    Call<List<BillModel>> getBillsForUser(@Query("username") String username);
}

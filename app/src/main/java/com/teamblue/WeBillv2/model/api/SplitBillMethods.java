package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.SplitBillModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SplitBillMethods {

    @POST("bill/addSplitBill")
    Call<LoginModel> putSplitBill(@Body SplitBillModel billSplit);

}

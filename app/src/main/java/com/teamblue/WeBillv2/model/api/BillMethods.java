package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.BillModel;
import com.teamblue.WeBillv2.model.pojo.BillsByLoc;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.OCRBill;
import com.teamblue.WeBillv2.model.pojo.VeryfiOcrResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BillMethods {

    @GET("bill/getBillsForUser")
    Call<List<BillModel>> getBillsForUser(@Query("username") String username, @Query("year") String year);

    @POST("bill/getBillsForUserByLoc")
    Call<List<BillModel>> getBillsForUserByLoc(@Body BillsByLoc billsByLoc);

    @POST("bill/processBill")
    Call<VeryfiOcrResponse> scanBillForImage(@Body OCRBill ocrBill);
}

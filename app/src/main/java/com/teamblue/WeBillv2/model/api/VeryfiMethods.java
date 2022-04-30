package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.OCRBill;
import com.teamblue.WeBillv2.model.pojo.VeryfiOcrResponse;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VeryfiMethods {

    @POST("processBill")
    Call<VeryfiOcrResponse> processBill(@Body OCRBill ocrBill);
}

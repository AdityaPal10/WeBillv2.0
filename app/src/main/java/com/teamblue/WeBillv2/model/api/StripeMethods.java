package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.PaymentSheetModel;

import retrofit2.Call;
import retrofit2.http.POST;

public interface StripeMethods {

    @POST("payment-sheet")
    Call<PaymentSheetModel> stripePaymentSheet();

}

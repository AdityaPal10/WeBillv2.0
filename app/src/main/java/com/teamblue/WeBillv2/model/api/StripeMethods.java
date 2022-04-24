package com.teamblue.WeBillv2.model.api;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.PayFriendModel;
import com.teamblue.WeBillv2.model.pojo.PaymentSheetModel;
import com.teamblue.WeBillv2.model.pojo.User;
import com.teamblue.WeBillv2.model.pojo.UserStripeAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StripeMethods {

    @POST("payments/createAccount")
    Call<UserStripeAccount> createStripeAccounts(@Body User user);

    @GET("payments/getAccount")
    Call<UserStripeAccount> getStripeAccounts(@Query("username") String username);

    @GET("payments/payment_sheet")
    Call<PaymentSheetModel> stripePaymentSheet(@Query("username") String username);

    @POST("payments/pay_friend")
    Call<LoginModel> payFriendTransaction(@Body PayFriendModel payFriendModel);

}

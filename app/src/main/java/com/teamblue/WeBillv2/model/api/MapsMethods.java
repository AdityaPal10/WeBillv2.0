package com.teamblue.WeBillv2.model.api;

import com.teamblue.WeBillv2.model.pojo.LocationModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MapsMethods {

    @GET("user/mapsData")
    Call<ArrayList<LocationModel>> getExpenseLocation(@Body String username);

}

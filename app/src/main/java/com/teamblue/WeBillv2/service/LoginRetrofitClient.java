package com.teamblue.WeBillv2.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetrofitClient {

    private static Retrofit retrofit;
    private static String BASE_URL = "https://webill-app.herokuapp.com/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

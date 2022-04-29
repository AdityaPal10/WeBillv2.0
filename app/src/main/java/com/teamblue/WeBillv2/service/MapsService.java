package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.teamblue.WeBillv2.controller.MapsController;
import com.teamblue.WeBillv2.model.api.MapsMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LocationModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.User;
import com.teamblue.WeBillv2.view.MenuView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsService {

    private String TAG = "ExpLocation";
    ArrayList<LocationModel> expLocationList= new ArrayList<>();

    public void getExpenseLocations(Context context, MapsController mapsControllerCallback){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.USERNAME_KEY,"");
//        String username = "test";

        MapsMethods mapsMethods = LoginRetrofitClient.getRetrofitInstance().create(MapsMethods.class);
        Call<ArrayList<LocationModel>> call = mapsMethods.getExpenseLocation(new User(username));

        call.enqueue(new Callback<ArrayList<LocationModel>>() {
            @Override
            public void onResponse(Call<ArrayList<LocationModel>> call, Response<ArrayList<LocationModel>> response) {
                Log.d(TAG, String.valueOf(response.code()));
                Log.d(TAG,response.toString());

                //if network call was successful
                if(response.code()== Constants.RESPONSE_OK){
                    //create our defined object from the response body of the api call
                    expLocationList = response.body();
                    System.out.println(expLocationList.toString());
                    Log.d("Maps",expLocationList.toString());
                    mapsControllerCallback.getExpenseLocations(expLocationList);

                }else{
                    //network call was unsuccessful, allow users to re-enter their details
                    Log.d(TAG,"Network failure");
                    Toast.makeText(context,"Network failure",Toast.LENGTH_LONG).show();
                }
            }

            //when api call was unable to hit the backend api for some reason, then throw the message to the user.
            @Override
            public void onFailure(Call<ArrayList<LocationModel>> call, Throwable t) {
                Log.d(TAG,"Could not get data");
                Toast.makeText(context,t.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

}

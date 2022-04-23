package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.teamblue.WeBillv2.model.api.FriendMethods;
//import com.teamblue.WeBillv2.model.api.GroupMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.Friend;
import com.teamblue.WeBillv2.model.pojo.Group;
import com.teamblue.WeBillv2.model.pojo.LoginModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
public class GroupService {
    private String TAG = "Group";
    public static void addGroup(Context context, String groupName){

        //1. create an instance of friend methods interface defined in our FriendMethods class
        GroupMethods groupMethods = LoginRetrofitClient.getRetrofitInstance().create(GroupMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username and friendName as paramaters
        Call<LoginModel> call = GroupMethods.addtoGroup(new Group(ArrayList<Friend>));
        Log.d(TAG,friendUsername.getText().toString().trim());

        */
/*3. create a callback for our call object, once its finished the network call, it will use this callback to further
           process whether the network call was successful or not.
         *//*

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d(TAG,String.valueOf(response.code()));
                //getting response body if call was successful
                if(response.code()== Constants.RESPONSE_OK){
                    LoginModel apiResponse = (LoginModel) response.body();

                    //case 1: when successfully added friend
                    if(apiResponse.getStatus()==Constants.RESPONSE_OK){
                        Toast.makeText(context,"successfully added friend",Toast.LENGTH_LONG).show();
                    }
                    //case 2: when friendship already exists
                    else if(apiResponse.getStatus()==Constants.FRIEND_EXISTS){
                        Toast.makeText(context,"Friend already exists",Toast.LENGTH_LONG).show();
                    }
                    //case 3 : when friend doesnt have an account on the app
                    else if(apiResponse.getStatus()==Constants.NO_FRIEND_EXISTS){
                        Toast.makeText(context,"Friend doesnt have an accout",Toast.LENGTH_LONG).show();
                    }
                    //case 4: when error adding friend
                    else{
                        Toast.makeText(context,"Error adding friend, please try again",Toast.LENGTH_LONG).show();
                    }
                }else{
                    //Log.d(TAG,"Error adding friend,please try again");
                    //friendUsername.setText("");
                    Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
                }
            }
}
*/

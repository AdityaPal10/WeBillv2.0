package com.teamblue.WeBillv2.service;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.api.FriendRequest;
import com.teamblue.WeBillv2.model.api.LoginMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.Friend;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendService {
    private String TAG = "Friend";


    /*
    making network call to our backend api to add friend.
    parameters :
       context - to pass the activity where its being called, in this case its the MainActivity.class
       username - our users username
       friendUsername - the edit text widget where the user enters their friends username to add

       Return type:
       void - the function if successful will start an intent to the friends activity.
     */
    public void addFriend(Context context, String username, EditText friendUsername){
        String friendName = friendUsername.getText().toString().trim();

        //1. create an instance of friend methods interface defined in our FriendMethods class
        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username and friendName as paramaters
        Call<LoginModel> call = friendMethods.addFriend(new FriendRequest(username,friendName));
        Log.d(TAG,friendUsername.getText().toString().trim());

        /*3. create a callback for our call object, once its finished the network call, it will use this callback to further
           process whether the network call was successful or not.
         */
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
                    Log.d(TAG,"Error adding friend,please try again");
                    friendUsername.setText("");
                    Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG,"Error adding friend,please try again");
                friendUsername.setText("");
                Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
            }
        });

    }
}

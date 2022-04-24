package com.teamblue.WeBillv2.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.service.FriendService;

public class FriendController {

    private String TAG = "Friend";
    FriendService friendService = new FriendService();

    public void addFriend(Context context, String username,EditText friendUsername){
        Log.d(TAG,"attempting add friend");
        friendService.addFriend(context,username,friendUsername.getText().toString().trim());
    }

}

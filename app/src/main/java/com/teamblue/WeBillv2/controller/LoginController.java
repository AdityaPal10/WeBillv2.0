package com.teamblue.WeBillv2.controller;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import com.teamblue.WeBillv2.service.LoginService;

//an intermediary between the MainActivity and Login service class.
//Main activity would only use this class and would not be privy to the login service methods.
public class LoginController {

    private String TAG = "Login";
    LoginService loginService = new LoginService();

    //calling the login service's login method.
    public void login(Context context,EditText email, EditText password){
        Log.d(TAG,"attempting login");
        loginService.authorizeLogin(context,email,password);
    }

    public void signUp(Context context,EditText email,EditText username,EditText password){
        Log.d(TAG,"attempting sign up");
        loginService.registerUser(context,email,username,password);
    }
}

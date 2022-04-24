package com.teamblue.WeBillv2.controller;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.SignUpUser;
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

    public void signUp(Context context,EditText email,EditText username,EditText password,EditText phone,EditText address,EditText gender){
        Log.d(TAG,"attempting sign up");
        SignUpUser signUpUser = new SignUpUser();
        signUpUser.setAddress((address!=null && address.getText()!=null)?address.getText().toString().trim():"");
        signUpUser.setGender((gender!=null && gender.getText()!=null)?gender.getText().toString().toLowerCase().trim():"female");
        signUpUser.setPhone((phone!=null && phone.getText()!=null)?phone.getText().toString().trim():"");
        signUpUser.setUsername(username.getText().toString().trim());
        signUpUser.setEmail(email.getText().toString().trim());
        signUpUser.setPassword(password.getText().toString().trim());
        Log.d(TAG,signUpUser.toString());
        loginService.registerUser(context,signUpUser);

    }
}

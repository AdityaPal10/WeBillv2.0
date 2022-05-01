package com.teamblue.WeBillv2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.LoginController;

public class SignUpView extends AppCompatActivity {
    private static final String TAG = SignUpView.class.getSimpleName();

    private EditText email;
    private EditText username;
    private EditText password;
    private EditText phone;
    private EditText address;
    private EditText gender;

    private Button register;
    private LoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setTitle("Sign Up");

        email = (EditText) findViewById(R.id.emailEditTextSignUp);
        username = (EditText) findViewById(R.id.usernameEditTextSignUp);
        password = (EditText) findViewById(R.id.passwordEditTextSignUp);
        phone = (EditText)findViewById(R.id.phoneEditTextSignUp);
        address = (EditText) findViewById(R.id.addressEditTextSignUp);
        gender = (EditText) findViewById(R.id.genderEditTextSignUp);
        register = (Button) findViewById(R.id.registerButtonSignUp);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username.getText().toString())) {
                    username.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(phone.getText().toString())) {
                    phone.setError("Must Not Be Empty!");
                    return;
                }
                loginController.signUp(getApplicationContext(),email,username,password,phone,address,gender);
            }
        });


    }
}

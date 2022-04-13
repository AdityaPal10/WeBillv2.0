package com.teamblue.WeBillv2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.controller.LoginController;

public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private Button signUpButton;
    private EditText emailEditText;
    private EditText passwordEditText;

    private LoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.registerButtonSignUp);
        signUpButton = (Button) findViewById(R.id.btnSignUp);
        emailEditText = (EditText) findViewById(R.id.emailEditTextSignUp);
        passwordEditText = (EditText) findViewById(R.id.usernameEditTextSignUp);

        //set on click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call login controller's login method
                loginController.login(getApplicationContext(),emailEditText,passwordEditText);
            }
        });

        //set on click listener for sign up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call login controllers sign up method
                Toast.makeText(getApplicationContext(),"Sign up",Toast.LENGTH_LONG).show();
                //move to sign up page
                segueToSignUpActivity();
            }
        });
    }

    public void segueToSignUpActivity(){
        Intent intent = new Intent(this,SignUpView.class);
        startActivity(intent);
    }

}

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
    private EditText usernameEditText;
    private EditText passwordEditText;

//    private Button btnSkip; // Skip login for developer testing (Delete after finished)

    private LoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.registerButtonSignUp);
        signUpButton = (Button) findViewById(R.id.btnSignUp);
        usernameEditText = (EditText) findViewById(R.id.emailEditTextSignUp);
        passwordEditText = (EditText) findViewById(R.id.usernameEditTextSignUp);

//        btnSkip = (Button) findViewById(R.id.btnSkip); // Skip login for developer testing (Delete after finished)

        //set on click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call login controller's login method
                loginController.login(getApplicationContext(),usernameEditText,passwordEditText);
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

//        // Skip login for developer testing (Delete after finished)
//        btnSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent goFriendPage = new Intent(MainActivity.this,FriendsView.class);
//                startActivity(goFriendPage);
//            }
//        });
    }

    public void segueToSignUpActivity(){
        Intent intent = new Intent(this,SignUpView.class);
        startActivity(intent);
    }

}

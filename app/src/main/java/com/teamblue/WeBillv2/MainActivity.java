package com.teamblue.WeBillv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.teamblue.WeBillv2.view.SIgnUpView;
import com.teamblue.WeBillv2.view.FriendsView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_example1, btn_example2, btn_example3, btn_example4;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.btnSignIn);

        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                startActivity(new Intent(MainActivity.this, FriendsView.class));
                break;

            case R.id.btnSignUp:
                startActivity(new Intent(MainActivity.this, SIgnUpView.class));
                break;
        }
    }
}

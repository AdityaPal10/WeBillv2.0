package com.teamblue.WeBillv2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;


import com.teamblue.WeBillv2.R;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class SIgnUpView extends AppCompatActivity {
    private static final String TAG = SIgnUpView.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        setTitle("Sign Up");

        animatedBottomBar = findViewById(R.id.animatedBottomBar);


    }
}

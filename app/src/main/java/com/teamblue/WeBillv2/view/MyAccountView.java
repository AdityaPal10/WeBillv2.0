package com.teamblue.WeBillv2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MyAccountView extends AppCompatActivity {
    private static final String TAG = MyAccountView.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Account");


    }
}

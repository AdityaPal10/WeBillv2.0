package com.teamblue.WeBillv2.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class AddBillView extends AppCompatActivity {
    private static final String TAG = AddBillView.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Bill");
    }
}
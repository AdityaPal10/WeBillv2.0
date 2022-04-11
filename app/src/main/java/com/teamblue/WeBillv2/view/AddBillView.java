package com.teamblue.WeBillv2.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.fragments.AccountFragment;
import com.teamblue.WeBillv2.fragments.AddBillFragment;
import com.teamblue.WeBillv2.fragments.FriendFragment;

import org.jetbrains.annotations.NotNull;

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
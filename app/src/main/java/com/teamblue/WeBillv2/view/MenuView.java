package com.teamblue.WeBillv2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.view.fragments.AccountFragment;
import com.teamblue.WeBillv2.view.fragments.AddBillFragment;
import com.teamblue.WeBillv2.view.fragments.FriendFragment;
import com.teamblue.WeBillv2.view.fragments.MapsFragment;
import com.teamblue.WeBillv2.view.fragments.ReceiptFragment;
import com.teamblue.WeBillv2.view.fragments.SpendingActivityFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MenuView extends AppCompatActivity {
    private static final String TAG = MenuView.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences tempSharedPref = getSharedPreferences(Constants.TEMP_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = tempSharedPref.edit();
        editor.clear();
        editor.apply();
        setContentView(R.layout.friends_home);
        setTitle("We Bill");




        animatedBottomBar = findViewById(R.id.animatedBottomBar);

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.friends, true);
            fragmentManager = getSupportFragmentManager();
            FriendFragment friendFragment = new FriendFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, friendFragment)
                    .commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }// optional but can't delete this for 'nl.joery.animatedbottombar:library:1.1.0' (used to be 1.0.4)

            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NotNull AnimatedBottomBar.Tab newTab) {
                Fragment fragment = null;
                switch (newTab.getId()) {
                    case R.id.friends:
                        fragment = new FriendFragment();
                        break;
                    case R.id.addbills:
                        fragment = new AddBillFragment();
                        break;
                    case R.id.account:
                        fragment = new AccountFragment();
                        break;
                    case R.id.receipt:
                        fragment = new ReceiptFragment();
                        break;
                    case R.id.spendingactivity:
                        fragment = new MapsFragment();
                        break;
                }

                if (fragment != null) {
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                            .commit();
                } else {
                    Log.e(TAG, "Error in creating Fragment");
                }
            }
        });
    }

    public void fragmentSwitcher(int fragmentTo) {
        Fragment fragment = null;
        switch (fragmentTo) {
            case R.id.friends:
                fragment = new FriendFragment();
                break;
            case R.id.addbills:
                fragment = new AddBillFragment();
                break;
            case R.id.account:
                fragment = new AccountFragment();
                break;
            case R.id.receipt:
                fragment = new ReceiptFragment();
                break;
            case R.id.spendingactivity:
                fragment = new MapsFragment();
                break;
        }

        if (fragment != null) {
//            animatedBottomBar.selectTabById(fragmentTo, true);
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();
        } else {
            Log.e(TAG, "Error in creating Fragment");
        }
    }
}

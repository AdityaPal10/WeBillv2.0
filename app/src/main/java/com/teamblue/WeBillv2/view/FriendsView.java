package com.teamblue.WeBillv2.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.view.fragments.AccountFragment;
import com.teamblue.WeBillv2.view.fragments.AddBillFragment;
import com.teamblue.WeBillv2.view.fragments.FriendChildFragment;
import com.teamblue.WeBillv2.view.fragments.FriendFragment;
import com.teamblue.WeBillv2.view.fragments.ReceiptFragment;
import com.teamblue.WeBillv2.view.fragments.SpendingActivityFragment;

import org.jetbrains.annotations.NotNull;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class FriendsView extends AppCompatActivity {
    private static final String TAG = FriendsView.class.getSimpleName();
    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
    //private Button addFriendBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_home);
        setTitle("We Bill");
        //addFriendBttn = (Button) findViewById(R.id.btn_addFriends);
        //addFriendBttn.setOnClickListener((View.OnClickListener) this);




        animatedBottomBar = findViewById(R.id.animatedBottomBar);

        if (savedInstanceState == null) {
            animatedBottomBar.selectTabById(R.id.friends, true);
            fragmentManager = getSupportFragmentManager();
            FriendFragment friendFragment = new FriendFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, friendFragment)
                    .commit();
        }

        addFriendBttn.setOnClickListener(new View.OnClickListener() {
                                             public void onClick(View v) {
                                                 Fragment fragment = null;
                                                 fragment = new FriendChildFragment();


                                             }
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
                        fragment = new SpendingActivityFragment();
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
}

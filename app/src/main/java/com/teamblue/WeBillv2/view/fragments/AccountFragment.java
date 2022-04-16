package com.teamblue.WeBillv2.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamblue.WeBillv2.R;


/**
 *  account fragment subclass.
 */
public class AccountFragment extends Fragment {

    private Button btnProfilePic;
    private Integer ImgId;
    AlertDialog dialogChooseProfilePic;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        buildProfilePicDialog(); // Ready the dialog when Account Page is loaded


        btnProfilePic = (Button) view.findViewById(R.id.btnProfilePic);
        btnProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChooseProfilePic.show();
            }
        });

        return view;
    }

    // Building Dialog for Choosing Profile Pic
    private void buildProfilePicDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        //Choose Dialog Layout here
        View dialogView = getLayoutInflater().inflate(R.layout.popup_change_profile_pic, null);

        /*********Settings for Different Profile Pictures here***********/
        Button btnX1Y1 = (Button) dialogView.findViewById(R.id.btnX1Y1);
        btnX1Y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl2); }});

        Button btnX2Y1 = (Button) dialogView.findViewById(R.id.btnX2Y1);
        btnX2Y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy1); }});

        Button btnX3Y1 = (Button) dialogView.findViewById(R.id.btnX3Y1);
        btnX3Y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy2); }});

        Button btnX1Y2 = (Button) dialogView.findViewById(R.id.btnX1Y2);
        btnX1Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl); }});

        Button btnX2Y2 = (Button) dialogView.findViewById(R.id.btnX2Y2);
        btnX2Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy4); }});

        Button btnX3Y2 = (Button) dialogView.findViewById(R.id.btnX3Y2);
        btnX3Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl3); }});

        Button btnX1Y3 = (Button) dialogView.findViewById(R.id.btnX1Y3);
        btnX1Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl4); }});

        Button btnX2Y3 = (Button) dialogView.findViewById(R.id.btnX2Y3);
        btnX2Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy5); }});

        Button btnX3Y3 = (Button) dialogView.findViewById(R.id.btnX3Y3);
        btnX3Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl5); }});


        /********Finalize Dialog Here********/
        builder.setView(dialogView); // required code to setup dialog view
        dialogChooseProfilePic = builder.create(); // finish building your dialog view
    }


}

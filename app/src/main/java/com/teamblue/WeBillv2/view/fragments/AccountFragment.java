package com.teamblue.WeBillv2.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.view.MainActivity;


/**
 *  account fragment subclass.
 */
public class AccountFragment extends Fragment {

    private Button btnProfilePic,btnLogOut,btnChangeUsername;
    private Button btnChangePassword, btnChangePhoneNumber;
    private Integer ImgId;
    private Integer savedImg;
    private String savedUsername;
    private EditText edtChangeUserName;
    private TextView tvAccountPageUsername;
    AlertDialog dialogChooseProfilePic,dialogLogout,dialogChangeUsername;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        tvAccountPageUsername = view.findViewById(R.id.tvAccountPageUsername);

        /******Dialog for Changing Profile Pics******/
        buildProfilePicDialog(); // build contents here
        btnProfilePic = (Button) view.findViewById(R.id.btnProfilePic);
        btnProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChooseProfilePic.show();
            }
        });
        // build contents here
        btnChangePassword= (Button) view.findViewById(R.id.btnResetPassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildChangePasswordDialog();
            }
        });
        btnChangePhoneNumber= (Button) view.findViewById(R.id.btnModifyMobileNumber);
        btnChangePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePhoneNumber();
            }
        });


        /******Dialog for User Logout ****************/
        buildUserLogoutDialog();// build contents here
        btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogLogout.show();
            }
        });


//        buildChangeUsernameDialog();// build contents here
//        btnChangeUsername = (Button) view.findViewById(R.id.btnChangeUsername);
//        btnChangeUsername.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogChangeUsername.show();
//            }
//        });




        loadData(); //sharedPreference load saved data
        return view;
    }

    private void buildChangePasswordDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
        alertDialog.setTitle("Values");
        final EditText oldPass = new EditText(this.getContext());
        final EditText newPass = new EditText(this.getContext());
        final EditText confirmPass = new EditText(this.getContext());


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(this.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = alertDialog.create();
        alert11.show();
    }

    private void changePhoneNumber() {

    }

    private void changePassword() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        //Choose Dialog Layout here
        View dialogView = getLayoutInflater().inflate(R.layout.popup_logout, null);

        builder.setView(dialogView);
        builder.setTitle("Are you sure to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(dialogView.getContext(), "We'll miss you ❤️", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialogLogout = builder.create();

    }

//    private void buildChangeUsernameDialog() {
//        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
//        //Choose Dialog Layout here
//        View dialogView = getLayoutInflater().inflate(R.layout.popup_change_username, null);
//
//        edtChangeUserName = dialogView.findViewById(R.id.edtChangeUserName);
//
//        builder.setView(dialogView);
//        builder.setTitle("Change Your Username Here")
//                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        tvAccountPageUsername.setText(edtChangeUserName.getText().toString()); //set new username to textview
//                        savedUsername = edtChangeUserName.getText().toString(); //save new username to sharedPreference
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        dialogChangeUsername = builder.create();
//    }

    private void buildUserLogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        //Choose Dialog Layout here
        View dialogView = getLayoutInflater().inflate(R.layout.popup_logout, null);

        builder.setView(dialogView);
        builder.setTitle("Are you sure to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(dialogView.getContext(), "We'll miss you ❤️", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialogLogout = builder.create();
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
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl2); ImgId = R.drawable.girl2;}});

        Button btnX2Y1 = (Button) dialogView.findViewById(R.id.btnX2Y1);
        btnX2Y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy1); ImgId = R.drawable.boy1;}});

        Button btnX3Y1 = (Button) dialogView.findViewById(R.id.btnX3Y1);
        btnX3Y1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy2); ImgId = R.drawable.boy2;}});

        Button btnX1Y2 = (Button) dialogView.findViewById(R.id.btnX1Y2);
        btnX1Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl); ImgId = R.drawable.girl;}});

        Button btnX2Y2 = (Button) dialogView.findViewById(R.id.btnX2Y2);
        btnX2Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy4); ImgId = R.drawable.boy4;}});

        Button btnX3Y2 = (Button) dialogView.findViewById(R.id.btnX3Y2);
        btnX3Y2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl3); ImgId = R.drawable.girl3;}});

        Button btnX1Y3 = (Button) dialogView.findViewById(R.id.btnX1Y3);
        btnX1Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl4); ImgId = R.drawable.girl4;}});

        Button btnX2Y3 = (Button) dialogView.findViewById(R.id.btnX2Y3);
        btnX2Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.boy5); ImgId = R.drawable.boy5;}});

        Button btnX3Y3 = (Button) dialogView.findViewById(R.id.btnX3Y3);
        btnX3Y3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { btnProfilePic.setBackgroundResource(R.drawable.girl5); ImgId = R.drawable.girl5;}});


        /********Finalize Dialog Here********/
        builder.setView(dialogView); // required code to setup dialog view
        dialogChooseProfilePic = builder.create(); // finish building your dialog view
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("IMGID",ImgId);
//    }

    @Override
    public void onStop() {
        super.onStop();
        saveData();

    }


    public void saveData(){
        //create a shared preferences object, on private mode means no other app can modify the data
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IMGID",ImgId);
        editor.putString("USERNAME",savedUsername);
        editor.apply();
//        Toast.makeText(this.getContext(), "Saved data", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        savedImg = sharedPreferences.getInt("IMGID",R.drawable.girl);
        savedUsername = sharedPreferences.getString(Constants.USERNAME_KEY,"Guest");
        tvAccountPageUsername.setText(savedUsername);
        btnProfilePic.setBackgroundResource(savedImg);
        ImgId = savedImg;
//        Toast.makeText(this.getContext(), "Loaded Data", Toast.LENGTH_SHORT).show();
    }
}

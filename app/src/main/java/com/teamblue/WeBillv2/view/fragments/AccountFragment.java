package com.teamblue.WeBillv2.view.fragments;

import static android.content.ContentValues.TAG;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ReplacementTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.api.AccountsMethods;
import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.ModifyPasswordModel;
import com.teamblue.WeBillv2.service.LoginRetrofitClient;
import com.teamblue.WeBillv2.service.ModifyPasswordService;
import com.teamblue.WeBillv2.service.ModifyPhoneNumberService;
import com.teamblue.WeBillv2.view.MainActivity;

import java.time.Year;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *  account fragment subclass.
 */
public class AccountFragment extends Fragment {

    private Button btnProfilePic,btnLogOut,btnChangeUsername;
    private Button btnChangePassword, btnChangePhoneNumber, btnContactUs;
    private Spinner spnrYearFilter;
    private Integer ImgId;
    private Integer savedImg;
    private String savedUsername;
    private EditText edtChangeUserName;
    private TextView tvAccountPageUsername;
    private boolean viewExists;
    AlertDialog dialogChooseProfilePic,dialogLogout,dialogChangeUsername;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        viewExists = false;
        tvAccountPageUsername = view.findViewById(R.id.tvAccountPageUsername);

        spnrYearFilter = (Spinner) view.findViewById(R.id.spnrYearFilter);// since spinner is inside myDialog, don't use view.getContext()
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.years,
                android.R.layout.simple_spinner_item);

        System.out.println(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrYearFilter.setAdapter(adapter);
        spnrYearFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if(viewExists){
                    String year = adapterView.getItemAtPosition(pos).toString();
                    setAppYear(year);
                }
                else{
                    spnrYearFilter.setSelection(22);
                    viewExists = true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                setDefaultAppYear();
            }
        });

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
                buildChangePhoneNumberDialog();
            }
        });

        //1. Contact Us button opens email intent and directs you to the developers through your email cient.
        // the user only needs to enter their inquiry and hit send
        btnContactUs= (Button) view.findViewById(R.id.btnContactUs);
        btnContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Send email", "");

                String[] TO = {"pal.aditya.ap@gmail.com"};
                //String[] CC = {"harsh@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");


                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                //emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WeBill Contact Developers");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter your inquiry here");
                //in case there is no email client installed on the phone, it wil catch the exception
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished sending", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(),
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

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

    //build a dialogue to change phone number
    //will check if the phone number is a valid string
    //will check if the new phone number and confirm phone number match

    private void buildChangePhoneNumberDialog() {
        AlertDialog.Builder phoneNumberDialog = new AlertDialog.Builder(this.getContext());
        phoneNumberDialog.setTitle("Modify Phone Number");
        final EditText oldPhoneNumber = new EditText(this.getContext());
        final EditText newPhoneNumber = new EditText(this.getContext());
        final EditText confirmPhoneNumber = new EditText(this.getContext());

        oldPhoneNumber.setHint("Old Phone Number");
        newPhoneNumber.setHint("New Phone Number");
        confirmPhoneNumber.setHint("Confirm Phone Number");
        LinearLayout ll=new LinearLayout(this.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPhoneNumber);

        ll.addView(newPhoneNumber);
        ll.addView(confirmPhoneNumber);
        phoneNumberDialog.setView(ll);

        //the entered phone number should start with + and should have only digits and length should be between 10 and 13 characters
        phoneNumberDialog.setPositiveButton("RESET PHONE NUMBER",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String regexStr = "^\\+[0-9]{10,13}$";
                        String number=newPhoneNumber.getText().toString();
                        if(number.matches(regexStr)==false  ) {
                            Toast.makeText(phoneNumberDialog.getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        }
                         else if (!newPhoneNumber.getEditableText().toString().equals(confirmPhoneNumber.getEditableText().toString())) {
                                Toast.makeText(phoneNumberDialog.getContext(), "new phone number and confirm number don't match", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //will modify the phone number from the backend
                                SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                                String loggedInUsername = sharedPref.getString(Constants.USERNAME_KEY,"");
                            ModifyPhoneNumberService modifyPhoneNumberService=new ModifyPhoneNumberService();
                            modifyPhoneNumberService.updatePhoneNumber(getContext(), loggedInUsername, oldPhoneNumber.toString(), newPhoneNumber.toString());
                        }
                        dialog.cancel();
                    }
                });
        phoneNumberDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog resetPhoneNumber = phoneNumberDialog.create();
        resetPhoneNumber.show();
    }
    //method to change password of the user
    private void buildChangePasswordDialog() {
        AlertDialog.Builder passwordDialog = new AlertDialog.Builder(this.getContext());
        passwordDialog.setTitle("RESET PASSWORD");
        final EditText oldPass = new EditText(this.getContext());
        final EditText newPass = new EditText(this.getContext());
        final EditText confirmPass = new EditText(this.getContext());


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //prompt the user to enter the old password and the new password and confirm the new password
        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(this.getContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        passwordDialog.setView(ll);
        passwordDialog.setPositiveButton("RESET PASSWORD",
                new DialogInterface.OnClickListener() {
                    //1. method to first verify if the new and confirm password match
                    //2. then check that the old password entered is correct
                    //3. check if the new password is not the same as the old password
                    //4. update the password in the backend if every condition is passed

                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
                        if (!newPass.getEditableText().toString().equals(confirmPass.getEditableText().toString())){
                            Toast.makeText(passwordDialog.getContext(), "new and confirm password don't match", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }

                        else if (!oldPass.getEditableText().toString().equals(sharedPreferences.getString(Constants.PASSWORD_KEY,""))) {
                            Toast.makeText(passwordDialog.getContext(), "old password is not correct", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else if (newPass.getEditableText().toString().equals(oldPass.getEditableText().toString())) {
                            Toast.makeText(passwordDialog.getContext(), "new password cannot be the same as old password", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else {
                            SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
                            String loggedInUsername = sharedPref.getString(Constants.USERNAME_KEY,"");
                            ModifyPasswordService modifyPasswordService=new ModifyPasswordService();
                            modifyPasswordService.updatePassword(getContext(), loggedInUsername, newPass.getText().toString());
                            Toast.makeText(passwordDialog.getContext(), "password is changed", Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                });

        //cancel button for reset password
        passwordDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        //create the reset password dialogue

        AlertDialog resetPassword = passwordDialog.create();
        resetPassword.show();
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
// build logout dialog
    private void buildUserLogoutDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this.getContext());
        //Choose Dialog Layout here
        View dialogView = getLayoutInflater().inflate(R.layout.popup_logout, null);

        builder.setView(dialogView);
        builder.setTitle("Are you sure to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(dialogView.getContext(), "We'll miss you ??????", Toast.LENGTH_SHORT).show();
                        setDefaultAppYear();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                //cancel button for closing the logout prompt
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialogLogout = builder.create();
    }

    // Building Dialog for Choosing Profile Pic
    //allows the user to change the profile pic and choose from a variety of clip arts
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

//save all the data in shared preferences to use later in the app
    public void saveData(){
        //create a shared preferences object, on private mode means no other app can modify the data
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IMGID",ImgId);
        editor.putString("USERNAME",savedUsername);
        editor.apply();
//        Toast.makeText(this.getContext(), "Saved data", Toast.LENGTH_SHORT).show();
    }

    //load data from the shared preferences
    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        savedImg = sharedPreferences.getInt("IMGID",R.drawable.girl);
        savedUsername = sharedPreferences.getString(Constants.USERNAME_KEY,"Guest");
        tvAccountPageUsername.setText(savedUsername);
        btnProfilePic.setBackgroundResource(savedImg);
        ImgId = savedImg;
//        Toast.makeText(this.getContext(), "Loaded Data", Toast.LENGTH_SHORT).show();
    }

    //Set the application's filter year to current year.
    public void setDefaultAppYear(){
        String currentYear = Integer.toString(Year.now().getValue());
        //set it to preferences
        SharedPreferences sharedPreferences = getActivity().getBaseContext().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.FILTER_YEAR,currentYear);
        editor.apply();
        Log.d("=======TAG======",(sharedPreferences.getString(Constants.FILTER_YEAR,Integer.toString(Year.now().getValue()))));
    }

    //Sets the application's filter year to the year selected in the Account Tab.
    public void setAppYear(String year){
        //set it to preferences
        SharedPreferences sharedPreferences = getActivity().getBaseContext().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.FILTER_YEAR,year);
        editor.apply();
        Log.d("=======TAG======",(sharedPreferences.getString(Constants.FILTER_YEAR,Integer.toString(Year.now().getValue()))));
    }
}

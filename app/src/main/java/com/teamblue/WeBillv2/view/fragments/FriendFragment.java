package com.teamblue.WeBillv2.view.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.teamblue.WeBillv2.R;


/**
 * friend fragment subclass.
 */
public class FriendFragment extends Fragment {

    private Button btnFilterMonth,btnAddCard;
    private Spinner spinnerMonths;
    private Dialog filterDialog;
    private Dialog addFriendDialog;
    private Dialog friendDetailsDialog;

    LinearLayout friendContainer;


    public FriendFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friendsdempty, container, false);

        //1 handling filter button and its pop up window
        filterDialog = new Dialog(this.getContext());
        filterDialog.setContentView(R.layout.popup_receipt_filter);
        btnFilterMonth = (Button) view.findViewById(R.id.btnFilterMonth);
        btnFilterMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.show();
            }
        });

        //2 Basic settings for spinner inside a Fragment
       /* spinnerMonths = (Spinner) filterDialog.findViewById(R.id.spinnerMonths);// since spinner is inside myDialog, don't use view.getContext()
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.months,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapter);
        spinnerMonths.setOnItemSelectedListener(this);*/

        //3 handling the receiptContainer
        btnAddCard = (Button) view.findViewById(R.id.btnAddFriendCard);
        friendContainer = (LinearLayout) view.findViewById(R.id.friendContainer);

        buildDialog();// building the layout for pop-up window and wait for call
        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFriendDialog.show();
            }
        });

        return view;
    }

    //2.1 Do spinner's functions here, now a demo of showing Toast text when select a month
   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textMonth = spinnerMonths.getSelectedItem().toString();
        Toast.makeText(this.getContext(), textMonth, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }*/

    //3.1 add Friend Dialog
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View view = getLayoutInflater().inflate(R.layout.popup_friend_info_fill, null);

        EditText edtFriendName = view.findViewById(R.id.edtfriendName);

        builder.setView(view);
        builder.setTitle("Enter Friend Details")
                .setPositiveButton("Add Friend", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(edtFriendName.getText().toString(), "0", "NONE");
                    }
                                //edtfriendAmount.getText().toString(),
                                //edtDirection.getText().toString());


                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        addFriendDialog = builder.create();
    }
    //3.2 once finished entering the information, pass them into the current new Friend cardView
    private void addCard(String friendName, String friendAmount, String friendDirection) {
        View view = getLayoutInflater().inflate(R.layout.cardview_friend, null);

        TextView tvFriendName = view.findViewById(R.id.tvFriendName);
        TextView tvFriendAmount = view.findViewById(R.id.tvFriendAmount);
        TextView tvDirection = view.findViewById(R.id.tvDirection);

        Button btnRemoveFriendCard = view.findViewById(R.id.btnRemoveFriendCard);
        Button btnFriendDetails = view.findViewById(R.id.btnFriendDetails);

        //4 handing the friends details button
        btnFriendDetails.setOnClickListener(new View.OnClickListener() { // when user click "details" at friend cardView
            @Override
            public void onClick(View view) {
                buildBillDetails(friendName,friendAmount,friendDirection); // pass any information we want to the pop up window
                friendDetailsDialog.show();
            }

        });


        btnRemoveFriendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendContainer.removeView(view);
            }
        });
        tvFriendName.setText(friendName);
        tvFriendAmount.setText(friendAmount);
        tvDirection.setText(friendDirection);
        friendContainer.addView(view);
    }

    //4.1 Render the friends details pop up windows here
    private void buildBillDetails(String BillAmount,String ActivityName,String Date){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View viewFriendDetails = getLayoutInflater().inflate(R.layout.popup_receipt_click_details, null);

        TextView tvTotal = viewFriendDetails.findViewById(R.id.tvTotal);
        TextView tvActivityText = viewFriendDetails.findViewById(R.id.tvActivityText);
        TextView tvDate = viewFriendDetails.findViewById(R.id.tvDate);

        builder.setView(viewFriendDetails);

        tvTotal.setText(BillAmount);
        tvActivityText.setText(ActivityName);
        tvDate.setText(Date);
        friendDetailsDialog = builder.create();
    }


}
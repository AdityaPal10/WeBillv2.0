package com.teamblue.WeBillv2.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.teamblue.WeBillv2.R;
import java.util.ArrayList;


/**
 * friend fragment subclass.
 */
public class FriendFragment extends Fragment {

    private Button btnAddFriends;

    AlertDialog dialogAddNewFriend;
    LinearLayout containerFriendCards;

    Context context;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        btnAddFriends = view.findViewById(R.id.btnAddFriends);
        containerFriendCards = view.findViewById(R.id.containerFriendCards);

        /*********Call Your Add Friend Dialog here********/
        buildAddNewFriendDialog();
        btnAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddNewFriend.show();
            }
        });


        return view;
    }

    /*********Build and Custom Your Add Friend Dialog here********/
    private void buildAddNewFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        View viewDialogAddFriend = getLayoutInflater().inflate(R.layout.popup_add_new_friend, null);

        EditText edtAddFriendUserName = viewDialogAddFriend.findViewById(R.id.edtAddFriendUserName);

        builder.setView(viewDialogAddFriend);
        builder.setTitle("Enter Friend Username")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.isEmpty(edtAddFriendUserName.getText().toString())) { // check no empty inputs
                            edtAddFriendUserName.setError("Must Not Be Empty!");
                            return;
                        }
                        addNewFriendCard(edtAddFriendUserName.getText().toString()); //Pass addCard Parameters, you can add more parameters to addCard method

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialogAddNewFriend = builder.create(); // Once setting up the Dialog, build this to dialogAddNewFriend
    }


    /*********Build A single Friend Card Here everytime you add a new friend,
     * You can also custom more information for this friend here.   ********/
    private void addNewFriendCard(String Username) {
        View viewNewFriendCard = getLayoutInflater().inflate(R.layout.cardview_new_friend, null); // inflate your new friend card view
        TextView tvFriendName = viewNewFriendCard.findViewById(R.id.tvFriendName);
        tvFriendName.setText(Username);

        /*******Right Now I just consider Initial Status of a friend card as Balance:0.00 , @+id/btnFriendStatus background as @drawable/credit_card********/

        /*******TODO: Add Friends Balance Status Here
         * Case 1:
         * If User Owes Money to a friend:
         * Set the @id/tvFriendStatus to: "You Owe: $" , !!!color #EC0D5C!!!
         * Set @+id/tvFriendBalance to Specific Dollar amount e.g. 10.00  , !!!color #EC0D5C!!!
         * Set @+id/btnFriendStatus background to @drawable/bell
         *
         * Case 2 : If User Needs to take back money from friend:
         * Set the @id/tvFriendStatus to: "Owes You: $" , !!!color #138806!!!
         * Set @+id/tvFriendBalance to Specific Dollar amount e.g. 10.00  , !!!color #138806!!! *******/


        /******TODO: Set user's correct Profilc Pic in the future from backend *******/

        /*******Original example is in z_demo_initial_friend_page.xml ********/


        containerFriendCards.addView(viewNewFriendCard);
    }


    /*******Original code********/
//    public class CustomAdapter extends BaseAdapter {
//
//        ArrayList<Integer> friendAvatar;
//        ArrayList<String> friendNames;
//        ArrayList<Integer> paymentDirection;
//        ArrayList<Double> paymentAmount;
//        ArrayList<Integer> buttonAction;
//
//        /*
//            1. make network call using retrofit
//            2. from response received populate lists
//            3. set context=acontext
//             */
//        public CustomAdapter(Context acontext){
//            context = acontext;
//        }
//
//
//        @Override
//        public int getCount() {
//            return friendNames.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//
//            return friendNames.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View view, ViewGroup viewGroup) {
//
//            View row;
//            if (view==null){
//                LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                row = inflater.inflate(R.layout.friends_ledger_row, viewGroup, false);
//            }
//            else{
//                row=view;
//            }
//            Button bttnLedger=(Button) row.findViewById(R.id.bttnLedger);
//            TextView ledgerName=(TextView) row.findViewById(R.id.friendNameLedger);
//            TextView ledgerAmount=(TextView) row.findViewById(R.id.amountLedger);
//            TextView ledgerDirection=(TextView) row.findViewById(R.id.paymentDirectionLedger);
//            ImageView imageView = (ImageView)row.findViewById(R.id.imageViewLedger);
//
//            ledgerName.setText(friendNames.get(position));
//            ledgerAmount.setText(String.valueOf(paymentAmount.get(position)));
//            ledgerDirection.setText(paymentDirection.get(position)==0?"Owes You:":"You Owe:");
//            bttnLedger.setText(paymentDirection.get(position)==0?"Remind":"Pay");
//            //imageView.setImageDrawable(R.);
//
//            bttnLedger.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getContext(), "Amount paid:", Toast.LENGTH_LONG).show();
//                }
//            });
//
//            return row;
//        }
//    }
}
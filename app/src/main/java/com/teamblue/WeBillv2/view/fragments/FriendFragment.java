package com.teamblue.WeBillv2.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.teamblue.WeBillv2.R;
import java.util.ArrayList;


/**
 * friend fragment subclass.
 */
public class FriendFragment extends Fragment {

    Context context;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }
    public class CustomAdapter extends BaseAdapter {

        ArrayList<Integer> friendAvatar;
        ArrayList<String> friendNames;
        ArrayList<Integer> paymentDirection;
        ArrayList<Double> paymentAmount;
        ArrayList<Integer> buttonAction;

        /*
            1. make network call using retrofit
            2. from response received populate lists
            3. set context=acontext
             */
        public CustomAdapter(Context acontext){
            context = acontext;
        }



        @Override
        public int getCount() {
            return friendNames.size();
        }

        @Override
        public Object getItem(int position) {

            return friendNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            View row;
            if (view==null){
                LayoutInflater inflater=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.friends_ledger_row, viewGroup, false);
            }
            else{
                row=view;
            }
            Button bttnLedger=(Button) row.findViewById(R.id.bttnLedger);
            TextView ledgerName=(TextView) row.findViewById(R.id.friendNameLedger);
            TextView ledgerAmount=(TextView) row.findViewById(R.id.amountLedger);
            TextView ledgerDirection=(TextView) row.findViewById(R.id.paymentDirectionLedger);
            ImageView imageView = (ImageView)row.findViewById(R.id.imageViewLedger);

            ledgerName.setText(friendNames.get(position));
            ledgerAmount.setText(String.valueOf(paymentAmount.get(position)));
            ledgerDirection.setText(paymentDirection.get(position)==0?"Owes You:":"You Owe:");
            bttnLedger.setText(paymentDirection.get(position)==0?"Remind":"Pay");
            //imageView.setImageDrawable(R.);

            bttnLedger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Amount paid:", Toast.LENGTH_LONG).show();
                }
            });

            return row;
        }
    }
}
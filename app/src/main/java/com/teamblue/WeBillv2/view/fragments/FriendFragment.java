package com.teamblue.WeBillv2.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

    Context context;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friendsempty, container, false);

    }

    public class CustomAdapter extends BaseAdapter {

        public ArrayList<Integer> friendAvatar;
        public ArrayList<String> friendNames= new ArrayList<String>();
        public ArrayList<Integer> paymentDirection;
        public ArrayList<String> paymentAmount;
        public ArrayList<Integer> buttonAction;
        public Button btnAdd_Friends;


        /*
            1. make network call using retrofit
            2. from response received populate lists
            3. set context=acontext
             */
        public CustomAdapter(Context acontext) {
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
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.friends_ledger_row, viewGroup, false);
            } else {
                row = view;
            }
            Button bttnLedger = (Button) row.findViewById(R.id.bttnLedger);
            TextView ledgerName = (TextView) row.findViewById(R.id.friendNameLedger);
            TextView ledgerAmount = (TextView) row.findViewById(R.id.amountLedger);
            TextView ledgerDirection = (TextView) row.findViewById(R.id.paymentDirectionLedger);
            ImageView imageView = (ImageView) row.findViewById(R.id.imageViewLedger);


            ledgerName.setText(friendNames.get(position));
            ledgerAmount.setText(String.valueOf(paymentAmount.get(position)));
            ledgerDirection.setText(paymentDirection.get(position) == 0 ? "Owes You:" : "You Owe:");
            bttnLedger.setText(paymentDirection.get(position) == 0 ? "Remind" : "Pay");
            //imageView.setImageDrawable(R.);

            bttnLedger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Amount paid:", Toast.LENGTH_LONG).show();
                }
            });

            return row;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_friendName, tv_amount;
            private LinearLayout title;

            public MyViewHolder(View row) {
                super(row);
                title = (LinearLayout) row.findViewById(R.id.title);
                tv_friendName = (TextView) row.findViewById(R.id.friendNameLedger);
                tv_amount = (TextView) row.findViewById(R.id.amountLedger);
            }
        }


        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_row_data, parent, false);

            return new MyViewHolder(itemView);
        }


        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.tv_friendName.setText(friendNames.get(position));
            holder.tv_amount.setText(paymentAmount.get(position));
            for (int i = 0; i < 5; i++) { // 5 is the count how many times you want to add textview
                ViewGroup.LayoutParams lparams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(holder.title.getContext());
                tv.setLayoutParams(lparams);
                tv.setText("test = " + i);
                holder.title.addView(tv);
            }

        }


        public int getItemCount() {
            return friendNames.size();
        }

    }
}


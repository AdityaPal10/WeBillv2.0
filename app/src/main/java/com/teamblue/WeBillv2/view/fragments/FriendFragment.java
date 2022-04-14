package com.teamblue.WeBillv2.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamblue.WeBillv2.R;


/**
 * friend fragment subclass.
 */
public class FriendFragment<addFriendBttn> extends Fragment {

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }
    public class FragmentInsideFragmentTestActivity extends Activity {

        private Button addFriendBttn;
        addFriendBttn = (Button) findViewById(R.id.btn_addFriends);


        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);

            button1 =(Button) this.findViewById(R.id.button1);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onButtonClick(view);
                }
            });

            button2 =(Button) this.findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onButtonClick(view);
                }
            });

            button3 =(Button) this.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onButtonClick(view);
                }
            });

            button4 =(Button) this.findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onButtonClick(view);
                }
            });
        }

        public void onButtonClick(View v) {
            Fragment fg;

            switch (v.getId()) {
                case R.id.button1:
                    fg=FirstFragment.newInstance();
                    replaceFragment(fg);
                    break;
                case R.id.button2:
                    fg=SecondFragment.newInstance();
                    replaceFragment(fg);
                    break;
                case R.id.button3:
                    fg=FirstFragment.newInstance();
                    replaceFragment(fg);
                    break;
                case R.id.button4:
                    fg=SecondFragment.newInstance();
                    replaceFragment(fg);
                    break;
            }
        }

        private void replaceFragment(Fragment newFragment) {
            FragmentTransaction trasection = getFragmentManager().beginTransaction();

            if(!newFragment.isAdded()) {
                try {
                    //FragmentTransaction trasection =
                    getFragmentManager().beginTransaction();
                    trasection.replace(R.id.linearLayout2, newFragment);
                    trasection.addToBackStack(null);
                    trasection.commit();
                } catch (Exception e) {
                    // TODO: handle exception
                    // AppConstants.printLog(e.getMessage());
                } else {
                    trasection.show(newFragment);
                }
            }
        }
}

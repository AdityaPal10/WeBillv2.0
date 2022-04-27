package com.teamblue.WeBillv2.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.api.FriendRequest;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.service.FriendService;
import com.teamblue.WeBillv2.service.LoginRetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * friend fragment subclass.
 */
public class FriendFragment extends Fragment {

    private Button btnAddFriends;

    AlertDialog dialogAddNewFriend;
    LinearLayout containerFriendCards;
    private String TAG = "Friend";

    Context context;

    public FriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        TextView tvToPay = view.findViewById(R.id.tvMyToPayNumber);
        TextView tvToTakeBack = view.findViewById(R.id.tvToTakeBackNumber);

        btnAddFriends = view.findViewById(R.id.btnAddFriends);
        containerFriendCards = view.findViewById(R.id.containerFriendCards);

        context = getActivity().getApplicationContext();

        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("FriendCardFinished",false);

        String username = sharedPreferences.getString(Constants.USERNAME_KEY,"");

        //make network call to fetch overall balance of logged in user
        FriendService friendService = new FriendService();
        friendService.getBalance(view,username,tvToPay,tvToTakeBack);

        //make network call to populate friends status/breakdown
        getFriendBalances(context,username);

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
        SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
       // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue);
        String loggedInUsername = sharedPref.getString(Constants.USERNAME_KEY,"");
        //FriendService friendService=new FriendService();
        //friendService.addFriend(context,loggedInUsername ,Username);
        add(context,loggedInUsername,Username);
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


       // containerFriendCards.addView(viewNewFriendCard);
    }

    public void getFriendBalances(Context context,String username){
        //1. create an instance of friend methods interface defined in our FriendMethods class
        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
        Call<List<FriendBalanceModel>> call = friendMethods.getFriendsBreakdown(username);

        call.enqueue(new Callback<List<FriendBalanceModel>>() {
            @Override
            public void onResponse(Call<List<FriendBalanceModel>> call, Response<List<FriendBalanceModel>> response) {
                Log.d(TAG,"response code :"+response.code());

                if(response.code()==Constants.RESPONSE_OK){
                    List<FriendBalanceModel> friendBalanceModels = response.body();
                    System.out.println(friendBalanceModels.toString());

                    for(FriendBalanceModel friendBalanceModel : friendBalanceModels){
                        //add each friend balance model to cards

                        //1. get view to card fragment
                        View newCard = getLayoutInflater().inflate(R.layout.cardview_new_friend,null);
                        TextView tvFriendName = newCard.findViewById(R.id.tvFriendName);
                        TextView tvBalance = newCard.findViewById(R.id.tvFriendBalance);
                        TextView tvStatus = newCard.findViewById(R.id.tvFriendStatus);
                        double balance = friendBalanceModel.getAmountOwed()-friendBalanceModel.getAmountToPay();
                        if(balance<0){
                            tvStatus.setText("To pay : $");
                            tvStatus.setTextColor(getResources().getColor(R.color.takeBackRed));
                            tvBalance.setTextColor(getResources().getColor(R.color.takeBackRed));
                        }else if(balance>0){
                            tvStatus.setText("To take back : $");
                            tvStatus.setTextColor(getResources().getColor(R.color.quantum_googgreen));
                        }else{
                            continue;
                        }
                        tvFriendName.setText(friendBalanceModel.getFriend_username());
                        tvBalance.setText(String.valueOf(Math.abs(balance)));

                        //add new card to existing container
                        containerFriendCards.addView(newCard);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendBalanceModel>> call, Throwable t) {
                //error getting cards
                Log.d(TAG,t.getMessage());
                Toast.makeText(getContext(),"error fetching data",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void add(Context context, String username, String friendName){

        //1. create an instance of friend methods interface defined in our FriendMethods class
        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username and friendName as paramaters
        Call<LoginModel> call = friendMethods.addFriend(new FriendRequest(username,friendName));
        Log.d(TAG,friendName.toString().trim());

        /*3. create a callback for our call object, once its finished the network call, it will use this callback to further
           process whether the network call was successful or not.
         */
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d(TAG,String.valueOf(response.code()));
                System.out.println(response.code());
                //getting response body if call was successful
                if(response.code()== Constants.RESPONSE_OK){
                    LoginModel apiResponse = (LoginModel) response.body();
                    System.out.println(apiResponse.getStatus());
                    //case 1: when successfully added friend
                    Toast.makeText(context,"successfully added friend",Toast.LENGTH_LONG).show();
                    View viewNewFriendCard = getLayoutInflater().inflate(R.layout.cardview_new_friend, null);
                    TextView tvFriendName= viewNewFriendCard.findViewById(R.id.tvFriendName);
                    tvFriendName.setText(friendName);
                    containerFriendCards.addView(viewNewFriendCard);
                }else{
                    switch (response.code()){
                        case Constants.FRIEND_EXISTS:
                            Toast.makeText(context,"Friend already exists",Toast.LENGTH_LONG).show();
                            break;
                        case Constants.NO_FRIEND_EXISTS:
                            Toast.makeText(context,"Friend doesn't have an accout",Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Toast.makeText(context,"Error adding friend, please try again",Toast.LENGTH_LONG).show();
                    }
                    Log.d(TAG,"Error adding friend,please try again");
                    //friendUsername.setText("");
                    //Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d(TAG,"Error adding friend,please try again");
                //friendUsername.setText("");
                Toast.makeText(context,"Error adding friend,please try again",Toast.LENGTH_LONG).show();
            }
        });

    }

}
package com.teamblue.WeBillv2.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.content.SharedPreferences;
import android.icu.number.Precision;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.teamblue.WeBillv2.BuildConfig;
import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.api.SplitBillMethods;
import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.model.pojo.LoginModel;
import com.teamblue.WeBillv2.model.pojo.SplitBillModel;
import com.teamblue.WeBillv2.service.LoginRetrofitClient;
import com.teamblue.WeBillv2.model.adapter.LineItemsAdapter;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LineItems;
import com.teamblue.WeBillv2.model.pojo.VeryfiOcrResponse;
import com.teamblue.WeBillv2.view.fragments.AddBillFragment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplitBillActivity extends AppCompatActivity {

    private static final String TAG = "SPLIT";

    private static int AUTOCOMPLETE_REQUEST_CODE = 7001;
    private EditText edtActivityNameSplitBill, edtTotalAmountSplitBill, edtDateSplitBill, edtAddressSplitBill;
    private Spinner edtPayerName;
    private TextView tvRemainAmount, tvTotalAmountSplitBill;
    private String getActivityName, getTotalAmount, getDate, getAddress;
    private String veryfiResponseString;
    private Button btnAddSplitFriend;
    private Button btnSaveBill;
    private Button btnDatePickerSplitBill;
    private Double RemainAmount, CurrentAmount, TotalAmount;
    private Button lineItemsButton;
    private DatePickerDialog datePickerDialog;
    LinearLayout LinearFriendSplit;
    AlertDialog AddSplitFriendDialog, ItemsDialog;

    List<LineItems> lineItems;

    private String username;
    private List<String> allFriendsList;
    private HashMap<String, Double> selectedFriendsMap;
    private String selectedFriend, selectedPayerName;
    private boolean isFriendSelected;
    private ArrayAdapter<String> spinnerArrayAdapter, paidByArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(Constants.USERNAME_KEY, "");

        veryfiResponseString = (String)getIntent().getExtras().get(Constants.VERYI_RESPONSE_KEY);

        allFriendsList = new ArrayList<>();
        allFriendsList.add("");
        allFriendsList.add(username);
        selectedFriendsMap = new HashMap();
        spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paidByArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        paidByArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getAllFriends(this);

        // Initialize the Google Maps Places SDK and create PlacesClient instance
        // we need this for the autocomplete feature in the address field
        if (!Places.isInitialized())
            Places.initialize(getBaseContext(), BuildConfig.MAPS_API_KEY);
        PlacesClient placesClient = Places.createClient(getBaseContext());

        edtActivityNameSplitBill = (EditText) findViewById(R.id.edtActivityNameSplitBill);
        btnDatePickerSplitBill = (Button) findViewById(R.id.btnDatePickerSplitBill);
        edtAddressSplitBill = (EditText) findViewById(R.id.edtAddressSplitBill);
        //getting logged in username to set default payer
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
//        edtPayerName = (EditText) findViewById(R.id.edtPayerName);
//        edtPayerName.setText(sharedPref.getString(Constants.USERNAME_KEY,""));
        edtPayerName = (Spinner) findViewById(R.id.edtPayerName);
        tvTotalAmountSplitBill = (TextView) findViewById(R.id.tvTotalAmountSplitBill);
        tvRemainAmount = (TextView) findViewById(R.id.tvRemainAmount);
        LinearFriendSplit = findViewById(R.id.LinearFriendSplit);
        btnSaveBill = (Button) findViewById(R.id.btnSaveBill);
        lineItemsButton = (Button) findViewById(R.id.lineItemsPopupButton);

        initDatePicker();
        //TODO:Re-enable item dialog after test.
        buildItemsDialog();


        /******** Show Items Dialog *********/
        //TODO:Re-enable item dialog after test.
        lineItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemsDialog.show();
            }
        });

        edtPayerName.setAdapter(paidByArrayAdapter);
        edtPayerName.setSelection(0);

        edtPayerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedPayerName = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedPayerName = "";
            }
        });

        /******** Check Final Bill Information Completeness *********/
        btnSaveBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edtActivityNameSplitBill.getText().toString())) {
                    edtActivityNameSplitBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(btnDatePickerSplitBill.getText().toString())){
                    return;
//                }else if(TextUtils.isEmpty(edtDateSplitBill.getText().toString())) {
//                    edtDateSplitBill.setError("Must Not Be Empty!");
//                    return;
                } else if (TextUtils.isEmpty(btnDatePickerSplitBill.getText().toString())) {
                    btnDatePickerSplitBill.setError("Must Not Be Empty!");
                    return;
                } else if (edtPayerName.getSelectedItem().toString().equals("")) {
                    Toast.makeText(SplitBillActivity.this, "Please select a payer!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Double.valueOf(tvRemainAmount.getText().toString()) != 0) {
                    Toast.makeText(SplitBillActivity.this, "You Still Have Remain Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    Address geocodedAddress = null;
//                    DecimalFormat df = new DecimalFormat("###.#####");
                    try {
                        List<Address> geocodedAddressList = geocoder.getFromLocationName(edtAddressSplitBill.getText().toString(), 1);
                        geocodedAddress = geocodedAddressList.get(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("USERNAME: "+ username);
                    System.out.println("BILL_NAME: "+ edtActivityNameSplitBill.getText().toString());
                    System.out.println("TOTAL_AMOUNT: "+ tvTotalAmountSplitBill.getText().toString());
                    System.out.println("DATE: "+ btnDatePickerSplitBill.getText().toString());
                    System.out.println("PAID_BY: "+ selectedPayerName);
                    System.out.println("LAT: "+ geocodedAddress.getLatitude());
                    System.out.println("LNG: "+ geocodedAddress.getLongitude());
                    System.out.println(selectedFriendsMap);
                    if((selectedFriendsMap.size()==1)&&(selectedPayerName.equalsIgnoreCase(selectedFriendsMap.keySet().toArray()[0].toString()))){
                        Toast.makeText(SplitBillActivity.this, "Payer can't be the only splitter!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        selectedFriendsMap.remove(selectedPayerName);
                        SplitBillModel splitBill = new SplitBillModel(username,
                                edtActivityNameSplitBill.getText().toString(),
                                Double.parseDouble(tvTotalAmountSplitBill.getText().toString()),
                                btnDatePickerSplitBill.getText().toString(),
                                selectedPayerName,
                                Double.toString(geocodedAddress.getLatitude()),
                                Double.toString(geocodedAddress.getLongitude()),
                                selectedFriendsMap);
                        putBillSplit(getApplicationContext(),splitBill);
                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putString("BILL_ACTIVITY_NAME",edtActivityNameSplitBill.getText().toString());
//                    bundle.putString("BILL_DATE",btnDatePickerSplitBill.getText().toString());
//                    bundle.putString("BILL_ADDRESS",edtAddressSplitBill.getText().toString());
//                    bundle.putString("BILL_TOTAL_AMOUNT",tvTotalAmountSplitBill.getText().toString());
                    //Right now I just go to ScanBillActivity Again, I wanted to goto AddBillFragment but I don't know how......
                    //It's fine if we are not able to do this, just let the user do their stuffs once finished one bill......
                    Intent gotoScanBillActivity = new Intent(view.getContext(), MenuView.class);
//                    gotoScanBillActivity.putExtras(bundle);
                    startActivity(gotoScanBillActivity);
                }
            }
        });


        /************Get Bundles from AddBillFragment Here************/
        getActivityName = (String) getIntent().getExtras().get("BILL_ACTIVITY_NAME");
        getTotalAmount = (String) getIntent().getExtras().get("BILL_TOTAL_AMOUNT");
        getDate = (String) getIntent().getExtras().get("BILL_DATE");
        getAddress = (String) getIntent().getExtras().get("BILL_ADDRESS");


        /************Put Bundles from AddBillFragment into SplitBillActivity************/
        edtActivityNameSplitBill.setText(getActivityName);
        tvTotalAmountSplitBill.setText(getTotalAmount);
        btnDatePickerSplitBill.setText(getDate);
        edtAddressSplitBill.setText(getAddress);

        /************Handle Dialog of Adding Split Friends************/
        TotalAmount = Double.valueOf(tvTotalAmountSplitBill.getText().toString());
        RemainAmount = Double.valueOf(tvTotalAmountSplitBill.getText().toString()); // Grabbing initial Total Amount
        tvRemainAmount.setText(RemainAmount.toString());

        /*******Right now not support Change Money of Bill Consumption********/
//        edtTotalAmountSplitBill.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals("")) {
//                    edtTotalAmountSplitBill.setText("0");
//                }
//                RemainAmount = Double.valueOf(edtTotalAmountSplitBill.getText().toString());
//                tvRemainAmount.setText(RemainAmount.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        BigDecimal amount3 = new BigDecimal("2.15");

        // set the autocomplete feature to activate when user clicks on address field
        edtAddressSplitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the fields to specify which types of place data to return after the user
                // has made a selection
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);

                // start the autocomplete intent
                Intent autocompleteIntent = new Autocomplete
                        .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getBaseContext());
                startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        edtAddressSplitBill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                // set the fields to specify which types of place data to return after the user
                // has made a selection
                if (hasFocus) {
                    List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);

                    // start the autocomplete intent
                    Intent autocompleteIntent = new Autocomplete
                            .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                            .build(getBaseContext());
                    startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);
                }
            }
        });

        buildAddSplitFriendDialog();
        btnAddSplitFriend = findViewById(R.id.btnAddSplitFriend);
        btnAddSplitFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSplitFriendDialog.show();
            }
        });

        btnDatePickerSplitBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    /********** Building Items content Dialog **********/
    //TODO:Re-enable item dialog after test.
    private void buildItemsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_bill_conclusion_items, null);

        lineItems = getLineItems(veryfiResponseString);
        ListView SplitBillItemLists = view.findViewById(R.id.SplitBillItemLists);
        int size = lineItems.size();
        if(size>0){
            String[] itemNames = new String[size];
            double[] itemsQ = new double[size];
            double[] totals = new double[size];
            int index = 0;
            for(LineItems lineItem : lineItems){
                itemNames[index] = lineItem.getDescription();
                itemsQ[index] = lineItem.getQuantity();
                totals[index] = lineItem.getTotal();
                index++;
            }

            LineItemsAdapter lineItemsAdapter = new LineItemsAdapter(this,itemNames,itemsQ,totals);
            SplitBillItemLists.setAdapter(lineItemsAdapter);
        }else{
            Toast.makeText(getApplicationContext(),"No line items to show",Toast.LENGTH_LONG).show();
        }


        builder.setView(view);
        ItemsDialog = builder.create();
    }


    //fetch line items from shared preferences
    private List<LineItems> getLineItems(String veryfiResponseString){
        //SharedPreferences sharedPref = this.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        //String jsonString = getIntent().getExtras().getString(Constants.VERYI_RESPONSE_KEY);
        Gson gson = new Gson();

        Log.d("veryfi",veryfiResponseString);
        VeryfiOcrResponse veryfiOcrResponse;
        try{
            veryfiOcrResponse = gson.fromJson(veryfiResponseString,VeryfiOcrResponse.class);
        }catch (Exception e){
            veryfiOcrResponse = null;
        }
        if(veryfiOcrResponse==null){
            lineItems = new ArrayList<>();
            return lineItems;
        }
        int noOfItems = veryfiOcrResponse.getLineItems().size();
        if(noOfItems>0){
            lineItems = veryfiOcrResponse.getLineItems();
        }else{
            lineItems = new ArrayList<>();
        }
        return lineItems;
    }

    private String getTodaysDate() {
        //Set default date as today
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btnDatePickerSplitBill.setText(date);
            }
        };

        //Set default date as today
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        //Initialize Date Picker Dialog
        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return  year + "-" + month + "-" + day;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // actually populate the address field with user's selection
                Place place = Autocomplete.getPlaceFromIntent(data);
                edtAddressSplitBill.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Toast.makeText(getBaseContext(), "Address not found", Toast.LENGTH_SHORT).show();
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /************Build Dialog For Adding Split Friends Card************/
    private void buildAddSplitFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_add_split_friends, null);

        Spinner edtAddFriendName = (Spinner) view.findViewById(R.id.edtAddFriendName);
        EditText edtAddFriendAmount = view.findViewById(R.id.edtAddFriendAmount);

        edtAddFriendName.setAdapter(spinnerArrayAdapter);
        edtAddFriendName.setSelection(0);

        edtAddFriendName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedFriend = adapterView.getItemAtPosition(pos).toString();
                isFriendSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                isFriendSelected = false;
            }
        });

        builder.setView(view);
        builder.setTitle("Enter Friend and Amount")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double CheckRemainAmount = Double.valueOf(tvRemainAmount.getText().toString());
                        if (TextUtils.isEmpty(edtAddFriendAmount.getText().toString())) {
//                            edtAddFriendName.setError("Must Not Be Empty!");
                            edtAddFriendAmount.setError("Must Not Be Empty!");
                            return;
                        }
                        if ((!isFriendSelected) || selectedFriend.equals("")) {
                            Toast.makeText(SplitBillActivity.this, "Please select a friend!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (Double.valueOf(edtAddFriendAmount.getText().toString()) > CheckRemainAmount) {
                            edtAddFriendAmount.setError("Too much!");
                            Toast.makeText(SplitBillActivity.this, "Too Much!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        addCard(selectedFriend, edtAddFriendAmount.getText().toString());
                        RemainAmount = RemainAmount - Double.valueOf(edtAddFriendAmount.getText().toString());
                        DecimalFormat df = new DecimalFormat("###.00");
                        tvRemainAmount.setText(df.format(RemainAmount));
                        spinnerArrayAdapter.remove(selectedFriend);
                        spinnerArrayAdapter.notifyDataSetChanged();
                        edtAddFriendName.setSelection(0);
                        selectedFriendsMap.put(selectedFriend, Double.parseDouble(edtAddFriendAmount.getText().toString()));
//                        tvRemainAmount.setText(RemainAmount.toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AddSplitFriendDialog = builder.create();
    }

    private void addCard(String Username, String PayAmount) {
        final View cardview = getLayoutInflater().inflate(R.layout.cardview_add_split_friends, null);


        TextView tvFriendSplitUsername = cardview.findViewById(R.id.tvFriendSplitUsername);
        TextView tvFriendSplitAmount = cardview.findViewById(R.id.tvFriendSplitAmount);
        Button btnRemoveFriendCard = cardview.findViewById(R.id.btnRemoveFriendCard);

        tvFriendSplitUsername.setText(Username);
        tvFriendSplitAmount.setText(PayAmount);
        CurrentAmount = Double.valueOf(tvFriendSplitAmount.getText().toString()); // First time amount

        btnRemoveFriendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearFriendSplit.removeView(cardview);
                CurrentAmount = Double.valueOf(tvFriendSplitAmount.getText().toString());
                RemainAmount = RemainAmount + CurrentAmount;
                DecimalFormat df = new DecimalFormat("###.00");
                tvRemainAmount.setText(df.format(RemainAmount));
                selectedFriendsMap.remove(tvFriendSplitUsername.getText().toString());
                spinnerArrayAdapter.add(tvFriendSplitUsername.getText().toString());
                spinnerArrayAdapter.notifyDataSetChanged();
            }
        });

        LinearFriendSplit.addView(cardview);

    }

    public void getAllFriends(Context context) {
        //1. create an instance of friend methods interface defined in our FriendMethods class
        FriendMethods friendMethods = LoginRetrofitClient.getRetrofitInstance().create(FriendMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
        Call<List<FriendBalanceModel>> call = friendMethods.getFriendsBreakdown(username);

        call.enqueue(new Callback<List<FriendBalanceModel>>() {
            @Override
            public void onResponse(Call<List<FriendBalanceModel>> call, Response<List<FriendBalanceModel>> response) {
                Log.d(TAG, "response code :" + response.code());

                if (response.code() == Constants.RESPONSE_OK) {
                    List<FriendBalanceModel> friendBalanceModels = response.body();
                    System.out.println(friendBalanceModels.toString());
                    for (FriendBalanceModel friendData : friendBalanceModels) {
                        allFriendsList.add(friendData.getFriend_username());
                    }
                    spinnerArrayAdapter.addAll(allFriendsList);
                    paidByArrayAdapter.addAll(allFriendsList);
                }
            }

            @Override
            public void onFailure(Call<List<FriendBalanceModel>> call, Throwable t) {
                //error getting cards
//                Log.d(TAG,t.getMessage());
                Toast.makeText(getApplicationContext(), "error fetching data", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void putBillSplit(Context context, SplitBillModel splitBillModel) {
        //1. create an instance of friend methods interface defined in our FriendMethods class
        SplitBillMethods splitBillMethods = LoginRetrofitClient.getRetrofitInstance().create(SplitBillMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
        Call<LoginModel> call = splitBillMethods.putSplitBill(splitBillModel);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d(TAG, "response code :" + response.code());

                if (response.code() == Constants.RESPONSE_OK) {
                    LoginModel loginModel = response.body();
                    System.out.println(loginModel.toString());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                //error getting cards
//                Log.d(TAG,t.getMessage());
                Toast.makeText(getApplicationContext(), "DB Transaction Failed!", Toast.LENGTH_LONG).show();
            }
        });
    }



}

package com.teamblue.WeBillv2.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.Precision;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.teamblue.WeBillv2.model.adapter.LineItemsAdapter;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.LineItems;
import com.teamblue.WeBillv2.model.pojo.VeryfiOcrResponse;
import com.teamblue.WeBillv2.view.fragments.AddBillFragment;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SplitBillActivity extends AppCompatActivity {

    private static int AUTOCOMPLETE_REQUEST_CODE = 7001;
    private EditText edtActivityNameSplitBill,edtTotalAmountSplitBill,edtDateSplitBill,edtAddressSplitBill,edtPayerName;
    private TextView tvRemainAmount, tvTotalAmountSplitBill;
    private String getActivityName, getTotalAmount, getDate, getAddress;
    private Button btnAddSplitFriend;
    private Button btnSaveBill;
    private Button btnDatePickerSplitBill;
    private Button lineItemsButton;
    private Double RemainAmount, CurrentAmount,TotalAmount;
    private DatePickerDialog datePickerDialog;
    LinearLayout LinearFriendSplit;
    AlertDialog AddSplitFriendDialog, ItemsDialog;

    List<LineItems> lineItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_bill);

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
        edtPayerName = (EditText) findViewById(R.id.edtPayerName);
        edtPayerName.setText(sharedPref.getString(Constants.USERNAME_KEY,""));
        tvTotalAmountSplitBill = (TextView) findViewById(R.id.tvTotalAmountSplitBill);
        tvRemainAmount = (TextView) findViewById(R.id.tvRemainAmount);
        LinearFriendSplit = findViewById(R.id.LinearFriendSplit);
        btnSaveBill = (Button) findViewById(R.id.btnSaveBill);
        lineItemsButton = (Button) findViewById(R.id.lineItemsPopupButton);

        initDatePicker();
        buildItemsDialog();


        /******** Show Items Dialog *********/
        lineItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemsDialog.show();
            }
        });

        /******** Check Final Bill Information Completeness *********/
        btnSaveBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtActivityNameSplitBill.getText().toString())) {
                    edtActivityNameSplitBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(btnDatePickerSplitBill.getText().toString())){
                    btnDatePickerSplitBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtPayerName.getText().toString())) {
                    edtPayerName.setError("Must Not Be Empty!");
                    return;
                }else if(Double.valueOf(tvRemainAmount.getText().toString()) != 0 ) {
                    Toast.makeText(SplitBillActivity.this, "You Still Have Remain Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else{
    /**********TODO: After Finishing a Bill Information, put all data needed to whatever fragment/activity the app needs *******/
                    Bundle bundle = new Bundle();
                    bundle.putString("BILL_ACTIVITY_NAME",edtActivityNameSplitBill.getText().toString());
                    bundle.putString("BILL_DATE",btnDatePickerSplitBill.getText().toString());
                    bundle.putString("BILL_ADDRESS",edtAddressSplitBill.getText().toString());
                    bundle.putString("BILL_TOTAL_AMOUNT",tvTotalAmountSplitBill.getText().toString());
                    //Right now I just go to ScanBillActivity Again, I wanted to goto AddBillFragment but I don't know how......
                    //It's fine if we are not able to do this, just let the user do their stuffs once finished one bill......
                    Intent gotoScanBillActivity = new Intent(view.getContext(), MenuView.class);
                    gotoScanBillActivity.putExtras(bundle);
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
    private void buildItemsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_bill_conclusion_items, null);

        lineItems = getLineItems();
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
    private List<LineItems> getLineItems(){
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String jsonString = sharedPref.getString(Constants.VERYI_RESPONSE_KEY,"");
        VeryfiOcrResponse veryfiOcrResponse = gson.fromJson(jsonString,VeryfiOcrResponse.class);

        int noOfItems = veryfiOcrResponse.getLineItems().size();
        if(noOfItems>0){
            lineItems = veryfiOcrResponse.getLineItems();
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
        return makeDateString(day,month,year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day,month,year);
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
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int day, int month, int year) {
        return month + "/" + day + "/" + year;
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

        EditText edtAddFriendName = view.findViewById(R.id.edtAddFriendName);
        EditText edtAddFriendAmount = view.findViewById(R.id.edtAddFriendAmount);



        builder.setView(view);
        builder.setTitle("Enter Friend and Amount")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Double CheckRemainAmount = Double.valueOf(tvRemainAmount.getText().toString());
                        if(TextUtils.isEmpty(edtAddFriendName.getText().toString()) ||
                                TextUtils.isEmpty(edtAddFriendAmount.getText().toString())){
                            edtAddFriendName.setError("Must Not Be Empty!");
                            edtAddFriendAmount.setError("Must Not Be Empty!");
                            return;
                        }
                        if (Double.valueOf(edtAddFriendAmount.getText().toString()) > CheckRemainAmount){
                            edtAddFriendAmount.setError("Too much!");
                            Toast.makeText(SplitBillActivity.this, "Too Much!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        addCard(edtAddFriendName.getText().toString(),
                                edtAddFriendAmount.getText().toString());
                        RemainAmount = RemainAmount - Double.valueOf(edtAddFriendAmount.getText().toString());
                        DecimalFormat df = new DecimalFormat("###.##");
                        tvRemainAmount.setText(df.format(RemainAmount));
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

    private void addCard(String Username, String PayAmount){
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
                DecimalFormat df = new DecimalFormat("###.##");
                tvRemainAmount.setText(df.format(RemainAmount));
            }
        });

        LinearFriendSplit.addView(cardview);

    }





}

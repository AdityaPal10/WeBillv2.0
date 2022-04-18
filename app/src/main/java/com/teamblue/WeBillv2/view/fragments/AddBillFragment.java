package com.teamblue.WeBillv2.view.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.teamblue.WeBillv2.BuildConfig;
import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.view.SplitBillActivity;

import java.util.Arrays;
import java.util.List;


/**
 *  addbill fragment subclass.
 */
public class AddBillFragment extends Fragment {


    private static int AUTOCOMPLETE_REQUEST_CODE = 7001;
    private EditText edtActivityNameAddBill,edtTotalAmountAddBill,edtDateAddBill,edtAddressAddBill;
    private Button btnEnterAddBill,btnScanBill;

    public AddBillFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // actually populate the address field with the user's selection
                Place place = Autocomplete.getPlaceFromIntent(data);
                edtAddressAddBill.setText(place.getAddress());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Toast.makeText(getContext(), "Address not found", Toast.LENGTH_SHORT).show();
            } else if (resultCode == AutocompleteActivity.RESULT_CANCELED) {
                // The user canceled the operation
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//prevent keyboard pushing my view
        View view = inflater.inflate(R.layout.fragment_add_bill, container, false);

        edtActivityNameAddBill = (EditText) view.findViewById(R.id.edtActivityNameAddBill);
        edtTotalAmountAddBill = (EditText) view.findViewById(R.id.edtTotalAmountAddBill);
        edtDateAddBill = (EditText) view.findViewById(R.id.edtDateAddBill);
        edtAddressAddBill = (EditText) view.findViewById(R.id.edtAddressAddBill);
        btnEnterAddBill = (Button) view.findViewById(R.id.btnEnterAddBill);
        btnScanBill = (Button) view.findViewById(R.id.btnScanBill);

        // Initialize the Google Maps Places SDK and create PlacesClient instance
        // we need this for the autocomplete feature in the address field
        if (!Places.isInitialized())
            Places.initialize(getContext(), BuildConfig.MAPS_API_KEY);
        PlacesClient placesClient = Places.createClient(getContext());

        // set to the autocomplete feature to activate after the user clicks on the address field
        edtAddressAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the fields to specify which types of place data to return after the user
                // has made a selection
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG);

                // start the autocomplete intent
                Intent autocompleteIntent = new Autocomplete
                        .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(getContext());
                startActivityForResult(autocompleteIntent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        btnEnterAddBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking for no total amount empty error
                if(TextUtils.isEmpty(edtActivityNameAddBill.getText().toString())) {
                    edtActivityNameAddBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtTotalAmountAddBill.getText().toString())) {
                    edtTotalAmountAddBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtDateAddBill.getText().toString())){
                    edtDateAddBill.setError("Must Not Be Empty!");
                        return;
                }else if(TextUtils.isEmpty(edtAddressAddBill.getText().toString())){
                    edtAddressAddBill.setError("Must Not Be Empty!");
                    return;
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("BILL_ACTIVITY_NAME",edtActivityNameAddBill.getText().toString());
                    bundle.putString("BILL_TOTAL_AMOUNT",edtTotalAmountAddBill.getText().toString());
                    bundle.putString("BILL_DATE",edtDateAddBill.getText().toString());
                    bundle.putString("BILL_ADDRESS",edtAddressAddBill.getText().toString());
                    Intent gotoSplitBillActivity = new Intent(view.getContext(), SplitBillActivity.class);
                    gotoSplitBillActivity.putExtras(bundle);
                    startActivity(gotoSplitBillActivity);
                }

            }
        });

        btnScanBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /********TODO: Start Scan Bill Activity here (not fragment!)********/
            }
        });



//        edtTotalAmountAddBill.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().equals("")){
//                    edtTotalAmountAddBill.setText("0");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


        return view;
    }
}

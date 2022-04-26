package com.teamblue.WeBillv2.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.teamblue.WeBillv2.R;
import com.teamblue.WeBillv2.model.api.BillMethods;
import com.teamblue.WeBillv2.model.api.FriendMethods;
import com.teamblue.WeBillv2.model.pojo.BillModel;
import com.teamblue.WeBillv2.model.pojo.Constants;
import com.teamblue.WeBillv2.model.pojo.FriendBalanceModel;
import com.teamblue.WeBillv2.service.LoginRetrofitClient;
import com.teamblue.WeBillv2.view.MyBillsListView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * receipts fragment subclass.
 */
public class ReceiptFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private Button btnFilterMonth;
    private Spinner spinnerMonths;
    private Dialog filterDialog;
    private Dialog addReceiptDialog;
    private Dialog receiptDetailsDialog;

    private String TAG = "Bills";

    LinearLayout receiptContainer;


    public ReceiptFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);

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
        spinnerMonths = (Spinner) filterDialog.findViewById(R.id.spinnerMonths);// since spinner is inside myDialog, don't use view.getContext()
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity().getBaseContext(),
                R.array.months,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonths.setAdapter(adapter);
        spinnerMonths.setOnItemSelectedListener(this);

        //3 handling the receiptContainer
        //btnAddCard = (Button) view.findViewById(R.id.btnAddCard);
        receiptContainer = (LinearLayout) view.findViewById(R.id.receiptContainer);

//        buildDialog();// building the layout for pop-up window and wait for call
//        btnAddCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addReceiptDialog.show();
//            }
//        });

        SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);

        getBillsForUser(getContext(),sharedPref.getString(Constants.USERNAME_KEY,""));

        return view;
    }

    //2.1 Do spinner's functions here, now a demo of showing Toast text when select a month
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String textMonth = spinnerMonths.getSelectedItem().toString();
        Toast.makeText(this.getContext(), textMonth, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //make network call to receive bills belonging to user
    public void getBillsForUser(Context context,String username){
        //1. create an instance of friend methods interface defined in our FriendMethods class
        BillMethods billMethods = LoginRetrofitClient.getRetrofitInstance().create(BillMethods.class);
        //2. create a call object which will make the REST API call to our backend by passing in username as paramaters
        Call<List<BillModel>> call = billMethods.getBillsForUser(username);

        call.enqueue(new Callback<List<BillModel>>() {
            @Override
            public void onResponse(Call<List<BillModel>> call, Response<List<BillModel>> response) {
                Log.d(TAG,"response code :"+response.code());

                if(response.code()== Constants.RESPONSE_OK){
                    List<BillModel> bills = response.body();
                    System.out.println(bills.toString());

                    //build data adapter
                    Integer[] billIds = new Integer[bills.size()];
                    String[] billAmounts = new String[bills.size()];
                    String[] billNames = new String[bills.size()];
                    String[] billDates = new String[bills.size()];
                    String[] latitudes = new String[bills.size()];
                    String[] longitudes = new String[bills.size()];
                    String[] paidBy = new String[bills.size()];
                    int index = 0;
                    for(index=0;index< bills.size();index++) {
                        //add each bill model to list adapter
                        BillModel bill = bills.get(index);
                        billIds[index] = bill.getBillId();
                        billNames[index] = bill.getBillName();
                        billDates[index] = bill.getDate();
                        billAmounts[index] = String.valueOf(bill.getTotalAmount());
                        latitudes[index] = bill.getLatitude();
                        longitudes[index] = bill.getLongitude();
                        paidBy[index] = bill.getPaidBy();
                    }

                    //set data to list view adapter
                    MyBillsListView myBillsListView = new MyBillsListView(getActivity(),billIds,billAmounts,billNames,billDates,latitudes,longitudes,paidBy);
                    ListView listView = (ListView)receiptContainer.findViewById(R.id.billsListView);
                    listView.setAdapter(myBillsListView);



                    //add new card to existing container
                    receiptContainer.removeAllViews();
                    receiptContainer.addView(listView);

                }
            }

            @Override
            public void onFailure(Call<List<BillModel>> call, Throwable t) {
                //error getting cards
                Log.d(TAG,t.getMessage());
                Toast.makeText(getContext(),"error fetching data",Toast.LENGTH_LONG).show();
            }
        });
    }

    //3.1 handling the addReceiptDialog function
//    private void buildDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        View view = getLayoutInflater().inflate(R.layout.popup_receipt_bill_info, null);
//
//        EditText edtBillAmount = view.findViewById(R.id.edtBillAmount);
//        EditText edtActivityName = view.findViewById(R.id.edtActivityName);
//        EditText edtDate = view.findViewById(R.id.edtDate);
//
//        builder.setView(view);
//        builder.setTitle("Enter Bills")
//                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        addCard(edtBillAmount.getText().toString(),
//                                edtActivityName.getText().toString(),
//                                edtDate.getText().toString());
//
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
//        addReceiptDialog = builder.create();
//    }
    //3.2 once finished entering the information, pass them into the current new cardView
//    private void addCard(String BillAmount, String ActivityName, String Date) {
//        View view = getLayoutInflater().inflate(R.layout.cardview_receipt, null);
//
//        TextView tvReceiptAmount = view.findViewById(R.id.tvReceiptAmount);
//        TextView tvActivityName = view.findViewById(R.id.tvActivityName);
//        TextView tvReceiptDate = view.findViewById(R.id.tvReceiptDate);
//
////        Button btnRemoveCard = view.findViewById(R.id.btnRemoveCard);
//        Button btnReceiptDetails = view.findViewById(R.id.btnReceiptDetails);
//
//        //4 handing the details button
//        btnReceiptDetails.setOnClickListener(new View.OnClickListener() { // when user click "details" at current bill cardView
//            @Override
//            public void onClick(View view) {
//                buildBillDetails(BillAmount,ActivityName,Date); // pass any information we want to the pop up window
//                receiptDetailsDialog.show();
//            }
//        });


//        btnRemoveCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                receiptContainer.removeView(view);
//            }
//        });
//        tvReceiptAmount.setText(BillAmount);
//        tvActivityName.setText(ActivityName);
//        tvReceiptDate.setText(Date);
//        receiptContainer.addView(view);
//    }

    //4.1 Render the receipt details pop up windows here
//    private void buildBillDetails(String BillAmount,String ActivityName,String Date){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        View viewReceiptDetails = getLayoutInflater().inflate(R.layout.popup_receipt_click_details, null);
//
//        TextView tvTotal = viewReceiptDetails.findViewById(R.id.tvTotal);
//        TextView tvActivityText = viewReceiptDetails.findViewById(R.id.tvActivityText);
//        TextView tvDate = viewReceiptDetails.findViewById(R.id.tvDate);
//
//        builder.setView(viewReceiptDetails);
//
//        tvTotal.setText(BillAmount);
//        tvActivityText.setText(ActivityName);
//        tvDate.setText(Date);
//        receiptDetailsDialog = builder.create();
//    }

}

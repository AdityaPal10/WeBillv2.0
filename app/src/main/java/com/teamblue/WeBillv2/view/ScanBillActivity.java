package com.teamblue.WeBillv2.view;

import static android.Manifest.permission.CAMERA;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.teamblue.WeBillv2.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;

public class ScanBillActivity extends Activity {
    private static final String TAG = "BASE64";
    private ImageView captureIV;
    private EditText edtActivityNameScanBill,edtTotalAmountScanBill,edtDateScanBill,edtAddressScanBill;
    private Button btnEnterScanBill;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bill);

        // Create View Objects
        captureIV = (GifImageView) findViewById(R.id.idIVCaptureImage);
        edtActivityNameScanBill = (EditText) findViewById(R.id.edtActivityNameScanBill);
        edtTotalAmountScanBill = (EditText) findViewById(R.id.edtTotalAmountScanBill);
        edtDateScanBill = (EditText) findViewById(R.id.edtDateScanBill);
        edtAddressScanBill = (EditText) findViewById(R.id.edtAddressScanBill);
        btnEnterScanBill = (Button) findViewById(R.id.btnEnterScanBill);

        btnEnterScanBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Checking for no total amount empty error
                if(TextUtils.isEmpty(edtActivityNameScanBill.getText().toString())) {
                    edtActivityNameScanBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtTotalAmountScanBill.getText().toString())) {
                    edtTotalAmountScanBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtDateScanBill.getText().toString())){
                    edtDateScanBill.setError("Must Not Be Empty!");
                    return;
                }else if(TextUtils.isEmpty(edtAddressScanBill.getText().toString())){
                    edtAddressScanBill.setError("Must Not Be Empty!");
                    return;
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("BILL_ACTIVITY_NAME",edtActivityNameScanBill.getText().toString());
                    bundle.putString("BILL_TOTAL_AMOUNT",edtTotalAmountScanBill.getText().toString());
                    bundle.putString("BILL_DATE",edtDateScanBill.getText().toString());
                    bundle.putString("BILL_ADDRESS",edtAddressScanBill.getText().toString());
                    Intent gotoSplitBillActivity = new Intent(view.getContext(), SplitBillActivity.class);
                    gotoSplitBillActivity.putExtras(bundle);
                    startActivity(gotoSplitBillActivity);
                }
            }
        });



        /***************Handling Receipt Scanning Camera Here ************/
        //Asking Permission
        if (ContextCompat.checkSelfPermission(ScanBillActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ScanBillActivity.this,
                    new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);

        }
        captureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ScanBillActivity.super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //5.6 then we use bundle to retrieve information from Bitmap
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
//            captureIV.setImageBitmap(imageBitmap);
//            captureIV.setRotation(90);
            // initialize byte stream
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            // compress Bitmap
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            // Initialize byte array
            byte[] bytes=stream.toByteArray();
            // get base64 encoded string
            String Base64String= Base64.encodeToString(bytes,Base64.DEFAULT); // send the string to backend
            // set encoded text on textview
//            resultTV.setText(Base64String);
            Log.d(TAG, Base64String);

            /********* TODO: Hard Coded scanned result now. Replace once finished real API ******/
            edtActivityNameScanBill.setText("Sushi(HardCode message)");
            edtTotalAmountScanBill.setText("999");
            edtDateScanBill.setText("1970/01/01");
            edtAddressScanBill.setText("HardCode Address Here");

        }
    }
}

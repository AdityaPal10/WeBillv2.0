package com.teamblue.WeBillv2.view;

import static android.Manifest.permission.CAMERA;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class ScanBillActivity extends AppCompatActivity {
    private static final String TAG = "BASE64";
    private ImageView captureIV;
    private TextView resultTV;
    private Button snapBtn,detectBtn;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_bill);

        //4.2 Create View Objects
        captureIV = (ImageView) findViewById(R.id.idIVCaptureImage);
        resultTV = (TextView) findViewById(R.id.idTVDetectorText);
        snapBtn = (Button) findViewById(R.id.idBtnSnap);
        detectBtn = (Button) findViewById(R.id.idBtnDetect);

        //Asking Permission
        if (ContextCompat.checkSelfPermission(ScanBillActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(ScanBillActivity.this,
                    new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_CAPTURE);

        }


        snapBtn.setOnClickListener(new View.OnClickListener() {
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
            captureIV.setImageBitmap(imageBitmap);
            captureIV.setRotation(90);

            // initialize byte stream
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            // compress Bitmap
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            // Initialize byte array
            byte[] bytes=stream.toByteArray();
            // get base64 encoded string
            String Base64String= Base64.encodeToString(bytes,Base64.DEFAULT);
            // set encoded text on textview
            resultTV.setText(Base64String);
            Log.d(TAG, Base64String);

        }
    }
}

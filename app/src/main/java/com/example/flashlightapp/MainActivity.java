package com.example.flashlightapp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ToggleButton;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private ToggleButton FlashOnOff;
    private String getCameraID;
    private CameraManager mCameraManager;
    private BatteryReceiver mBatteryReceiver = new BatteryReceiver();
    private IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FlashOnOff = findViewById(R.id.flashlight);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            getCameraID = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)//bez @requiresApi man rādija error pie setTorchMode, bet programma gāja, tas laikam lai uz vecākām ierīcēm kas nenojūk
    public void toggleFlashLight(View view) {//Flashlight on/off
        if (FlashOnOff.isChecked()) {
            try {
                mCameraManager.setTorchMode(getCameraID, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            try {
                mCameraManager.setTorchMode(getCameraID, false);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void finish() {//Izsledz flashlight, ja izslēdz app
        super.finish();
        try {
            mCameraManager.setTorchMode(getCameraID, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mBatteryReceiver);
        super.onPause();
    }
}
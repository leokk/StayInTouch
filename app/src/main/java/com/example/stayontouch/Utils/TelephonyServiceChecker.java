package com.example.stayontouch.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class TelephonyServiceChecker extends AppCompatActivity {
    private static Activity content;
    public TelephonyServiceChecker(Activity activity){
        content = activity;
    }

    private void CheckPermissionAndStartIntent() {
        if (ContextCompat.checkSelfPermission(content, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(content, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(content, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            //SEY SOMTHING LIKE YOU CANT ACCESS WITHOUT PERMISSION
        } else {
            doSomthing();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doSomthing();
                } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    //SEY SOMTHING LIKE YOU CANT ACCESS WITHOUT PERMISSION
                    //you can show something to user and open setting -> apps -> youApp -> permission
                    // or unComment below code to show permissionRequest Again
                    //ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }
            }
        }
    }


    void doSomthing() {
        String  deviceIMEI = getDeviceIMEI();
        //andGoToYourNextStep
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceIMEI() {

        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) content.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(content, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(content, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            else
                deviceUniqueIdentifier = tm.getDeviceId();
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length())
                deviceUniqueIdentifier = "0";
        }
        return deviceUniqueIdentifier;
    }
}

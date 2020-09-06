package com.example.stayontouch.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.stayontouch.Activities.MapsActivity;
import com.example.stayontouch.Entitie.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyService extends Service {

    User user;
    private final IBinder mBinder = new MyBinder();
    private String TAG = "MyService";

    public MyService() { }

//    @Override
//    public void onCreate() {
//        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
//
//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run() {
//                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
//                handler.postDelayed(runnable, 10000);
//            }
//        };
//
//        handler.postDelayed(runnable, 15000);
//    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        onTaskRemoved(intent);

        Log.d(TAG,"IT finally works");

        Toast.makeText(getApplicationContext(),"This is a Service running in Background",
                Toast.LENGTH_SHORT).show();

        SystemClock.sleep(2000);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        MyService getService() {
            return MyService .this;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }
}
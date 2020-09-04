package com.example.stayontouch.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyService", "Service Stops! Oops!!!!");
        Intent serviceIntent = new Intent(context, ServiceNoDelay.class);
        context.startService(serviceIntent);

    }
}

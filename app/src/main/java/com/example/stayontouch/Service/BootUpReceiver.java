package com.example.stayontouch.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {
        Intent gpsIntent = new Intent(c, YourService.class);


        // Add putExtras() or look at parceables to see if there is anything we need to do here

        Toast.makeText(c, "Our broadcast receiver was hit!", Toast.LENGTH_LONG).show();

        // Start the activity (by utilizing the passed context)
        gpsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.getApplicationContext().startService(gpsIntent);

    }

}
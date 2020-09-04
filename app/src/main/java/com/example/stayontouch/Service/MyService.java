package com.example.stayontouch.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.stayontouch.Activities.MainActivity;
import com.example.stayontouch.Activities.ProfileActivity;
import com.example.stayontouch.R;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    NotificationManager nm;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sendNotif();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    void sendNotif() {
        // 1-я часть
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), ProfileActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Stay On Touch service");
        bigText.setBigContentTitle("Network service is required");
//        bigText.setSummaryText("The best approach is turn on JPS and network services on your phone for more precise accuracy");
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Little Tip");
        mBuilder.setContentText("Network service is required. The best approach is to turn on JPS on your phone for more precise accuracy");
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        String channelId = "Your_channel_id";
        NotificationChannel channel = new NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH);
        mNotificationManager.createNotificationChannel(channel);
        mBuilder.setChannelId(channelId);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }
}


//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.location.Location;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.SystemClock;
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.example.stayontouch.Activities.MapsActivity;
//import com.example.stayontouch.Entitie.User;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//public class MyService extends Service {
//
//    User user;
//
//    private FusedLocationProviderClient mFusedLocationProviderClient;
//    private static String TAG = "MyService";
//    public Context context = this;
//    public Handler handler = null;
//    public static Runnable runnable = null;
//
//    public MyService() { }
//
////    @Override
////    public void onCreate() {
////        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
////
////        handler = new Handler();
////        runnable = new Runnable() {
////            public void run() {
////                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
////                handler.postDelayed(runnable, 10000);
////            }
////        };
////
////        handler.postDelayed(runnable, 15000);
////    }
//
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId){
//        onTaskRemoved(intent);
//
//        Log.d(TAG,"IT finally works");
//
//        Toast.makeText(getApplicationContext(),"This is a Service running in Background",
//                Toast.LENGTH_SHORT).show();
//        getDeviceLocation();
//        SystemClock.sleep(2000);
//        return START_STICKY;
//    }
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
////    @Override
////    public void onDestroy() {
////        handler.removeCallbacks(runnable);
////        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
////    }
//
//    private void getDeviceLocation() {
//        Log.d(TAG, "getDeviceLocation: getting the devices current location");
//
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        try {
//
//
//            final Task location = mFusedLocationProviderClient.getLastLocation();
//            location.addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//                    if (task.isSuccessful()) {
//                        Log.d(TAG, "onComplete: found location!");
//                        Location currentLocation = (Location) task.getResult();
//                        Log.d(TAG, "ACCURACY = " + currentLocation.getAccuracy());
//                        user.setPosx(currentLocation.getLatitude());
//                        user.setPosy(currentLocation.getLongitude());
//                    } else {
//                        Log.d(TAG, "onComplete: current location is null");
//                        Toast.makeText(MyService.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//        } catch (SecurityException e) {
//            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onTaskRemoved(Intent rootIntent) {
//        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//        startService(restartServiceIntent);
//        super.onTaskRemoved(rootIntent);
//    }
//}
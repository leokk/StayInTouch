package com.example.stayontouch.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.stayontouch.Activities.InitialActivity;
import com.example.stayontouch.Activities.ProfileActivity;
import com.example.stayontouch.R;

public class tst extends Service {
    static final int NOTIFICATION_ID = 543;
    public boolean killed = false;
    public static boolean isServiceRunning = false;
    String TAG = "MyService";
    @Override
    public void onCreate() {
        super.onCreate();
//        startServiceWithNotification();
        sendNotif();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null && intent.getAction().equals(C.ACTION_START_SERVICE)) {
//              startServiceWithNotification();
//        else stopMyService();
//        }
        if(killed){
            sendNotif();
        }
        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    void startServiceWithNotification() {
        if (isServiceRunning) return;
        isServiceRunning = true;
        Log.d(TAG,"it goes to start service");
        Intent notificationIntent = new Intent(getApplicationContext(), ProfileActivity.class);
//        notificationIntent.setAction(C.ACTION_MAIN);  // A string containing the action name
        notificationIntent.setAction("com.example.stayontouch.onDestroy");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setChannelId("tstService")
                .setTicker(getResources().getString(R.string.app_name))
                .setContentText(getResources().getString(R.string.watch_enabled))
                .setSmallIcon(R.drawable.logo)
                .setContentText("Some Text")
                .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(contentPendingIntent)
                .setOngoing(true)
//                .setDeleteIntent(contentPendingIntent)  // if needed
                .build();
        notification.flags = notification.flags | Notification.FLAG_NO_CLEAR;     // NO_CLEAR makes the notification stay when the user performs a "delete all" command
        startForeground(1, notification);
    }


    private void startMyOwnForeground(){
        Intent intent = new Intent(this, InitialActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
        Notification n  = new Notification.Builder(this)
                .setContentTitle("New mail from " + "test@gmail.com")
                .setContentText("Subject")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .addAction(R.drawable.logo, "Call", pIntent)
                .addAction(R.drawable.logo, "More", pIntent)
                .addAction(R.drawable.logo, "And more", pIntent).build();


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);
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
        bigText.setBigContentTitle("Do not turn of at least your internet");
        bigText.setSummaryText("The best approach is turn on JPS and network services on your phone for more precise accuracy");
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


    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}
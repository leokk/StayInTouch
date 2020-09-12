package com.example.stayontouch.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.Utils.Constants;
import com.example.stayontouch.web.RetrofitWrapper;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    Timer timer;
    public int counter=0;
    User user;
    private TimerTask timerTask;
    private final IBinder binder = new LocalBinder();
    private String TAG = "MyService";

    public MyService() { }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startTimer();
        return START_STICKY;
    }

    public void startTimer() {
        final String  androidId =  android.provider.Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                user = new RetrofitWrapper().login(new User(androidId));

            }
        };
        timer.schedule(timerTask, 1, Constants.SEND_TIMER); //todo change period
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder  extends Binder {
         public MyService getService() {
            return MyService.this;
        }
        public User getUser(){
             return user;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    public User getUser() {
        return this.user;
    }
}
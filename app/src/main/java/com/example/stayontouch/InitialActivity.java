package com.example.stayontouch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.Utils.ServiceChecker;
import com.example.stayontouch.Utils.TelephonyServiceChecker;
import com.example.stayontouch.web.UserInterface;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialActivity extends AppCompatActivity {
    private String imei;
    private boolean connected;
    @BindView(R.id.connectionProgressBar)
    ProgressBar progressBar;
    private static final String TAG = "Initial";
    private static String loginUrl = "https://stay-in-touch-server.herokuapp.com/";
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(loginUrl)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();


    private void onConnectionResult(boolean result){
        if(result){
            Intent intent = new Intent(InitialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            showToast("Trying to reconnect");
            new InitialActivity.LoginUser().execute();
        }
    }

    @SuppressLint("HardwareIds")
    public static String getDeviceIMEI(Activity activity) {

        String deviceUniqueIdentifier = null;
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (null != tm) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            else
                deviceUniqueIdentifier = tm.getDeviceId();
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length())
                deviceUniqueIdentifier = "0";
        }
        return deviceUniqueIdentifier;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imei = TelephonyServiceChecker.getDeviceIMEI();
        new InitialActivity.LoginUser().execute();
        setContentView(R.layout.activity_initial);
    }


    private class LoginUser extends AsyncTask<Void, Void, Void> {

        private boolean result;
        public LoginUser() { }

        @Override
        protected Void doInBackground(Void... params) {
            UserInterface userInterface = retrofit.create(UserInterface.class);

            String imei = "032841231823123";
            User user = new User(imei);
            Call<User> call = userInterface.logInUser(imei);

            try {
                retrofit2.Response<User> response = call.execute();
                Log.d(TAG, "response is: "+ new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    result = true;
                    Log.d(TAG, "good");
                }


            } catch (IOException e) {
                result = false;
                Log.d(TAG, "bad");
                Log.d(TAG, e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onConnectionResult(result);
        }
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(InitialActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
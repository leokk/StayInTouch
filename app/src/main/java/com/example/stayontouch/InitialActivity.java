package com.example.stayontouch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.Utils.ServiceChecker;
import com.example.stayontouch.Utils.TelephonyServiceChecker;
import com.example.stayontouch.web.RetrofitWrapper;
import com.example.stayontouch.web.UserInterface;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialActivity extends AppCompatActivity {
    private String androidId ;
    private boolean connected;
    User user;
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
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }
        else{
            showToast("Trying to reconnect");
            new InitialActivity.LoginUser().execute();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidId =  android.provider.Settings.System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        new InitialActivity.LoginUser().execute();
        setContentView(R.layout.activity_initial);
    }


    private class LoginUser extends AsyncTask<Void, Void, Void> {

        private boolean result;

        @Override
        protected Void doInBackground(Void... params) {
            RetrofitWrapper wrapper = new RetrofitWrapper();
            user = wrapper.login(new User(androidId));
            if(user!=null)
                result = true;
            else
                result = false;
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
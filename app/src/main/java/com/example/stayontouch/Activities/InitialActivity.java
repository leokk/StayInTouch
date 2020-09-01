package com.example.stayontouch.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.Utils.Preferences;
import com.example.stayontouch.web.RetrofitWrapper;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialActivity extends AppCompatActivity {
    private String androidId ;
    private boolean connected;
    User user;
    @BindView(R.id.connectionProgressBar)
    ProgressBar progressBar;
    private static final String TAG = "Initial";

    private void onConnectionResult(boolean result){
        if(result){
            Intent intent = new Intent(InitialActivity.this, ProfileActivity.class);
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
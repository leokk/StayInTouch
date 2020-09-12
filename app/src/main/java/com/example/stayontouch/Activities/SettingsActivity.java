package com.example.stayontouch.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.Utils.Preferences;
import com.example.stayontouch.web.RetrofitWrapper;

public class SettingsActivity extends AppCompatActivity {
    User user;
    Switch isWatchEnabled = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.user = (User) bundle.getSerializable("user");
        }
        isWatchEnabled = findViewById(R.id.switchIsWatchEnabled);
        isWatchEnabled.setChecked(this.user.isWatchEnabled());
        isWatchEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                user.setWatchEnabled(b);
                Preferences preferences = new Preferences(getApplicationContext());
                preferences.setUserPrefs(user);
                User Test = preferences.getUserPrefs();

                Log.d("userrr","from settings prefs "+Test.toString());

//                todo save user in preferences
            }
        });
    }


    private void returnResult(Boolean b){
        if(b){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("user",user);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }else{
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }

    }

    private void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SettingsActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }


    private class UserUpdater extends AsyncTask<Void, Void, Void> {
        User usr;
        Context context;
        public UserUpdater(Context context,  User user) {
            this.usr = user;
            this.context = context;
        }


        @Override
        protected Void doInBackground(Void... params) {
            Preferences preferences = new Preferences(context);
            preferences.setUserPrefs(usr);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onConnectionResult();
        }
    }

    @Override
    public void onBackPressed() {
        onConnectionResult();
        super.onBackPressed();
    }

    private void onConnectionResult() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("user", user);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
package com.example.stayontouch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.user = (User) bundle.getSerializable("user");
        }
        Switch isWatchEnabled = findViewById(R.id.switchIsWatchEnabled);

        isWatchEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                user.setWatchEnabled(b);
                new UserUpdater().execute();
//                new Preferences().saveUser(user);
//                todo save user in preferences
            }
        });
    }

    private class UserUpdater extends AsyncTask<Void, Void, Void> {

        private boolean result = false;

        @Override
        protected Void doInBackground(Void... params) {
            RetrofitWrapper wrapper = new RetrofitWrapper();
            user = wrapper.updateUser(user);
            if(user!=null && user.getMessage()==0)
                result = true;
            return null ;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onConnectionResult(result);
        }

    }

    private void onConnectionResult(boolean result) {
        switch (user.getMessage()){
            case 409:{
                showToast("try again later");
                returnResult(result);
                break;
            }
            case 0:{
                showToast("successfully updated");
                returnResult(result);
                break;
            }
        }
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






    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}
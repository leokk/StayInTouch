package com.example.stayontouch.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.Service.MyService;
import com.example.stayontouch.Utils.Preferences;
import com.example.stayontouch.web.RetrofitWrapper;

import butterknife.OnTextChanged;

public class ProfileSettingsActivity extends AppCompatActivity {
    User user;
    EditText fName;
    EditText lName;
    EditText pass;
    Button submit;

    private boolean firstLaunch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.user = (User) bundle.getSerializable("user");
            firstLaunch = this.user.getFirstName() == null;
        }

        setContentView(R.layout.activity_profile_settings);


        fName = (EditText) findViewById(R.id.fName);
        lName = (EditText) findViewById(R.id.lName);
        pass = (EditText) findViewById(R.id.password);
        setListeners();

    }

    private void setListeners() {
        submit = findViewById(R.id.submitSettingsButton);

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (validate()) {
                    user.setFirstName(fName.getText().toString());
                    user.setLastName(lName.getText().toString());
                    user.setPassword(pass.getText().toString());

                    new UserUpdater().execute();
//                    if(new RetrofitWrapper().upd(user)!=null)
//                        showToast("successfully changed");
//                    else
//                        showToast("error, try again later");
                }
                ;
            }
        });
    }




//    @OnTextChanged({R.id.fName, R.id.lName, R.id.password})
//     void setSubmitState(Boolean status){
//        if(status){
//            submit.setEnabled(false);
//            pass.setHint(R.string.password);
//        }
//        else{
//            fName.setText(user.getFirstName());
//            lName.setText(user.getLastName());
//            pass.setHint(R.string.passwordLast);
//            submit.setEnabled(true);
//        }
//
//    }

    public boolean validate() {// if form is valid and different
        boolean valid = true;

        if(user.getFirstName()==null && user.getLastName()==null){
            if(fName.length()==0 || lName.length()==0 || pass.length()==0){
                showToast("Enter your credentials!");
                valid = false;
            }
        }
        if (fName.length() < 2 && fName.length()>0 || fName.length() > 30) {
            fName.setError("between 2 and 30 characters");
            valid = false;
        } else
            fName.setError(null);
        if (lName.length() < 2 && lName.length()>0|| lName.length() > 30) {
            lName.setError("between 2 and 30 characters");
            valid = false;
        } else
            lName.setError(null);
        if (pass.length() < 5 && pass.length()>0 || pass.length() > 30) {
            pass.setError("between 6 and 30 characters");
            valid = false;
        } else
            pass.setError(null);

        if(fName.length()==0 && lName.length()==0 && pass.length()==0)
            valid = false;

        return valid;

    }

    private void onConnectionResult(boolean result) {
        switch (user.getMessage()) {
            case 409: {
                showToast("try again later");
                returnResult(result);
                break;
            }
            case 0: {
                showToast("successfully updated");
                returnResult(result);
                break;
            }
        }
    }

    private class UserUpdater extends AsyncTask<Void, Void, Void> {


        public UserUpdater() {
        }

        private boolean result = false;

        @Override
        protected Void doInBackground(Void... params) {
            RetrofitWrapper wrapper = new RetrofitWrapper();
            user = wrapper.updateUser(user);
            if (user != null && user.getMessage() == 0)
                result = true;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            onConnectionResult(result);
        }
    }

    private void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileSettingsActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void returnResult(Boolean b) {
        if(firstLaunch){
            Intent intent = new Intent(ProfileSettingsActivity.this, InitialActivity.class);
            startActivity(intent);
        }else {
            if (b) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("user", user);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        }


    }
}
package com.example.stayontouch.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.web.RetrofitWrapper;

public class ProfileSettingsActivity extends AppCompatActivity {
    User user;
    EditText fName ;
    EditText lName ;
    EditText pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.user = (User) bundle.getSerializable("user");
        }

        setContentView(R.layout.activity_profile_settings);

        fName =(EditText) findViewById(R.id.fName);
        lName =(EditText) findViewById(R.id.lName);
        pass =(EditText) findViewById(R.id.password);
        setListeners();
    }

    private void setListeners(){
        Button submit = (Button)findViewById(R.id.submitSettingsButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    user.setFirstName(fName.getText().toString());
                    user.setLastName(lName.getText().toString());
                    user.setPassword(pass.getText().toString());
                    new UserUpdater().execute();
//                    if(new RetrofitWrapper().upd(user)!=null)
//                        showToast("successfully changed");
//                    else
//                        showToast("error, try again later");
                };
            }
        });
    }
    public boolean validate() {
        boolean valid = true;

        if (fName.length() < 2 || fName.length() > 30) {
            fName.setError("between 3 and 30 characters");
            valid = false;
        }else
            fName.setError(null);
        if (lName.length() < 2 || lName.length() > 30) {
            lName.setError("between 3 and 30 characters");
            valid = false;
        }else
            lName.setError(null);
        if(pass.length()<5 || pass. length() > 30){
            pass.setError("between 6 and 30 characters");
            valid = false;
        }else
            pass.setError(null);

        return valid;
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
    private void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileSettingsActivity.this,
                        Text, Toast.LENGTH_LONG).show();
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


}
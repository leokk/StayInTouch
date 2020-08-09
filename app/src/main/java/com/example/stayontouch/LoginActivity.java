package com.example.stayontouch;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.web.UserInterface;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final int REQUEST_SIGNUP = 0;
    private static String url_Login = "https://questionare-test.herokuapp.com/account/";
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(url_Login)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        //todo change in signup
//        new LoginUser().execute(email,password,"fname","lname", String.valueOf(22),"+380999789564");

        new LoginUser(email,password).execute();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                }, 2000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Log.d(TAG,"UEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE You got this");
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _passwordText.setText("");
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 30) {
            _passwordText.setError("between 4 and 30 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


//_______________________________________________________________________________________________________________
//_______________________________________________________________________________________________________________

    public class LoginUser extends AsyncTask<Void, Void, Void> {

        private final String email;
        private final String pass;
        private boolean loginResult = false;
        private String errorMessage;

        public LoginUser(String email, String pass) {
            this.email = email;
            this.pass = pass;
        }


        @Override
        protected Void doInBackground(Void... params) {
            UserInterface userInterface =  retrofit.create(UserInterface.class);
            String base = email+":"+pass;

            String header = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

            Call<User> call = userInterface.getUser(header);
            try {
                retrofit2.Response<User> response=call.execute();
                if(response.isSuccessful()){
                    loginResult = true;
                }


            } catch (IOException e) {
                errorMessage = e.getMessage();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(loginResult)
                onLoginSuccess();
            else
                onLoginFailed();
        }

        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Password = strings[1];
//            String firstName= strings[2];;
//            String lastName= strings[3];;
//            int age = Integer.parseInt(strings[4]);
//            String phone= strings[5];;
            OkHttpClient okHttpClient = new OkHttpClient();
//            todo change in signup
            RequestBody formBody = new FormBody.Builder()
                    .add("email", Email)
                    .add("password",Password)
//                    .add("age", String.valueOf(age))
//                    .add("password",Password)
//                    .add("firstName",firstName)
//                    .add("lastName",lastName)
//                    .add("phone",phone)
                    .build();

            Request request = new Request.Builder()
                    .url(url_Login)
                    .post(formBody)
                    .build();

            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    showToast("Successs!!!!!!!!!!!!");
                    String result = response.body().string();
                    if(result.equalsIgnoreCase("login")){
                        Intent i = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        showToast("Email or Password mismatched!");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }



}
package com.example.stayontouch.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.stayontouch.Dialogs.AddSubordinateDialog;
import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.Utils.ServiceChecker;
import com.example.stayontouch.web.RetrofitWrapper;

public class MainActivity extends AppCompatActivity implements AddSubordinateDialog.AddSubordinateDialogListener {

    private static final String TAG = "MainActivity";
    private User user;
    private boolean result;

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onWatchingThisUser(){

        final ConstraintLayout constraintLayout = findViewById(R.id.constraintPersonalInfo);
        constraintLayout.setVisibility(ConstraintLayout.VISIBLE);

        final TextView login = findViewById(R.id.id);
        final TextView password = findViewById(R.id.password);

//        todo implement on watch mechanic
        Log.d(TAG,user.toString());
        login.setText("Login: "+user.getId().toString());

        //todo starting service
//        startService(new Intent(getApplicationContext(),MyService.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            this.user = (User) bundle.getSerializable("user");
        }

        // todo draw all slave entity
        dynamicUIDraw();
        setAddSubordinateButtonClickListener();

        ServiceChecker serviceChecker = new ServiceChecker(MainActivity.this);

        onWatchingThisUser();
        showToast("your unique id is: " + user.getAndroidId());
//        startService(new Intent(getApplicationContext(),MyService.class));
        if(serviceChecker.isServicesOK()){
//            init();
        }
    }

    private void setAddSubordinateButtonClickListener(){
        ImageButton btn = (ImageButton) findViewById(R.id.addSubordinate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSubordinateDialog dialog = new AddSubordinateDialog();
                dialog.show(getSupportFragmentManager(),"dialog");
            }
        });
    }

    private void redrawSubs(){
        final LinearLayout lm = findViewById(R.id.linearMain);
        lm.removeAllViews();
        dynamicUIDraw();
    }


    private void dynamicUIDraw(){

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Create four
        for (User u : user.getSubordinates()) {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create Button
            final Button btn = new Button(this);

//            btn.setId();

            btn.setText(u.getFirstName() + "   " + u.getLastName());
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "opening :" + btn.getText());

                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(),
                            "Clicked Button Index :" + btn.getText(),
                            Toast.LENGTH_LONG).show();

                }
            });

            //Add button to LinearLayout
            ll.addView(btn);
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }
    }

    @Override
    public void addSubordinate(Long id, String password) {
        boolean isok = true;
        if(!id.equals(this.user.getId())){
            User sub = new User(id, password);
            Log.d(TAG,"id + pas : "+ id+" "+ password);

            for(User u:user.getSubordinates()){
                if(u.getId().equals(sub.getId())){
                    showToast("you have already subscribed for dat guy"+ u.getId());
                    isok = false;
                }
            }
            if(isok){
                this.user.getSubordinates().add(sub);
                Log.d("subs",user.toString());
                new SubordinateToServer().execute();
            }
        }
        else
        {
            showToast("sooo, u wanna subscribe for yourself haha))");
        }
    }



    private class SubordinateToServer extends AsyncTask<Void, Void, Void> {

        private boolean result;

        @Override
        protected Void doInBackground(Void... params) {
            RetrofitWrapper wrapper = new RetrofitWrapper();
            user = wrapper.addSubordinates(user);
            if(user.getMessage()==0)
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

    private void onConnectionResult(boolean result) {
        if(result){
            redrawSubs();
            return;
        }

        else if(user.getMessage()==409){
            showToast("bad credentials, try again");
            user.setMessage(0);
        }

        user.getSubordinates().remove(user.getSubordinates().size()-1);
    }


}
package com.example.stayontouch;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.Utils.ServiceChecker;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private User user;


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
        if(user.isWatchEnabled()){
            final ConstraintLayout constraintLayout = findViewById(R.id.constraintPersonalInfo);
            constraintLayout.setVisibility(ConstraintLayout.VISIBLE);
        }
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

        ServiceChecker serviceChecker = new ServiceChecker(MainActivity.this);

        onWatchingThisUser();
        showToast("your unique id is: " + user.getAndroidId());
        if(serviceChecker.isServicesOK()){
//            init();
        }
    }

//    private void init(){
//        Button btnMap = (Button) findViewById(R.id.ok_button);
//        btnMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
//            }
//        });
//    }

    private void dynamicUIDraw(){

        final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Create four
        for (User u : user.getiFollow()) {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            // Create Button
            final Button btn = new Button(this);

//            btn.setId();

            btn.setText(u.getFirstName() + u.getLastName());
            // set the layoutParams on the button
            btn.setLayoutParams(params);


            // Set click listener for button
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.i("TAG", "opening :" + btn.getText());

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
}




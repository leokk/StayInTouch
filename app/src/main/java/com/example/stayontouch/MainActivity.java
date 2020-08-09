package com.example.stayontouch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Utils.ServiceChecker;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ServiceChecker serviceChecker = new ServiceChecker(MainActivity.this);

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
        final ScrollView scroll = findViewById(R.id.scrollMain);

            final LinearLayout lm = (LinearLayout) findViewById(R.id.linearMain);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //Create four
            for(int j=0;j<=4;j++)
            {
                // Create LinearLayout
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                // Create TextView
                TextView product = new TextView(this);
                product.setText(" Product"+j+"    ");
                ll.addView(product);

                // Create TextView
                TextView price = new TextView(this);
                price.setText("  $"+j+"     ");
                ll.addView(price);

                // Create Button
                final Button btn = new Button(this);
                // Give button an ID
                btn.setId(j+1);
                btn.setText("Add To Cart");
                // set the layoutParams on the button
                btn.setLayoutParams(params);

                final int index = j;
                // Set click listener for button
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Log.i("TAG", "index :" + index);

                        Toast.makeText(getApplicationContext(),
                                "Clicked Button Index :" + index,
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




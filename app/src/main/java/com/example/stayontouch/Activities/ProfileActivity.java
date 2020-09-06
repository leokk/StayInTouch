package com.example.stayontouch.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.stayontouch.Entitie.User;
import com.example.stayontouch.R;
import com.example.stayontouch.Service.YourService;
import com.example.stayontouch.Utils.ServiceChecker;
import com.example.stayontouch.web.RetrofitWrapper;

public class ProfileActivity extends AppCompatActivity {
    User user = null;
    User FF = null;
    private static final String TAG = "MapActivity";
    private YourService mSensorService;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.user = (User) bundle.getSerializable("user");
        }
        if (!new ServiceChecker(this).isServicesOK())
            getLocationPermission();
        setOnclickListeners();
        startServiceOnce();

    }


    private void setOnclickListeners() {
        Button settings = findViewById(R.id.toSettings);
        Button account = findViewById(R.id.toAccountSettings);
        Button subs = findViewById(R.id.getSubs);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, SettingsActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileSettingsActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 2);

            }
        });
        subs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                intent.putExtra("user", user);
                startActivityForResult(intent, 1);
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d("MyService", "Running");
                return true;
            }
        }
        Log.d("MyService", "Not running");
        return false;
    }

    private void startServiceOnce() {
        if (!isMyServiceRunning(YourService.class)) {
            mSensorService = new YourService(this);
            Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
            startService(mServiceIntent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("userrr","AAAAAAAAAAAAA");
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("userrr","FFFFFFFFFFFFFFFFFFFFFF");
                User neww = (User) data.getSerializableExtra("user");
//                if (this.user.equals(neww)) {
                    this.user = neww;
                    new UserUpdater(neww).execute();
//                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private class UserUpdater extends AsyncTask<Void, Void, User> {

        User requestUser;

        public UserUpdater(User user) {
            this.requestUser = user;
        }

        @Override
        protected void onPostExecute(User u) {
            Log.d("user",u.toString());
            user = u;
        }

        @Override
        protected User doInBackground(Void... params) {
            User u = new RetrofitWrapper().updateUser(user);

            if(u!=null && u.getMessage()==0)
                return user;
            return null ;
        }
    }


    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                //todo some stuff
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

}
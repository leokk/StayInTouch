package com.example.stayontouch.Utils;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Entitie.User;
import com.google.gson.Gson;

public class Preferences extends AppCompatActivity {
    SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);;
    public void saveUser(User user) {

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString("User", json);
        prefsEditor.apply();
    }
    public User getUser(){
        Gson gson = new Gson();
        String json = mPrefs.getString("User", "");
        User user = gson.fromJson(json, User.class);
        Log.d("prefs",user.toString());
        return user;
    }
}

package com.example.stayontouch.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stayontouch.Entitie.User;
import com.google.gson.Gson;

public class Preferences extends AppCompatActivity {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;

    public Preferences(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_MULTI_PROCESS);
    }

    public User getUserPrefs() {
        Gson gson = new Gson();
        String json = sharedpreferences.getString("user","");
        User user = gson.fromJson(json, User.class);

        int count = sharedpreferences.getInt("count", -1);
        return user;
    }

    public void setUserPrefs(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
//        prefsEditor.putString("User", json);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("user", json);
        editor.apply();
    }

    public void clearUser() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("user");
        editor.apply();
    }



    //    static SharedPreferences  mPrefs;
//    public Preferences() {
//        mPrefs = getPreferences(MODE_PRIVATE);;
//    }
//
//
//    public static void saveUserPrefs(User user) {
//
//        SharedPreferences.Editor prefsEditor = mPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(user);
//        prefsEditor.putString("User", json);
//        prefsEditor.apply();
//    }
//    public static User getUserPrefs(){
//        Gson gson = new Gson();
//        String json = mPrefs.getString("User", "");
//        User user = gson.fromJson(json, User.class);
//        Log.d("prefs",user.toString());
//        return user;
//    }
}

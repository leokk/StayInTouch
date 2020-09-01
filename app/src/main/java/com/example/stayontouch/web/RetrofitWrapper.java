package com.example.stayontouch.web;

import android.util.Log;

import com.example.stayontouch.Entitie.User;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitWrapper {
    private static String mainUrl = "https://stay-in-touch-server.herokuapp.com/";
    private static String login = "login";
    private static String subs = "subs";
//    private User user;
    Retrofit.Builder builder = new Retrofit.Builder();
    Retrofit retrofit;

    public RetrofitWrapper( ) {

        builder.baseUrl(mainUrl)
            .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
    }

    public User login(User user){
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<User> call = userInterface.logInUser(user);
        try {
            retrofit2.Response<User> response = call.execute();
            Log.d(login, "response is: "+ new Gson().toJson(response.body()));
            if (response.isSuccessful()) {
                user = response.body();
                Log.d(login,"user is: " + user.toString());
                Log.d(login, "good");
                return user;
            }
        } catch (IOException e) {
            Log.d(login, "bad");
            Log.d(login, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public User addSubordinates(User user){
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Log.d(subs,user.toString());
        Call<User> call = userInterface.addSubordinates(user);
        try {
            retrofit2.Response<User> response = call.execute();
            Log.d(subs, "response is: "+ new Gson().toJson(response.body()));
            if (response.isSuccessful()) {
                user = response.body();
                Log.d(subs,"user is: " + user.toString());
                Log.d(subs, "good");
                return user;
            }
            else{
                user.setMessage(response.code());
                return user ;
            }

        } catch (RuntimeException | IOException e) {
            Log.d(subs, "bad");
            Log.d(subs, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

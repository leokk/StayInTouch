package com.example.stayontouch.web;

import com.example.stayontouch.Entitie.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("loginn")
    @Headers("Content-Type: application/json")
    Call<User> getUser(@Header("Authorization")String authHeader);

    @POST("login")
    @Headers("Content-Type: application/json")
    Call<User> logInUser(@Body String imei);

    @GET("T")
    @Headers("Content-Type: application/json")
    Call<User> testReq(String imei);

}

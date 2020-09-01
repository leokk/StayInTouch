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
    Call<User> logInUser(@Body User user);

    @POST("update")
    @Headers("Content-Type: application/json")
    Call<User>updateUser(@Body User user);

    @POST("follow")
    @Headers("Content-Type: application/json")
    Call<User>addSubordinates(@Body User user);



    @POST("coords")
    @Headers("Content-Type: application/json")
    Call<String>sendCoordinates(@Body User user);

    @GET("T")
    @Headers("Content-Type: application/json")
    Call<User> testReq(String imei);

}

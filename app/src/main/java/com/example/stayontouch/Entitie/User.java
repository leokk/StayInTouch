package com.example.stayontouch.Entitie;

import android.provider.Settings;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
    private String androidId ;
    private String email;
    private Long login;
    private String password;
    private int age;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean watchEnabled;



    private double posx;
    private double posy;
    private Set<User> iFollow = new HashSet<User>();

    public Set<User> getiFollow() {
        return iFollow;
    }

    public void setiFollow(Set<User> iFollow) {
        this.iFollow = iFollow;
    }

    private Set<User> followers = new HashSet<User>();

    public User(String androidId, String password) {
        this.androidId = androidId;
        this.password = password;
    }

    public String getAndroidId() {
        return androidId;
    }

    public boolean isWatchEnabled() {
        return watchEnabled;
    }

    public void setWatchEnabled(boolean watchEnabled) {
        this.watchEnabled = watchEnabled;
    }

    public User(String androidId) {
        this.androidId = androidId;
    }

    public User(String firstName, String lastName, String password, int age, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public User() {
    }

    public User(double posx, double posy) {
        this.posx = posx;
        this.posy = posy;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public User(String name, double posx, double posy) {
        this.firstName = name;
        this.posx = posx;
        this.posy = posy;
    }

    public double getPosx() {
        return posx;
    }

    public double getPosy() {
        return posy;
    }

    public User(String name, int id, double posx, double posy) {
        this.firstName = name;
        this.posx = posx;
        this.posy = posy;
    }

}


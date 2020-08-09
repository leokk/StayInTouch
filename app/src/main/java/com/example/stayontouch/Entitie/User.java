package com.example.stayontouch.Entitie;

import java.util.HashSet;
import java.util.Set;

public class User {
    private Long id;
    private String email;
    private Long login;
    private String imei;
    private String password;
    private int age;
    private String firstName;
    private String lastName;
    private String phone;
    private double posx;
    private double posy;


    private Set<User> followers = new HashSet<User>();

    private Set<User> iFollow = new HashSet<User>();

    public User(String imei) {
        this.imei = imei;
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

    public User(String name, String emale, int id, double posx, double posy) {
        this.firstName = name;

        this.posx = posx;
        this.posy = posy;
    }

}


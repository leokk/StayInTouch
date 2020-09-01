package com.example.stayontouch.Entitie;

import android.provider.Settings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private String androidId ;
    private String email;
    private Long login;
    private Long id;
    private String password;
    private int age;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean watchEnabled;


    private double posx;
    private double posy;
    private List<User> subordinates = new ArrayList<>();
    private int message;


    public User(String androidId, String password) {
        this.androidId = androidId;
        this.password = password;
    }

    public User(Long id, String password) {
        this.id = id;
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "androidId='" + androidId + '\'' +
                ", email='" + email + '\'' +
                ", login=" + login +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", watchEnabled=" + watchEnabled +
                ", posx=" + posx +
                ", posy=" + posy +
                ", subordinates=" + subordinates +
                '}';
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPosx(double posx) {
        this.posx = posx;
    }

    public void setPosy(double posy) {
        this.posy = posy;
    }

    public List<User> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<User> subordinates) {
        this.subordinates = subordinates;
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


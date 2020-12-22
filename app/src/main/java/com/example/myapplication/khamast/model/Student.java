package com.example.myapplication.khamast.model;

import android.media.Image;

import java.util.ArrayList;

public class Student {

    private  String key;
    private String emailstu, passwordstu,namestu,phonestu,city,hewayah;
    private Double longitudestu, latitudestu;


    private ArrayList<Image> images ;

    public Student() {
    }

    public Student(String key, String emailstu, String passwordstu, String namestu, String phonestu, String city, String hewayah, Double longitudestu, Double latitudestu, ArrayList<Image> images) {
        this.key = key;
        this.emailstu = emailstu;
        this.passwordstu = passwordstu;
        this.namestu = namestu;
        this.phonestu = phonestu;
        this.city = city;
        this.hewayah = hewayah;
        this.longitudestu = longitudestu;
        this.latitudestu = latitudestu;
        this.images = images;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmailstu() {
        return emailstu;
    }

    public void setEmailstu(String emailstu) {
        this.emailstu = emailstu;
    }

    public String getPasswordstu() {
        return passwordstu;
    }

    public void setPasswordstu(String passwordstu) {
        this.passwordstu = passwordstu;
    }

    public String getNamestu() {
        return namestu;
    }

    public void setNamestu(String namestu) {
        this.namestu = namestu;
    }

    public String getPhonestu() {
        return phonestu;
    }

    public void setPhonestu(String phonestu) {
        this.phonestu = phonestu;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHewayah() {
        return hewayah;
    }

    public void setHewayah(String hewayah) {
        this.hewayah = hewayah;
    }

    public Double getLongitudestu() {
        return longitudestu;
    }

    public void setLongitudestu(Double longitudestu) {
        this.longitudestu = longitudestu;
    }

    public Double getLatitudestu() {
        return latitudestu;
    }

    public void setLatitudestu(Double latitudestu) {
        this.latitudestu = latitudestu;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

}

package com.example.myapplication.khamast.model;

import android.media.Image;

import java.util.ArrayList;

public class Resturant {

    private  String key;
    private String emailres, passwordres,nameres,phoneres,city,hewayahres;
    private Double longituderes, latituderes;
    private ArrayList<Image> images ;
    public Resturant() {
    }

    public Resturant(String key, String emailres, String passwordres, String nameres, String phoneres, String city, String hewayahres, Double longituderes, Double latituderes, ArrayList<Image> images) {
        this.key = key;
        this.emailres = emailres;
        this.passwordres = passwordres;
        this.nameres = nameres;
        this.phoneres = phoneres;
        this.city = city;
        this.hewayahres = hewayahres;
        this.longituderes = longituderes;
        this.latituderes = latituderes;
        this.images = images;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmailres() {
        return emailres;
    }

    public void setEmailres(String emailres) {
        this.emailres = emailres;
    }

    public String getPasswordres() {
        return passwordres;
    }

    public void setPasswordres(String passwordres) {
        this.passwordres = passwordres;
    }

    public String getNameres() {
        return nameres;
    }

    public void setNameres(String nameres) {
        this.nameres = nameres;
    }

    public String getPhoneres() {
        return phoneres;
    }

    public void setPhoneres(String phoneres) {
        this.phoneres = phoneres;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHewayahres() {
        return hewayahres;
    }

    public void setHewayahres(String hewayahres) {
        this.hewayahres = hewayahres;
    }

    public Double getLongituderes() {
        return longituderes;
    }

    public void setLongituderes(Double longituderes) {
        this.longituderes = longituderes;
    }

    public Double getLatituderes() {
        return latituderes;
    }

    public void setLatituderes(Double latituderes) {
        this.latituderes = latituderes;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}


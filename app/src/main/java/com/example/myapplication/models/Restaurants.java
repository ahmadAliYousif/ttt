package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Restaurants implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = "key_Res")
    @NonNull
    private String keyRes;
    @ColumnInfo(name = "password_Res")
    private String passwordRes;
    @ColumnInfo(name = "email_Res")
    private String emailRes;
    @ColumnInfo(name = "country_Res")
    private String countryRes;
    @ColumnInfo(name = "name_Res")
    private String nameRes;

    public Restaurants() {
    }

    public Restaurants(String keyRes, String passwordRes, String emailRes, String countryRes, String nameRes) {
        this.keyRes = keyRes;
        this.passwordRes = passwordRes;
        this.emailRes = emailRes;
        this.countryRes = countryRes;
        this.nameRes = nameRes;
    }

    public String getKeyRes() {
        return keyRes;
    }

    public void setKeyRes(String keyRes) {
        this.keyRes = keyRes;
    }

    public String getPasswordRes() {
        return passwordRes;
    }

    public void setPasswordRes(String passwordRes) {
        this.passwordRes = passwordRes;
    }

    public String getEmailRes() {
        return emailRes;
    }

    public void setEmailRes(String emailRes) {
        this.emailRes = emailRes;
    }

    public String getCountryRes() {
        return countryRes;
    }

    public void setCountryRes(String countryRes) {
        this.countryRes = countryRes;
    }

    public String getNameRes() {
        return nameRes;
    }

    public void setNameRes(String nameRes) {
        this.nameRes = nameRes;
    }
}

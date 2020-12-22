package com.example.myapplication.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Student implements Serializable {
    @ColumnInfo(name = "name_std")
    private String nameStd;
    @ColumnInfo(name = "key_std")
    @PrimaryKey()
    @NonNull
    private String keyStd;
    @ColumnInfo(name = "email_std")
    private String emailStd;
    @ColumnInfo(name = "password_std")
    private String passwordStd;
    @ColumnInfo(name = "country_std")
    private String countryStd;
    public Student() {
    }

    public Student(String nameStd, String keyStd, String emailStd, String passwordStd, String countryStd) {
        this.nameStd = nameStd;
        this.keyStd = keyStd;
        this.emailStd = emailStd;
        this.passwordStd = passwordStd;
        this.countryStd = countryStd;
    }

    public String getNameStd() {
        return nameStd;
    }

    public void setNameStd(String nameStd) {
        this.nameStd = nameStd;
    }

    public String getKeyStd() {
        return keyStd;
    }

    public void setKeyStd(String keyStd) {
        this.keyStd = keyStd;
    }

    public String getEmailStd() {
        return emailStd;
    }

    public void setEmailStd(String emailStd) {
        this.emailStd = emailStd;
    }

    public String getPasswordStd() {
        return passwordStd;
    }

    public void setPasswordStd(String passwordStd) {
        this.passwordStd = passwordStd;
    }

    public String getCountryStd() {
        return countryStd;
    }

    public void setCountryStd(String countryStd) {
        this.countryStd = countryStd;
    }
}

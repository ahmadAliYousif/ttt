package com.example.myapplication.khamast.model;

public class Orders {



    private  String keys;
    private String emailstu,namestu,phonestu,citystu,hewayah, emailres,nameres,phoneres,cityres,hewayahres;
    private Double longitudestu, latitudestu ,longituderes, latituderes;


    public Orders() {
    }

    public Orders(String keys, String emailstu, String namestu, String phonestu, String citystu, String hewayah,
                  String emailres, String nameres, String phoneres, String cityres, String hewayahres, Double longitudestu, Double latitudestu, Double longituderes, Double latituderes) {
        this.keys = keys;
        this.emailstu = emailstu;
        this.namestu = namestu;
        this.phonestu = phonestu;
        this.citystu = citystu;
        this.hewayah = hewayah;
        this.emailres = emailres;
        this.nameres = nameres;
        this.phoneres = phoneres;
        this.cityres = cityres;
        this.hewayahres = hewayahres;
        this.longitudestu = longitudestu;
        this.latitudestu = latitudestu;
        this.longituderes = longituderes;
        this.latituderes = latituderes;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getEmailstu() {
        return emailstu;
    }

    public void setEmailstu(String emailstu) {
        this.emailstu = emailstu;
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

    public String getCitystu() {
        return citystu;
    }

    public void setCitystu(String citystu) {
        this.citystu = citystu;
    }

    public String getHewayah() {
        return hewayah;
    }

    public void setHewayah(String hewayah) {
        this.hewayah = hewayah;
    }

    public String getEmailres() {
        return emailres;
    }

    public void setEmailres(String emailres) {
        this.emailres = emailres;
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

    public String getCityres() {
        return cityres;
    }

    public void setCityres(String cityres) {
        this.cityres = cityres;
    }

    public String getHewayahres() {
        return hewayahres;
    }

    public void setHewayahres(String hewayahres) {
        this.hewayahres = hewayahres;
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
}

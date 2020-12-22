package com.example.myapplication.khamast;

import android.widget.Toast;

import java.util.ArrayList;

public interface OrdeCallback {

    public void Onsucess(double lat, double lng);

    public void OnError(String errMsg);

    public void Onsucess(ArrayList<Orde> ordes);

}

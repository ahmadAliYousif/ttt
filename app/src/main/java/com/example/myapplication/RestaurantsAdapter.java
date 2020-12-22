package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.models.Restaurants;

import java.util.ArrayList;

public class RestaurantsAdapter extends BaseAdapter {

    Context context;
    int resourse;
    ArrayList<Restaurants> arrayList;


    public RestaurantsAdapter(Context context, int resourse, ArrayList<Restaurants> restaurants) {
        this.context = context;
        this.resourse = resourse;
        this.arrayList = restaurants;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Restaurants getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(resourse, null, false);
        }

        TextView tv_name = view.findViewById(R.id.tv_resturansts_name);

        Restaurants restaurants = getItem(position);

        tv_name.setText(restaurants.getNameRes());

        return view;
    }
}

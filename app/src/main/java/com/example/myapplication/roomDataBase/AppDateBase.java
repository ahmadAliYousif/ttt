package com.example.myapplication.roomDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.myapplication.interfaces.RoomInterface;
import com.example.myapplication.models.Restaurants;
import com.example.myapplication.models.Student;

@Database(entities = {Student.class, Restaurants.class}, version = 1)
public abstract class AppDateBase extends RoomDatabase {
    public abstract RoomInterface roomInterface();

}

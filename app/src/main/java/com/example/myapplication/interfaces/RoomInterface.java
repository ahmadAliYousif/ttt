package com.example.myapplication.interfaces;

import android.content.pm.ResolveInfo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myapplication.R;
import com.example.myapplication.models.Restaurants;
import com.example.myapplication.models.Student;

import java.util.List;

@Dao
public interface RoomInterface {
    @Query("SELECT * FROM Student")
    List<Student> getAllStudent();


    @Query("SELECT * FROM Student WHERE  name_std = :nameStd")
    Student searchRStudentByName(String nameStd);

    @Query("SELECT * FROM Student WHERE country_std = :countryStd")
    List<Student> searchStudentCountry(String countryStd);


    @Query("DELETE FROM Student")
    public void deleteStudentTable();


    @Insert
    void insertStd(Student Student);

    @Insert
    void insertListStdItems(List<Student> StudentList);

    @Delete
    void delete(Student Student);

    @Update
    void update(Student Student);


    @Query("SELECT * FROM Restaurants")
    List<Restaurants> getAllRes();


//    @Query("SELECT * FROM Restaurants WHERE  name_Res = :nameRes")
//    Student searchByResName(String nameRes);

    @Query("SELECT * FROM Restaurants WHERE country_Res = :countryRes")
    List<Restaurants> searchResByCountry(String countryRes);


    @Query("DELETE FROM Restaurants")
    public void deleteResTable();


    @Insert
    void insertRes(Restaurants restaurants);

    @Insert
    void insertListRes(List<Restaurants> restaurants);

    @Delete
    void deleteRes(Restaurants restaurants);

    @Update
    void updateRes(Restaurants restaurants);


}

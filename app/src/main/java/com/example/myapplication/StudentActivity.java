package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.myapplication.models.Restaurants;
import com.example.myapplication.models.Student;
import com.example.myapplication.roomDataBase.DatabaseClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_remove_request_std, btn_sign_out, Btn_remove_request_std;
    Spinner spinner;
    RestaurantsAdapter restaurantsAdapter;
    ArrayList<Restaurants> restaurantsArrayList;
    FirebaseAuth mAuth;
    Student student;
    Restaurants restaurants;
    ArrayList<String> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        define();
        mAuth = FirebaseAuth.getInstance();
        restaurantsArrayList = new ArrayList<>();

        arrayList = new ArrayList<>();
        Intent getIntent = getIntent();
        student = (Student) getIntent.getSerializableExtra("std");
        getAllRestaurants(student);
//        getAllResRequs(student);
        btn_remove_request_std.setOnClickListener(this::onClick);
        btn_sign_out.setOnClickListener(this::onClick);
        Btn_remove_request_std.setOnClickListener(this::onClick);

    }

    @Override
    protected void onStart() {
        super.onStart();


        restaurantsAdapter = new RestaurantsAdapter(getBaseContext(), R.layout.restaurents, restaurantsArrayList);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                restaurants = restaurantsArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.std_btn_sign_out:
                mAuth.signOut();

                break;
            case R.id.std_btm_delete_req_std:
                deleteRestaurantRequest(restaurants, student);
                break;
            case R.id.std_btm_send_req:
                setRequest(student, restaurants);
                break;


        }
    }

    public void define() {

        btn_remove_request_std = findViewById(R.id.std_btm_delete_req_std);
        btn_sign_out = findViewById(R.id.std_btm_delete_req_std);
        Btn_remove_request_std = findViewById(R.id.std_btm_delete_req_std);
        spinner = findViewById(R.id.std_spinner);
        listView = findViewById(R.id.std_std_list);

    }

    public void setRequest(Student student, Restaurants restaurants) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("restaurants");


        databaseReference.child("restaurants").child(restaurants.getKeyRes()).child("requests").child(student.getKeyStd() + "")
                .child("requestInfo").setValue(student);
        databaseReference.child("restaurants").child(restaurants.getKeyRes()).child("requests").child(student.getKeyStd() + "")
                .child("chat").setValue("chatting ..");


        databaseReference.child("Student").child(student.getKeyStd()).child("myReq").child(restaurants.getKeyRes()).setValue(restaurants);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DatabaseClient.getInstance(getBaseContext()).getAppDatabase()
                            .roomInterface().insertRes(restaurants);
                } catch (Exception e) {
                }

            }
        }).start();


    }

    public void startChatFromStudent(Student student) {
        List<Restaurants> restaurantsList = getAllResRequseted(student);
        Restaurants restaurant1 = null;
        for (Restaurants restaurants : restaurantsList) {

            Log.d("qqqqq", restaurants.getNameRes());
            restaurant1 = restaurants;
        }
        // to chatting with restaurant
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("restaurants").child(restaurant1.getKeyRes()).child("requests").child(student.getKeyStd() + "")
                .child("chat").setValue("chatting ..");

    }

    public List<Restaurants> getAllResRequseted(Student student) {
        final List<Restaurants>[] list = new List[0];
        Semaphore semaphore = new Semaphore(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list[0] = DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().getAllRes();
                semaphore.release();
            }
        }).start();
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {

        }
        return list[0];

    }

    public List<String> getAllResRequs(Student student) {
        Semaphore semaphore = new Semaphore(0);
        List<String> list = new ArrayList<>();

        final Restaurants[] restaurants = new Restaurants[1];
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().
                child("Student").child(student.getKeyStd()).child("myReq");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    restaurants[0] = dataSnapshot.getValue(Restaurants.class);
                    if (restaurants[0] != null) {
                        list.add(restaurants[0].getEmailRes());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().insertRes(restaurants[0]);

                                } catch (Exception ignored) {
                                }

                            }
                        }).start();

                    }
                }
                semaphore.release();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getAllRestaurants(Student student) {
        Semaphore semaphore = new Semaphore(0);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Restaurants restaurants = dataSnapshot.getValue(Restaurants.class);
                    Log.d("ssssss", dataSnapshot.getKey());
                    if (restaurants != null) {
                        if (restaurants.getCountryRes().equalsIgnoreCase(student.getCountryStd())) {
                            restaurantsArrayList.add(restaurants);
                            Log.d("ssssss", restaurants.getNameRes());
                            setRequest(student, restaurants);

                        }

                    }
                }
//                semaphore.release();
                databaseReference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        try {
//            semaphore.acquire();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void chattingRes(Restaurants restaurants) {
        final String[] keyStudent = {""};
        Semaphore semaphore = new Semaphore(0);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("restaurants").child(restaurants.getKeyRes()).child("requests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //choose one
                    keyStudent[0] = dataSnapshot.getValue(Student.class).getKeyStd();


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void deleteRestaurantRequest(Restaurants restaurants, Student student) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference()
                .child("restaurants").child(restaurants.getKeyRes()).child("requests").child(student.getKeyStd() + "").setValue(null);

        firebaseDatabase.getReference().
                child("Student").child(student.getKeyStd()).child("myReq").child(restaurants.getKeyRes()).setValue(null);
    }

}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.myapplication.interfaces.RoomInterface;
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
import java.util.concurrent.Semaphore;

public class ResActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_sign_out, btn_remove_request_res;
    Spinner spinner;
    ListView listView;
    FirebaseAuth mAuth;
    ArrayAdapter<String> studentArrayList;
    Restaurants restaurants;

    @Override
    protected void onStart() {
        super.onStart();
        studentArrayList = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        listView.setAdapter(studentArrayList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res);
        mAuth = FirebaseAuth.getInstance();
        Intent getIntent = getIntent();
        restaurants = (Restaurants) getIntent.getSerializableExtra("res");
        showAllStudents(restaurants);
        btn_sign_out = findViewById(R.id.res_btn_sign_out);
        btn_remove_request_res = findViewById(R.id.res_btm_delete_req_res);
        spinner = findViewById(R.id.res_spinner);
        listView = findViewById(R.id.res_std_list);

        btn_sign_out.setOnClickListener(this::onClick);
        btn_remove_request_res.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.res_btn_sign_out:
                mAuth.signOut();
                break;
            case R.id.res_btm_delete_req_res:
                Semaphore semaphore = new Semaphore(0);
                final Student[] student = new Student[1];
                final Restaurants[] restaurants = new Restaurants[1];
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RoomInterface roomInterface = DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface();
                        student[0] = roomInterface.getAllStudent().get(0);
                        restaurants[0] = roomInterface.getAllRes().get(0);
                        semaphore.release();
                    }
                }).start();
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deleteRestaurantRequest(restaurants[0], student[0]);
                break;


        }

    }

    public void deleteRestaurantRequest(Restaurants restaurants, Student student) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference()
                .child("restaurants").child(restaurants.getKeyRes()).child("requests").child(student.getKeyStd() + "").setValue(null);

        firebaseDatabase.getReference().
                child("Student").child(student.getKeyStd()).child("myReq").child(restaurants.getKeyRes()).setValue(null);
    }

    public void showAllStudents(Restaurants restaurants) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("restaurants").child(restaurants.getKeyRes()).child("requests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               Student s=  snapshot.getValue(Student.class);
               if (s!=null)
                studentArrayList.add(s.getNameStd());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
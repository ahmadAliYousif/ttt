package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.interfaces.RoomInterface;
import com.example.myapplication.models.Restaurants;
import com.example.myapplication.models.Student;
import com.example.myapplication.roomDataBase.DatabaseClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText et_email, et_password, et_country;
    Button btn_register_res, btn_register_std, btn_login_res, btn_login_std;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defer();
        mAuth = FirebaseAuth.getInstance();
        btn_register_res.setOnClickListener(this::onClick);
        btn_register_std.setOnClickListener(this::onClick);
        btn_login_res.setOnClickListener(this::onClick);

        btn_login_std.setOnClickListener(this::onClick);


    }

    private void defer() {
        et_country = findViewById(R.id.et_contry);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login_res = findViewById(R.id.btn_log_res);
        btn_login_std = findViewById(R.id.btn_log_std);
        btn_register_res = findViewById(R.id.btn_reg_Rec);
        btn_register_std = findViewById(R.id.btn_reg_Std);

    }


    public void signUpStudent(String email, String password) {
        Semaphore semaphores = new Semaphore(0);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        Student student = new Student();
                        student.setCountryStd(et_country.getText().toString());
                        student.setEmailStd(email);
                        updatename(currentUser, et_country.getText().toString());
                        student.setPasswordStd(password);
                        student.setNameStd(email);
                        student.setKeyStd(currentUser.getUid());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().insertStd(student);
                                semaphores.release();
                            }
                        }).start();
                        try {
                            semaphores.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        createNewStudent(student);
                        Intent intent = new Intent(getBaseContext(), StudentActivity.class);
                        intent.putExtra("std", student);
                        startActivity(intent);
                    }
                });
    }

    private void updatename(FirebaseUser user, String key) {// space = 32
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(key).build();
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

    }

    public void loginStudent(String email, String password) {
        Semaphore semaphores = new Semaphore(0);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Student student = new Student();
                student.setCountryStd(currentUser.getDisplayName());
                student.setEmailStd(email);
                student.setPasswordStd(password);
                student.setKeyStd(currentUser.getUid());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().insertStd(student);

                        }catch (Exception e){}
                        semaphores.release();

                    }
                }).start();
                try {
                    semaphores.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                createNewStudent(student);
                Intent intent = new Intent(getBaseContext(), StudentActivity.class);
                intent.putExtra("std", student);
                startActivity(intent);
            }
        });

    }

    public void signUpSRes(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        Restaurants restaurants = new Restaurants();
                        restaurants.setCountryRes(et_country.getText().toString());
                        restaurants.setEmailRes(email);
                        restaurants.setPasswordRes(password);
                        restaurants.setNameRes(email);
                        restaurants.setKeyRes(currentUser.getUid());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().insertRes(restaurants);

                            }
                        }).start();
                        createNewRes(restaurants);
                        Intent intent = new Intent(getBaseContext(), ResActivity.class);
                        intent.putExtra("res", restaurants);
                        startActivity(intent);

                    }
                });
    }

    public void loginRes(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                Restaurants restaurants = new Restaurants();
                currentUser.getUid();
                restaurants.setCountryRes(currentUser.getDisplayName());
                restaurants.setEmailRes(email);
                restaurants.setPasswordRes(password);
                restaurants.setNameRes(email);
                restaurants.setKeyRes(currentUser.getUid());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseClient.getInstance(getBaseContext()).getAppDatabase().roomInterface().insertRes(restaurants);

                    }
                }).start();
                createNewRes(restaurants);
                Intent intent = new Intent(getBaseContext(), ResActivity.class);
                intent.putExtra("res", restaurants);
                startActivity(intent);
            }
        });

    }


    public void createNewStudent(Student student) {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Student").child(student.getKeyStd()).setValue(student);
    }

    public void createNewRes(Restaurants restaurants) {


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("restaurants").child(restaurants.getKeyRes()).setValue(restaurants);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_log_res:
                loginRes(et_email.getText().toString(), et_password.getText().toString());

                break;
            case R.id.btn_log_std:
                loginStudent(et_email.getText().toString(), et_password.getText().toString());
                break;
            case R.id.btn_reg_Rec:
                signUpSRes(et_email.getText().toString(), et_password.getText().toString());

                break;
            case R.id.btn_reg_Std:
                signUpStudent(et_email.getText().toString(), et_password.getText().toString());

                break;


        }
    }


}
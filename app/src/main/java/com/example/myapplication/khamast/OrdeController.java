package com.example.myapplication.khamast;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdeController {

    public void getAllUsers(final OrdeCallback callback) {

        //her can show users for shoce to chat
        //final Query query=ref.orderByChild("email").equalTo("naif15@gmail.com");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final Query query = ref.orderByChild("email").equalTo("ahmed@ahmed.com");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Orde orde = null;
                ArrayList<Orde> ordes = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    orde = snapshot.getValue(Orde.class);
                    ordes.add(orde);
                }
                callback.Onsucess(ordes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}

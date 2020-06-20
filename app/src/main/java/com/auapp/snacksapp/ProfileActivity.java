package com.auapp.snacksapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String usid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        usid = firebaseAuth.getCurrentUser().getUid();
        t1 = findViewById(R.id.profilename);
        t2 = findViewById(R.id.profileemali);
        t3 = findViewById(R.id.profiledegi);
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(usid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String s1 = dataSnapshot.child("name").getValue().toString();
                    String s2 = dataSnapshot.child("email").getValue().toString();
                    String s3 = dataSnapshot.child("prof").getValue().toString();
                    t1.setText(s1);
                    t2.setText(s2);
                    t3.setText(s3);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
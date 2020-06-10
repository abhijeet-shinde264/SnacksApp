package com.auapp.snacksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartingPage extends AppCompatActivity {

    Button vegbt,nonvegbt;
    FirebaseAuth mauth;
    String currentuserid;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);
        vegbt = findViewById(R.id.veg);
        nonvegbt = findViewById(R.id.nonveg);
        mauth=FirebaseAuth.getInstance();
        currentuserid=mauth.getCurrentUser().getUid();
        FirebaseUser user=mauth.getCurrentUser();
        email=user.getEmail();
        final String mail=mauth.getCurrentUser().getEmail();
        final String email="abhijeetshinde1999@gmail.com";
        vegbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mail.equals(email)){
                    startActivity(new Intent(StartingPage.this,FirstPage.class));
                }else{
                    Toast.makeText(StartingPage.this, "Sorry ur not a admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
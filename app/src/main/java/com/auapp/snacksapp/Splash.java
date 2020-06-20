package com.auapp.snacksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    ImageView im;
    TextView t1;
    Animation topAnim,bottomAnim;
    private FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        im = findViewById(R.id.imageView);
        t1 = findViewById(R.id.textView);
//Set animation to elements
        im.setAnimation(topAnim);
        t1.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i1 = new Intent(Splash.this,MainActivity.class);
                startActivity(i1);
                finish();
            }
        },3000);
    }
}
package com.auapp.snacksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowSnacks extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;

    private ProgressBar progressBar;

    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
   // FloatingActionButton fb;
    FirebaseAuth mauth;
    String currentuserid;
    String email1;
    TextView up,del,add;
    private FloatingActionButton fab_main, fab1_update, fab2_delete,fab3_add;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    Boolean isOpen = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_snacks);
        mauth = FirebaseAuth.getInstance();
        currentuserid = mauth.getCurrentUser().getUid();
        FirebaseUser user = mauth.getCurrentUser();
        email1 = user.getEmail();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progress_circular);
        //fb = findViewById(R.id.floatingActionButton);
        fab_main = findViewById(R.id.fab);
        fab1_update = findViewById(R.id.fab1_up);
        fab2_delete = findViewById(R.id.fab2_del);
        fab3_add = findViewById(R.id.fab3_addddd);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);
        up = findViewById(R.id.update);
        del = findViewById(R.id.delete);
        add = findViewById(R.id.addaa);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    up.setVisibility(View.INVISIBLE);
                    del.setVisibility(View.INVISIBLE);
                    add.setVisibility(View.INVISIBLE);
                    fab2_delete.setAnimation(fab_close);
                    fab1_update.setAnimation(fab_close);
                    fab3_add.setAnimation(fab_close);
                    fab_main.setAnimation(fab_anticlock);
                    fab2_delete.setClickable(false);
                    fab1_update.setClickable(false);
                    fab3_add.setClickable(false);
                    isOpen = false;
                }else {
                    up.setVisibility(View.VISIBLE);
                    del.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);
                    fab2_delete.startAnimation(fab_open);
                    fab1_update.startAnimation(fab_open);
                    fab3_add.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab2_delete.setClickable(true);
                    fab1_update.setClickable(true);
                    fab3_add.setClickable(true);
                    isOpen = true;
                }
            }
        });
        fab2_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDialogDelete();
                //Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Page Under Development", Toast.LENGTH_SHORT).show();
            }
        });

        fab1_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Page Under Development", Toast.LENGTH_SHORT).show();

            }
        });
        fab3_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Show Snacks");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(ShowSnacks.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ShowSnacks.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
//                final String mail = mauth.getCurrentUser().getEmail();
//                final String email="abhijeetshinde1999@gmail.com";
//                if (email1.equals("abhijeetshinde1999@gmail.com")){
//                    fb.setVisibility(View.VISIBLE);
//                    fb.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(ShowSnacks.this,FirstPage.class));
//                        }
//                    });
//                }
//        fb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialog();
//            }
//        });
    }

    private void openDialogDelete() {
        View view = LayoutInflater.from(ShowSnacks.this).inflate(R.layout.dialog_show,null);
        final EditText email = view.findViewById(R.id.email111);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowSnacks.this);
        builder.setMessage("Please enter your email")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String em = email.getText().toString().trim();
                        if (em.equals("abhijeetshinde1999@gmail.com")){
                            startActivity(new Intent(ShowSnacks.this,DeletePage.class));
                        }else{
                            Toast.makeText(ShowSnacks.this, "Sorry your not admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(ShowSnacks.this, "Sorry your not admin", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openDialog() {
//        ExampleDialog dialog = new ExampleDialog();
//        dialog.show(getSupportFragmentManager(),"Enter email id.");

        View view = LayoutInflater.from(ShowSnacks.this).inflate(R.layout.dialog_show,null);
        final EditText email = view.findViewById(R.id.email111);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowSnacks.this);
        builder.setMessage("Please enter your email")
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String em = email.getText().toString().trim();
                        if (em.equals("abhijeetshinde1999@gmail.com")){
                            startActivity(new Intent(ShowSnacks.this,FirstPage.class));
                        }else{
                            Toast.makeText(ShowSnacks.this, "Sorry your not admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(ShowSnacks.this, "Sorry your not admin", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
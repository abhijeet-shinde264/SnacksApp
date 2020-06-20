package com.auapp.snacksapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowBuyers extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<BuyerData> list = new ArrayList<>();
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_buyers);
        recyclerView = findViewById(R.id.rv1);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowBuyers.this));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Buyer Items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        BuyerData data = ds.getValue(BuyerData.class);
                        list.add(data);
                    }
                    adapter = new ShowBuyerAdapter(list,ShowBuyers.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Buyers");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
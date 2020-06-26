package com.auapp.snacksapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHistory extends AppCompatActivity {

    TextView t1,t2;
    String email,getemail,name,quan;
    DatabaseReference reference;
    FirebaseUser user;
    RecyclerView recyclerView;
    List<BuyerData> list = new ArrayList<>();
    RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
         user = FirebaseAuth.getInstance().getCurrentUser();
         recyclerView = findViewById(R.id.rvhistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserHistory.this));
//        t1 = findViewById(R.id.namehis1);
//        t2 = findViewById(R.id.quanthis1);
        if (user != null) {
//                    //String name = user.getDisplayName();
                    email = user.getEmail();
//                    //String uid = user.getUid(); // The user's ID, unique to the Firebase project.
//                    //t1.setText(email);
//                    BuyerData buyerData = new BuyerData();
//                    getemail = buyerData.getEmail();
//                    if (email.equals(getemail)) {
//                        name = buyerData.getIte();
//                        quan = buyerData.getQuan();
//                        t1.setText(name);
//                        t2.setText(quan);
                    }
//                }
        reference = FirebaseDatabase.getInstance().getReference().child("Buyer Items");
        reference.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    BuyerData register1 = ds.getValue(BuyerData.class);
                    //BuyerData data1 = ds.getValue(BuyerData.class);
                    list.add(register1);
//                    name = ds.child("ite").getValue().toString();
//                    quan = ds.child("quan").getValue().toString();
//                    t1.setText(name);
//                    t2.setText(quan);
//                    Toast.makeText(UserHistory.this, "Email id is:"+email, Toast.LENGTH_SHORT).show();
                }
                adapter = new ShowHistoryAdapter(list,UserHistory.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String getemail1 = dataSnapshot.child("email").getValue(String.class);
//                Toast.makeText(UserHistory.this, "Email is:"+getemail1, Toast.LENGTH_SHORT).show();
//                if (email.equals(getemail1)){
//                    name =dataSnapshot.child("ite").getValue(String.class);
//                    quan = dataSnapshot.child("quan").getValue(String.class);
//                    t1.setText(name);
//                    t2.setText(quan);
//                }
////                if (email.equals(getemail)){
////                    for (DataSnapshot ds : dataSnapshot.getChildren()){
////                        BuyerData data1 = ds.getValue(BuyerData.class);
////                        list.add(data1);
////                    }
////                    adapter = new ShowBuyerAdapter(list,UserHistory.this);
////                    recyclerView.setAdapter(adapter);
////                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Users History");
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
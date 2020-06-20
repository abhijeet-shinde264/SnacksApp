package com.auapp.snacksapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BuyItems extends AppCompatActivity {

    TextView t1,t2;
    EditText e1,e2;
    ImageView i11;
    Button b1;
    String name,img,postkey,quan;
    DatabaseReference reference,ref2;
    BuyerData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_items);
        t1 = findViewById(R.id.buyitem);
        t2 = findViewById(R.id.buyquant);
        i11 = findViewById(R.id.buyimage);
        b1 = findViewById(R.id.buybutton);
        e1 = findViewById(R.id.numquantity);
        e2 = findViewById(R.id.buyname);
        data = new BuyerData();
        //Intent i1 = getIntent();
        postkey = getIntent().getStringExtra("key");
        reference = FirebaseDatabase.getInstance().getReference().child("Items").child(postkey);
        ref2 = FirebaseDatabase.getInstance().getReference().child("Buyer Items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 name = dataSnapshot.child("name").getValue().toString();
                 img = dataSnapshot.child("imgUrl").getValue().toString();
                 quan = dataSnapshot.child("quantity").getValue().toString();
                 t1.setText(name);
                t2.setText(quan);
                Picasso.get()
                        .load(img)
                        .fit()
                        .centerCrop()
                        .into(i11);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = t2.getText().toString();
                String s2 = e1.getText().toString();
                String s3 = e2.getText().toString();
                if ((s3.isEmpty()) || (s2.isEmpty())){
                    Toast.makeText(BuyItems.this, "Please enter the fields...", Toast.LENGTH_SHORT).show();
                }else{
                    data.setNa(e2.getText().toString().trim());
                    data.setQuan(e1.getText().toString().trim());
                    data.setIte(name.trim());
                    ref2.push().setValue(data);
                    Toast.makeText(BuyItems.this, "Thanks for buying", Toast.LENGTH_SHORT).show();
                    Intent i1 = new Intent(BuyItems.this,ShowSnacks.class);
                    startActivity(i1);
                    finish();
                }
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Buy Snacks");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//        image = i1.getStringExtra("image");
//        name = i1.getStringExtra("name");
//        quantaty = i1.getIntExtra("abhiquantity",0);
//        Picasso.get()
//                .load(image)
//                .fit()
//                .centerCrop()
//                .into(i11);
//        t1.setText(name);
//        t2.setText("Quantity:"+quantaty);
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
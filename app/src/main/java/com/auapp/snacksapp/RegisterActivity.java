package com.auapp.snacksapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText ename,eemai,eprof,epass,ecpass;
    Button bregi;
    Register1 register1;
    FirebaseAuth firebaseAuth;
    String s1,s2,s3;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ename = findViewById(R.id.signame);
        eemai = findViewById(R.id.sigemail);
        eprof = findViewById(R.id.sigdeg);
        epass = findViewById(R.id.sigpass);
        ecpass = findViewById(R.id.conpass);
        bregi = findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();
        register1 = new Register1();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        bregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = eemai.getText().toString().trim();
                s2 = epass.getText().toString().trim();
                s3 = ecpass.getText().toString().trim();
                if ((ename.getText().toString().isEmpty()) || (eemai.getText().toString().isEmpty()) || (eprof.getText().toString().isEmpty())
                || (epass.getText().toString().isEmpty()) || (ecpass.getText().toString().isEmpty())){
                    Toast.makeText(RegisterActivity.this, "Please fill the details", Toast.LENGTH_SHORT).show();
                }else if ((s2).length()<6){
                    epass.setError("Password must be of 6 characters");
                }else if ((!s2.equals(s3))){
                    Toast.makeText(RegisterActivity.this, "Please check the password.", Toast.LENGTH_SHORT).show();
                }else if(s2.equals(s3)) {
                    register(s1,s2);
                }
            }
        });
    }
    public void register(String s1,String s2){
        if (TextUtils.isEmpty(s1)){
            Toast.makeText(getApplicationContext(),"enter username",Toast.LENGTH_LONG).show();;
        }
        else if(TextUtils.isEmpty(s2)){
            Toast.makeText(getApplicationContext(),"password",Toast.LENGTH_LONG).show();;
        }else{
            firebaseAuth.createUserWithEmailAndPassword(s1,s2)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                register1.setName(ename.getText().toString().trim());
                                register1.setEmail(eemai.getText().toString().trim());
                                register1.setProf(eprof.getText().toString().trim());
                                databaseReference.push().setValue(register1);
                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                Intent i1 = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(i1);
                                finish();
                            }
                        }
                    });
        }
    }
}

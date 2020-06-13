package com.auapp.snacksapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class FirstPage extends AppCompatActivity {
    private ImageView img;
    private EditText itname;
    Button add;
    TextView quan;
    private Bitmap compressedImageFile=null;
    private Uri imgUrl = null;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    int minteger = 0;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;

    private static final int CHOOSE_IMAGE = 1;
    private ProgressBar uploadProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        img = findViewById(R.id.addimage);
        itname = findViewById(R.id.item_name);
        quan = findViewById(R.id.integer_number);
        add = findViewById(R.id.additem);
        uploadProgress = findViewById(R.id.uploadProgress);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Items");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(FirstPage.this);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                    Toast.makeText(FirstPage.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    uploadImage();
                }
            }
        });
    }public void increaseInteger(View view) {
        minteger = minteger + 1;
        display(minteger);
        if (minteger>10){
            Toast.makeText(this, "Sorry u cant increment more", Toast.LENGTH_SHORT).show();
            display(0);
        }
    }public void decreaseInteger(View view) {
        minteger = minteger - 1;
        display(minteger);
        if (minteger<0){
            Toast.makeText(this, "Sorry u cant decrement more", Toast.LENGTH_SHORT).show();
            display(0);
        }
    }
    private void display(int number) {
        quan.setText("" + number);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imgUrl = result.getUri();
                Picasso.with(this).load(imgUrl).networkPolicy(NetworkPolicy.OFFLINE).into(img);
                File crop_path=new File(imgUrl.getPath());
                try {
                    compressedImageFile=new Compressor(this)
                            .setMaxHeight(300)
                            .setMaxWidth(300).setQuality(100).compressToBitmap(crop_path);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        if (imgUrl != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imgUrl));

            mUploadTask = fileReference.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    uploadProgress.setProgress(0);
                                }
                            }, 500);
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(itname.getText().toString().trim(),quan.getText().toString().trim(),uri.toString());
                                    String uploadID = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadID).setValue(upload);
                                    Toast.makeText(FirstPage.this, "Upload successfully", Toast.LENGTH_LONG).show();
                                    img.setImageResource(R.drawable.imagepreview);
                                    itname.setText("");
                                    quan.setText("");
                                    Intent i1 = new Intent(FirstPage.this,ShowSnacks.class);
                                    startActivity(i1);
                                }
                            });


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FirstPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            uploadProgress.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(FirstPage.this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
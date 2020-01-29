package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class AddResultActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    private ProgressDialog loadingBar;
    String currentUserID;
    private StorageReference UserProfileImageRef;
    final static int Gallery_Pick =1;



    EditText sems,year,batch;
    Button save;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

        loadingBar= new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Result");
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Results");


        imageView =(ImageView) findViewById(R.id.imageView);
        sems =(EditText) findViewById(R.id.editText);
        year =(EditText) findViewById(R.id.editText2);
        batch=(EditText) findViewById(R.id.editText3);

        save =(Button) findViewById(R.id.button3);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveResult();
                finish();
            }
        });

        ImageView imagei =(ImageView) findViewById(R.id.back);
        imagei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                // startActivityForResult(galleryIntent, Gallery_Pick);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"),Gallery_Pick);
            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("result_image"))
                    {
                        String image = dataSnapshot.child("result_image").getValue().toString();
                        Picasso.get().load(image).into(imageView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            Uri ImageUri = data.getData();

            CropImage.activity(ImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Result");
                loadingBar.setMessage("Please wait, while we updating your Result ...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();

                final StorageReference filePath = UserProfileImageRef.child(currentUserID + ".jpg");

                filePath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downUri = task.getResult();
                            Toast.makeText(AddResultActivity.this, "Result Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = downUri.toString();
                            UsersRef.child("result_image").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent selfIntent = new Intent(AddResultActivity.this, AddResultActivity.class);
                                                startActivity(selfIntent);

                                                Toast.makeText(AddResultActivity.this, "Result Image stored to Firebase Database Successfully...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            } else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(AddResultActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });
                        }

                    }

                });

            }
        }
    }


    private void SaveResult() {

        String Semester = sems.getText().toString();
        String Year = year.getText().toString();
        String Batch = batch.getText().toString();

        loadingBar.setTitle("Saving Information");
        loadingBar.setMessage("Please Wait, while we are your new account...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        HashMap userMap = new HashMap();
        userMap.put("Semester", Semester);
        userMap.put("Year", Year);
        userMap.put("Batch", Batch);

        UsersRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    //SendUserToMainActivity();
                    Toast.makeText(AddResultActivity.this, "your account is created succesfully", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(AddResultActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
        });

    }


}

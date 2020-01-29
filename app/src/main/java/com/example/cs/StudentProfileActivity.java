package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

public class StudentProfileActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    private EditText rollno, name, surname, dep, batch, semes, year;
    private ImageView UserProfile;
    private StorageReference UserProfileImageRef;
    final static int Gallery_Pick = 1;
    private ProgressDialog loadingBar;
    private CardView uptodate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        UserProfile = (ImageView) findViewById(R.id.nav_profile_image);
        rollno = findViewById(R.id.Rollno);
        name = findViewById(R.id.Name);
        surname = findViewById(R.id.Surname);
        dep = findViewById(R.id.Department);
        batch = findViewById(R.id.Batch);
        semes = findViewById(R.id.Semester);
        year = findViewById(R.id.Year);

        ImageView card =(ImageView) findViewById(R.id.back);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uptodate = findViewById(R.id.update);
        uptodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("profileimage")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(UserProfile);
                    }

                    if (dataSnapshot.hasChild("rollno")) {
                        String Rollno = dataSnapshot.child("rollno").getValue().toString();

                        rollno.setText(Rollno);
                    }
                    if (dataSnapshot.hasChild("fullname")) {
                        String fullname = dataSnapshot.child("fullname").getValue().toString();

                        name.setText(fullname);
                    }
                    if (dataSnapshot.hasChild("surname")) {
                        String Surname = dataSnapshot.child("surname").getValue().toString();

                        surname.setText(Surname);
                    }
                    if (dataSnapshot.hasChild("department")) {
                        String department = dataSnapshot.child("department").getValue().toString();

                        dep.setText(department);
                    }
                    if (dataSnapshot.hasChild("batch")) {
                        String Batch = dataSnapshot.child("batch").getValue().toString();

                        batch.setText(Batch);
                    }
                    if (dataSnapshot.hasChild("semester")) {
                        String Semester = dataSnapshot.child("semester").getValue().toString();

                        semes.setText(Semester);
                    }
                    if (dataSnapshot.hasChild("year")) {
                        String Year = dataSnapshot.child("year").getValue().toString();

                        year.setText(Year);
                    } else {
                        //Toast.makeText(ProfileActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                // startActivityForResult(galleryIntent, Gallery_Pick);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), Gallery_Pick);
            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("profileimage")) {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(UserProfile);
                    } else {
                        Toast.makeText(StudentProfileActivity.this, "Please select profile image first.", Toast.LENGTH_SHORT).show();
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
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                loadingBar.setTitle("Profile Image");
                loadingBar.setMessage("Please wait, while we updating your profile image...");
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
                            Toast.makeText(StudentProfileActivity.this, "Profile Image stored successfully to Firebase storage...", Toast.LENGTH_SHORT).show();
                            final String downloadUrl = downUri.toString();
                            UsersRef.child("profileimage").setValue(downloadUrl)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Intent selfIntent = new Intent(StudentProfileActivity.this, SetupActivity.class);
                                                startActivity(selfIntent);

                                                Toast.makeText(StudentProfileActivity.this, "Profile Image stored to Firebase Database Successfully...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            } else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(StudentProfileActivity.this, "Error Occured: " + message, Toast.LENGTH_SHORT).show();
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

    private void UpdateProfile() {

        String username = rollno.getText().toString();
        String full_name = name.getText().toString();
        String Surname = surname.getText().toString();
        String department = dep.getText().toString();
        String Batch = batch.getText().toString();
        String semester = semes.getText().toString();
        String Year = year.getText().toString();


        loadingBar.setTitle("Saving Information");
        loadingBar.setMessage("Please Wait, while we are your new account...");
        loadingBar.show();
        loadingBar.setCanceledOnTouchOutside(true);

        HashMap userMap = new HashMap();
        userMap.put("rollno", username);
        userMap.put("fullname", full_name);
        userMap.put("surname", Surname);
        userMap.put("department", department);
        userMap.put("batch", Batch);
        userMap.put("semester", semester);
        userMap.put("year", Year);


        UsersRef.child(currentUserID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    Toast.makeText(StudentProfileActivity.this, "your account is created succesfully", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(StudentProfileActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
        });
    }


    public void CancelPro(View view) {
        CardView cardView = findViewById(R.id.cancelpro);
        finish();
    }


}
package com.example.cs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CardView updateData;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    private EditText rollno,name,surname,dep,batch,semes,year;
    private ImageView UserProfile;


    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile);
        ImageView imageView =(ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");




        UserProfile = (ImageView) findViewById(R.id.nav_profile_image);
        rollno= findViewById(R.id.Rollno);
        name= findViewById(R.id.Name);
        surname= findViewById(R.id.Surname);
        dep= findViewById(R.id.Department );
        batch= findViewById(R.id.Batch);
      semes= findViewById(R.id.Semester);
       year= findViewById(R.id.Year);

       updateData =findViewById(R.id.update);
       updateData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(ProfileActivity.this,UpdateProfileActivity.class));
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
                       String Semester= dataSnapshot.child("semester").getValue().toString();

                      semes.setText(Semester);
                   }
                   if (dataSnapshot.hasChild("year")) {
                       String Year = dataSnapshot.child("year").getValue().toString();

                       year.setText(Year);
                   }
                  else {
                       //Toast.makeText(ProfileActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                   }

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });







    }


  /*  private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(ProfileActivity.this, ProfileActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
*/
    public void CancelPro(View view){
        CardView cardView =findViewById(R.id.cancelpro);
        finish();
    }
}

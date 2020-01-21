package com.example.cs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    private TextView rollno,name,surname,dep,batch,semes,year;
    private CircleImageView NavProfileImage;

    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_profile);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_profile,null);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        NavProfileImage = (CircleImageView) v.findViewById(R.id.nav_profile_image);
        rollno= v.findViewById(R.id.Rollno);
        name= v.findViewById(R.id.Name);
        surname= v.findViewById(R.id.Surname);
        dep= v.findViewById(R.id.Department );
        batch= v.findViewById(R.id.Batch);
      semes= v.findViewById(R.id.Semester);
       year= v.findViewById(R.id.Year);

       UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if (dataSnapshot.exists()) {
                   if (dataSnapshot.hasChild("profileimage")) {
                       //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                       String image = dataSnapshot.child("profileimage").getValue().toString();
                       Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);
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


        return v;
    }
}

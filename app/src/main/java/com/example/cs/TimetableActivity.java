package com.example.cs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TimetableActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference Timetable1,Timetable2,Timetable3,Timetable4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        ImageView imageView =(ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ImageView imageView1 =(ImageView) findViewById(R.id.image1);
        final ImageView imageView2 =(ImageView) findViewById(R.id.image2);
        final ImageView imageView3 =(ImageView) findViewById(R.id.image3);
        final ImageView imageView4 =(ImageView) findViewById(R.id.image4);

        mAuth = FirebaseAuth.getInstance();

        Timetable4 = FirebaseDatabase.getInstance().getReference().child("Batch17");
        Timetable3 = FirebaseDatabase.getInstance().getReference().child("Batch18");
        Timetable2 = FirebaseDatabase.getInstance().getReference().child("Batchf18");
        Timetable1 = FirebaseDatabase.getInstance().getReference().child("Batch19");

        Timetable1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("batch19")) {

                        String image = dataSnapshot.child("batch19").getValue().toString();
                        Picasso.get().load(image).into(imageView1);

                        //   Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.profile_image).into(NavProfileImage);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Timetable2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("batchf18")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("batchf18").getValue().toString();
                        Picasso.get().load(image).into(imageView2);

                        //   Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.profile_image).into(NavProfileImage);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Timetable3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("batch18")) {

                        String image = dataSnapshot.child("batch18").getValue().toString();
                        Picasso.get().load(image).into(imageView3);



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Timetable4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("batch17")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("batch17").getValue().toString();
                        Picasso.get().load(image).into(imageView4);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

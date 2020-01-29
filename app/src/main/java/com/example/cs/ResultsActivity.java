package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ResultsActivity extends AppCompatActivity {


    ImageView imageView;
    TextView sems,year,batch;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mAuth = FirebaseAuth.getInstance();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Result");

imageView=findViewById(R.id.result);
sems=findViewById(R.id.semesterid);
year=findViewById(R.id.yearid);
batch=findViewById(R.id.batchid);

        ImageView card =(ImageView) findViewById(R.id.back);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("result_image")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("result_image").getValue().toString();
                        Picasso.get().load(image).into(imageView);
                    }

                    if (dataSnapshot.hasChild("Semester")) {
                        String Rollno = dataSnapshot.child("Semester").getValue().toString();

                        sems.setText(Rollno);
                    }
                    if (dataSnapshot.hasChild("Year")) {
                        String Year = dataSnapshot.child("Year").getValue().toString();

                        year.setText(Year);
                    }
                    if (dataSnapshot.hasChild("Batch")) {
                        String Batch= dataSnapshot.child("Batch").getValue().toString();

                        batch.setText(Batch);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

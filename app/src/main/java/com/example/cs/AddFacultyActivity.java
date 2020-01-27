package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Map;

public class AddFacultyActivity extends AppCompatActivity {

    private EditText TeacherName,scale,emal,education;
    private Button button;
    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;


    private DatabaseReference UsersRef, FacultyRef;
    private FirebaseAuth mAuth;

    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        mAuth = FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

       // PostsImagesRefrence = FirebaseStorage.getInstance().getReference();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        FacultyRef = FirebaseDatabase.getInstance().getReference().child("Faculty");

        TeacherName =findViewById(R.id.teacher);
        scale=findViewById(R.id.scale);
        emal =findViewById(R.id.email);
        education=findViewById(R.id.education);
        button =findViewById(R.id.btn);





        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFacultyDatabase();
            }
        });
    }



    private void AddFacultyDatabase()
    {
       final String Teacher_Name=TeacherName.getText().toString();
       final String Teacher_Scale=scale.getText().toString();
       final String Teacher_Email=emal.getText().toString();
       final String Teacher_Education=education.getText().toString();

        UsersRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                   // String userFullName = dataSnapshot.child("fullname").getValue().toString();
                  //  String userProfileImage = dataSnapshot.child("profileimage").getValue().toString();

                    HashMap facultyMap = new HashMap();
                    facultyMap.put("uid", current_user_id);
                    facultyMap.put("teacher_name", Teacher_Name);
                    facultyMap.put("teacher_status", Teacher_Scale);
                    facultyMap.put("teacher_email", Teacher_Email);
                    facultyMap.put("teacher_education", Teacher_Education);
                    FacultyRef.child(current_user_id).updateChildren(facultyMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if(task.isSuccessful())
                                    {
                                       sendUserToMainActivity();
                                        Toast.makeText(AddFacultyActivity.this, "New Post is updated successfully.", Toast.LENGTH_SHORT).show();
                                       // loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddFacultyActivity.this, "Error Occured while updating your post.", Toast.LENGTH_SHORT).show();
                                       // loadingBar.dismiss();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendUserToMainActivity() {

        Intent mainIntent = new Intent(AddFacultyActivity.this,FacultyActivity.class);
        startActivity(mainIntent);
    }

}

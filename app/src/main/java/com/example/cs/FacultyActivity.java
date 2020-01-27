package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FacultyActivity extends AppCompatActivity {

     RecyclerView recyclerView;
     RecyclerView.LayoutManager layoutManager;

     FacultyAdapter facultyAdapter;

     ArrayList<Faculty> list;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

       ImageView imageView =(ImageView) findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linearList();


       RecyclerView recyclerView = findViewById(R.id.faculty_recycler);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setHasFixedSize(true);

       FacultyAdapter facultyAdapter =new FacultyAdapter(this,list);

       recyclerView.setAdapter(facultyAdapter);

    }


    private void linearList(){

        list =new ArrayList<Faculty>();

        list.add(new Faculty(R.drawable.saleem,"Dr. Muhammad Saleem Vighio","Associate Professor / Chairman","saleem.vighio@quest.edu.pk","PhD (Denmark)"));
        list.add(new Faculty(R.drawable.dean,"Prof. Dr. Zahid Hussain Abro","Professor / Dean Faculty of Science","zhussain@quest.edu.pk","Ph.D (Austria)"));
        list.add(new Faculty(R.drawable.adnan," Dr. Adnan Manzoor Rajper","Professor","adnan@quest.edu.pk","MS Information Technology (QUEST)"));
        list.add(new Faculty(R.drawable.fareed," Dr. Fareed Ahmed Jokhio","Associate Professor","fajokhio@quest.edu.pk","PhD Computer Engine"));
        list.add(new Faculty(R.drawable.ubaid," Dr. Ubaidullah Rajput","Associate Professor","ubaidullah@quest.edu.pk","PhD (Hanyang University, South Korea)"));
        list.add(new Faculty(R.drawable.halepoto,"Dr. Imtiaz Ali Halepoto","Associate Professor","halepoto@quest.edu.pk","PhD (HKU, Hong Kong)"));
        list.add(new Faculty(R.drawable.fayaz," Engr. Fayyaz Ahmed Memon","Assistant Professor","engr_fayaz@hotmail.com","M.E.(CSN ) Mehran University"));
        list.add(new Faculty(R.drawable.amir,"Engr. Muhammad Aamir Bhutto","Assistant Professor","aamirbhutto@quest.edu.pk","M.E (Computer Systems)"));
        list.add(new Faculty(R.drawable.bakir,"Mr. Baqar Ali Zardari ","Assistant Professor","alizardari34@gmail.com","MS Information Technology (QUEST)"));
        list.add(new Faculty(R.drawable.aijaz,"Dr. Aijaz Ahmed Arain ","Assistant Professor","aijaz@quest.edu.pk","Ph.D. Information Technology"));



    }

}



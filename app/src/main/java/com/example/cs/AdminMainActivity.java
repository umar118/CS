package com.example.cs;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;


        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;


public class AdminMainActivity extends AppCompatActivity {

     CardView chairman,faculty,time_table,other_activity,exit,results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        chairman =findViewById(R.id.add_chairman);
        faculty=findViewById(R.id.add_faculty);
        time_table=findViewById(R.id.add_timetable);
        results=findViewById(R.id.add_results);
        other_activity=findViewById(R.id.add_other);
        exit=findViewById(R.id.exit_card);

        chairman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,ChairmanActivity.class));
            }
        });
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,FacultyActivity.class));
            }
        });
        time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddTimeTableActivity.class));
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this,AddResultActivity.class));
            }
        });
        other_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

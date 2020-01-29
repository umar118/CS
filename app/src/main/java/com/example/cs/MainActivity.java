package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   private Toolbar toolbar;
   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef;
    String currentUserID;
    private CardView faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
       // UsersRef = FirebaseDatabase.getInstance().getReference("Users");
        //UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        toolbar =findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawerLayout =findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView =findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
       // View navView =navigationView.getHeaderView(0);
        NavProfileImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_image);

        NavProfileUserName =  (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);

        faculty =findViewById(R.id.facultycard);
        faculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FacultyActivity.class));
            }
        });


        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("profileimage")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);

                     //   Picasso.with(MainActivity.this).load(image).placeholder(R.drawable.profile_image).into(NavProfileImage);

                    }
                    if (dataSnapshot.hasChild("fullname")) {
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        //  Toast.makeText(MainActivity.this, "Name: "+fullname, Toast.LENGTH_LONG).show();
                        NavProfileUserName.setText(fullname);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            senduserTologinActivity();
        }
    }

    private void senduserTologinActivity() {

        Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }




   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                startActivity(new Intent(MainActivity.this,StudentProfileActivity.class));

               break;
            case  R.id.nav_chairman:

                startActivity(new Intent(MainActivity.this,ChairmanActivity.class));
             break;

            case R.id.nav_faculty:
              //  Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,FacultyActivity.class));
                break;
            case R.id.nav_table:
                startActivity(new Intent(MainActivity.this,TimetableActivity.class));
                break;
            case R.id.nav_result:
               startActivity(new Intent(MainActivity.this,ResultsActivity.class));

                break;
            case R.id.nav_logout:
                mAuth.signOut();
                senduserTologinActivity();
                break;
            case R.id.nav_delete_account:

              final   FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                AuthCredential authCredential = EmailAuthProvider.getCredential("user@example.com", "password1234");
                FragmentManager manager =getSupportFragmentManager();
                DeleteAccountDailogFragemt myDialogFragment =new DeleteAccountDailogFragemt();
                myDialogFragment.show(manager,"DeleteAccountDialogFragment");
                firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   // Log.d(, "User account deleted!");
                                    Toast.makeText(MainActivity.this, "User account deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                break;
            //case R.id.nav_exit:
            //    Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            //    finish();
            //    break;

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    public void Profile_Card(View view){
       CardView pro =(CardView) findViewById(R.id.profile_card);
        startActivity(new Intent(MainActivity.this,StudentProfileActivity.class));
    }
    public void Chairman_Card(View view){
        CardView ch =findViewById(R.id.chairman_card);
       startActivity(new Intent(MainActivity.this,ChairmanActivity.class));
    }
    public void Exit_Card(View view){
       CardView exit =findViewById(R.id.exit_card);
       FragmentManager manager =getSupportFragmentManager();
       MyDialogFragment myDialogFragment =new MyDialogFragment();
       myDialogFragment.show(manager,"MyDialogFragment");
    }
    public void Result(View view){

        CardView re= findViewById(R.id.results);
        startActivity(new Intent(MainActivity.this,ResultsActivity.class));

    }
    public void TimeTable (View view){

        CardView ti=findViewById(R.id.timetable);
        startActivity(new Intent(MainActivity.this,TimetableActivity.class));

    }


}

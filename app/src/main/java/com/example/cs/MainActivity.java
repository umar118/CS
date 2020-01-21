package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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

    private Query query;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef,HomeRef;
    String currentUserID;
    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar =findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView =findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        UsersRef = FirebaseDatabase.getInstance().getReference("Users");

        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);

        NavProfileImage = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView) navView.findViewById(R.id.username);


        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("profileimage")) {
                        //String image = "https://firebasestorage.googleapis.com/v0/b/poster-44926.appspot.com/o/Profile%20Images%2FjIR4L7pSWphSsBlBT8xu52FXI6L2.jpg?alt=media&token=86f09465-0562-4a00-ae15-1b5d9d94727b";
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);
                    }
                    if (dataSnapshot.hasChild("fullname")) {
                        String fullname = dataSnapshot.child("fullname").getValue().toString();
                        Toast.makeText(MainActivity.this, "Name: "+fullname, Toast.LENGTH_LONG).show();
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
        FragmentManager manager =getSupportFragmentManager();
        FragmentTransaction transaction =manager.beginTransaction();
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                ProfileActivity profileActivity =new ProfileActivity();
                transaction.replace(R.id.main_container,profileActivity);
                transaction.commit();
            case  R.id.nav_chairman:
               return true;

            case R.id.nav_faculty:
              //  Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_table:
               // Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_result:
               // Toast.makeText(this,"",Toast.LENGTH_SHORT).show();

                break;
            case R.id.nav_logout:
                mAuth.signOut();
                senduserTologinActivity();
                break;
            //case R.id.nav_exit:
            //    Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            //    finish();
            //    break;

        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

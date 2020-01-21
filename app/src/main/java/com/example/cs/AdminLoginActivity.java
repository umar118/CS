package com.example.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        email =findViewById(R.id.adminemail);
        password =findViewById(R.id.adminpassword);
    }
    public void AdminLogin(View view){
        String Admin_Email =email.getText().toString();
        String Admin_Password =password.getText().toString();


        if (Admin_Email.equals("admin@gmail.com") && Admin_Password.equals("admin#1234") )
        {

            startActivity(new Intent(AdminLoginActivity.this,AdminMainActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(),"Invalid Email and Password",Toast.LENGTH_SHORT).show();
        }
    }

    public void Exit(View view){
        Button exit =findViewById(R.id.button2);
        finish();
    }

}

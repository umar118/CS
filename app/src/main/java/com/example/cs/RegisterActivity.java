package com.example.cs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText useremail,password,confirm_pass;
    private FirebaseAuth mAuth;

    CardView signupbutton,exit;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();


        useremail =findViewById(R.id.emailText);
        password= findViewById(R.id.Passwordnumber);
        confirm_pass =findViewById(R.id.ConPasswordnumber);
        loadingBar= new ProgressDialog(this);

        signupbutton =findViewById(R.id.regis);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CreateNewAccount();


            }

            private void CreateNewAccount()
            {

                String email = useremail.getText().toString();
                String password_num = password.getText().toString();
                String confirmPassword= confirm_pass.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this, "Please write your email...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password_num))
                {
                    Toast.makeText(RegisterActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(confirmPassword))
                {
                    Toast.makeText(RegisterActivity.this, "Please confirm your email", Toast.LENGTH_SHORT).show();
                }
                else if (!password_num.equals(confirmPassword))
                {
                    Toast.makeText(RegisterActivity.this, "Your password does not match...",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingBar.setTitle("Creating New Account");
                    loadingBar.setMessage("Please Wait, while we are creating your Account...");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    mAuth.createUserWithEmailAndPassword(email,password_num).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                SendUserToSetupActivity();
                                Toast.makeText(RegisterActivity.this, "you are authenticated succesfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error occured: "+ message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                        }

                        private void SendUserToSetupActivity() {

                            Intent setupIntent = new Intent(RegisterActivity.this,SetupActivity.class);
                            setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(setupIntent);
                            finish();

                        }
                    });

                }


            }

        });
    }

    public void Exit(View view){
        exit =findViewById(R.id.exitbutton);
        finish();
    }



}


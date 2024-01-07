package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aduraapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, editPassword;
    private Button login;

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        email = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        login = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = editPassword.getText().toString();

                // Check if email and password match the admin credentials
                if (txt_email.equals("admin@gmail.com") && txt_password.equals("123456")) {
                    // If admin credentials match, start MainAdminActivity
                    Intent adminIntent = new Intent(LoginActivity.this, MainAdminActivity.class);
                    adminIntent.putExtra("IS_LOGGED_IN", true);
                    startActivity(adminIntent);
                    finish();
                } else {
                    // If not admin credentials, proceed with regular login
                    loginUser();

                }
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = editPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        mainIntent.putExtra("IS_LOGGED_IN", true);
                        startActivity(mainIntent);

                        // Finish the activity after a delay using a Handler
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000); // Adjust the delay as needed
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Login Gagal, Periksa kembali email dan password anda!", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void onButtonRegisterClicked(View view) {
        Log.d("LoginActivity", "onButtonRegisterClicked: Navigating to RegisterActivity");
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}
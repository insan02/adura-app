package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aduraapp.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    TextInputEditText email, editPassword, editconfirmPassword;

    private Button register;

    private FirebaseAuth auth;
    private ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        email = findViewById(R.id.email);
        editPassword = findViewById(R.id.editPassword);
        editconfirmPassword = findViewById(R.id.editconfirmPassword);
        register = findViewById(R.id.buttonRegister);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = editPassword.getText().toString();
                String txt_confirmPassword = editconfirmPassword.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "Empty Credential", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length()<6) {
                    Toast.makeText(RegisterActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                } else if (!txt_password.equals(txt_confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "password and confirm password not same", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txt_email, txt_password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        Log.d("RegisterActivity", "Attempting to register user...");

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register Successfull", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void onButtonLoginClicked(View view){
//        Intent loginIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginIntent);
//    }


//    public void onButtonRegisterClicked(View view) {
//        //explicit intent
//        String username = binding.editUsername.getText().toString();
//        String password = binding.editPassword.getText().toString();
//        String confirmpassword = binding.editconfirmPassword.getText().toString();
//
//
//        Intent loginIntent = new Intent(this, LoginActivity.class);
//        startActivity(loginIntent);
//
//    }


}

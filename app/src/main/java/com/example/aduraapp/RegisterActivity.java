package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aduraapp.databinding.ActivityLoginBinding;
import com.example.aduraapp.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editUsername, editPassword, editconfirmPassword;

    private ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        editconfirmPassword = findViewById(R.id.editconfirmPassword);

    }

    public void onButtonLoginClicked(View view){
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }


    public void onButtonRegisterClicked(View view) {
        //explicit intent
        String username = binding.editUsername.getText().toString();
        String password = binding.editPassword.getText().toString();
        String confirmpassword = binding.editconfirmPassword.getText().toString();


        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);

    }


}

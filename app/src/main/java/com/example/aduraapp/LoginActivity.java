package com.example.aduraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.aduraapp.databinding.ActivityLoginBinding;
import com.example.aduraapp.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText editUsername, editPassword;

    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

    }

    public void onButtonLoginClicked(View view) {
        //explicit intent
        String username = binding.editUsername.getText().toString();
        String password = binding.editPassword.getText().toString();


        if(password.equals("123")){
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra("USERNAME", username);
            mainIntent.putExtra("IS_LOGGED_IN", true);
            startActivity(mainIntent);
        }else{
            Toast.makeText(this, "Kombinasi Username dan Password Anda Salah!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onButtonRegisterClicked(View view){
        Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }
}
package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aduraapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;
//    TextView textGreeting;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent mainIntent = getIntent();
        String Username = mainIntent.getStringExtra("USERNAME");
        isLoggedIn = mainIntent.getBooleanExtra("IS_LOGGED_IN", false);

        if (!isLoggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

//        textGreeting = binding.textGreeting;//findViewById(R.id.textGreeting);
//        textGreeting.setText("Hello, " + Username);

        binding.textGreeting.setText("Hello, " + Username);
    }

}
package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

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
        replaceFragment(new HomeFragment());

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

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
                binding.textGreeting.setText("Hello, " + Username);
            } else if (itemId == R.id.riwayat) {
                replaceFragment(new RiwayatFragment());
                binding.textGreeting.setText("");
            } else if (itemId == R.id.profil) {
                replaceFragment(new ProfilFragment());
                binding.textGreeting.setText("");//
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}
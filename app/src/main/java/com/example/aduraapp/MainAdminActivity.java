package com.example.aduraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.aduraapp.databinding.ActivityMainadminBinding;

public class MainAdminActivity extends AppCompatActivity {

    private boolean isLoggedIn = false;

    private ActivityMainadminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainadminBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent mainIntent = getIntent();
        isLoggedIn = mainIntent.getBooleanExtra("IS_LOGGED_IN", false);

        if (!isLoggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        } else {
            replaceFragment(new HomeAdminFragment());
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeAdminFragment());
            } else if (itemId == R.id.riwayat) {
                replaceFragment(new RiwayatAdminFragment());
            }else if (itemId == R.id.profil) {
                replaceFragment(new ProfilAdminFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_adminlayout, fragment);
        fragmentTransaction.commit();
    }
}

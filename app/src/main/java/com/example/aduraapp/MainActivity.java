package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aduraapp.adapters.MenuAdapter;
import com.example.aduraapp.databinding.ActivityMainBinding;
import com.example.aduraapp.models.Menu;
import com.google.firebase.messaging.FirebaseMessaging;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private boolean isLoggedIn = false;
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        replaceFragment(new HomeFragment());

        Intent mainIntent = getIntent();
        isLoggedIn = mainIntent.getBooleanExtra("IS_LOGGED_IN", false);

        if (!isLoggedIn) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.riwayat) {
                replaceFragment(new RiwayatFragment());
            } else if (itemId == R.id.profil) {
                replaceFragment(new ProfilFragment());
            }
            return true;
        });

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e(TAG, "Fetching FCM registration token failed", task.getException());
                return;
            }

            String token = task.getResult();

            Log.d(TAG, "Token: " + token);

        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }

}
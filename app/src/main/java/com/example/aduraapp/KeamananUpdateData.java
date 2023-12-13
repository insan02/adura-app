package com.example.aduraapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aduraapp.databinding.ActivityKeamananupdatedataBinding;

public class KeamananUpdateData extends AppCompatActivity {

    ActivityKeamananupdatedataBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeamananupdatedataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}

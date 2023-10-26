package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MenuCreateActivity extends AppCompatActivity {

    String namaMenu;
    TextView textNamaMenuCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_create);

        Intent createIntent = getIntent();
        if(createIntent !=null) {
            namaMenu = createIntent.getStringExtra("Nama_Menu");
            textNamaMenuCreate = findViewById(R.id.textNamaMenuCreate);
            textNamaMenuCreate.setText(namaMenu);
        }
    }
}
package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MedisRiwayatActivity extends AppCompatActivity {

    List<MedisRiwayat>medisRiwayatList = new ArrayList<MedisRiwayat>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medisriwayat);

        fillMedisRiwayatList();
    }

    private void fillMedisRiwayatList() {
        MedisRiwayat m0 = new MedisRiwayat(124, "mas", "0546", "padangh", "kebakaran");

    }
}
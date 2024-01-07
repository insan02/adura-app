package com.example.aduraapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.adapters.MedisAdminListAdapter;
import com.example.aduraapp.models.MedisAdminRiwayat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedisAdminRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MedisAdminListAdapter medisadminListAdapter;
    ArrayList<MedisAdminRiwayat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medis_adminriwayat_list);

        recyclerView = findViewById(R.id.MedisAdminRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        medisadminListAdapter = new MedisAdminListAdapter(this, list);
        recyclerView.setAdapter(medisadminListAdapter);

        database = FirebaseDatabase.getInstance().getReference("Laporan_medis");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        MedisAdminRiwayat medisadminRiwayat = childSnapshot.getValue(MedisAdminRiwayat.class);
                        if (medisadminRiwayat != null) {
                            // Log and add to the list
                            Log.d("Tag", "Data Tanggal: " + medisadminRiwayat.getTanggalkejadian());
                            Log.d("Tag", "Data Keterangan: " + medisadminRiwayat.getKeterangan());
                            list.add(medisadminRiwayat);
                        } else {
                            Log.e("Tag", "MedisRiwayat is null");
                        }
                    }
                }
                medisadminListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error, e.g., log or display a message to the user
                Log.e("Tag", "Error: " + error.getMessage());
            }
        });
    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}

package com.example.aduraapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.adapters.KeamananAdminListAdapter;
import com.example.aduraapp.models.KeamananAdminRiwayat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KeamananAdminRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    KeamananAdminListAdapter keamananadminListAdapter;
    ArrayList<KeamananAdminRiwayat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanan_adminriwayat_list);

        recyclerView = findViewById(R.id.KeamananAdminRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        keamananadminListAdapter = new KeamananAdminListAdapter(this, list);
        recyclerView.setAdapter(keamananadminListAdapter);

        database = FirebaseDatabase.getInstance().getReference("Laporan_keamanan");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        KeamananAdminRiwayat keamananadminRiwayat = childSnapshot.getValue(KeamananAdminRiwayat.class);
                        if (keamananadminRiwayat != null) {
                            // Log and add to the list
                            Log.d("Tag", "Data Tanggal: " + keamananadminRiwayat.getTanggalkejadian());
                            Log.d("Tag", "Data Keterangan: " + keamananadminRiwayat.getKeterangan());
                            list.add(keamananadminRiwayat);
                        } else {
                            Log.e("Tag", "KeamananRiwayat is null");
                        }
                    }
                }
                keamananadminListAdapter.notifyDataSetChanged();
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

package com.example.aduraapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.adapters.KebakaranAdminListAdapter;
import com.example.aduraapp.models.KebakaranAdminRiwayat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KebakaranAdminRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    KebakaranAdminListAdapter kebakaranadminListAdapter;
    ArrayList<KebakaranAdminRiwayat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebakaran_adminriwayat_list);

        recyclerView = findViewById(R.id.KebakaranAdminRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        kebakaranadminListAdapter = new KebakaranAdminListAdapter(this, list);
        recyclerView.setAdapter(kebakaranadminListAdapter);

        database = FirebaseDatabase.getInstance().getReference("Laporan_kebakaran");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before adding new data

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        KebakaranAdminRiwayat kebakaranadminRiwayat = childSnapshot.getValue(KebakaranAdminRiwayat.class);
                        if (kebakaranadminRiwayat != null) {
                            // Log and add to the list
                            Log.d("Tag", "Data Tanggal: " + kebakaranadminRiwayat.getTanggalkejadian());
                            Log.d("Tag", "Data Keterangan: " + kebakaranadminRiwayat.getKeterangan());
                            kebakaranadminRiwayat.setnextIdLaporan(childSnapshot.getKey());
                            kebakaranadminRiwayat.setidUser(dataSnapshot.getKey()); // Menggunakan setnextIdLaporan
                            list.add(kebakaranadminRiwayat);
                        } else {
                            Log.e("Tag", "KebakaranRiwayat is null");
                        }
                    }
                }
                kebakaranadminListAdapter.notifyDataSetChanged();
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

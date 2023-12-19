package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.aduraapp.adapters.KeamananListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KeamananRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    KeamananListAdapter keamananListAdapter;
    ArrayList<KeamananRiwayat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanan_riwayat_list);

        recyclerView = findViewById(R.id.KeamananRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        keamananListAdapter = new KeamananListAdapter(this, list);
        recyclerView.setAdapter(keamananListAdapter);

        // Get the currently logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Use the user's ID to query the database for their security reports
            String userId = currentUser.getUid();
            database = FirebaseDatabase.getInstance().getReference("Laporan_keamanan").child(userId);

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear(); // Clear the list before adding new data

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("Tag", "DataSnapshot: " + dataSnapshot.toString());

                        KeamananRiwayat keamananRiwayat = dataSnapshot.getValue(KeamananRiwayat.class);

                        if (keamananRiwayat != null) {
                            // Log data for debugging
                            Log.d("Tag", "Data Tanggal: " + keamananRiwayat.getTanggalkejadian());
                            Log.d("Tag", "Data Keterangan: " + keamananRiwayat.getKeterangan());

                            keamananRiwayat.setnextIdLaporan(dataSnapshot.getKey());

                            list.add(keamananRiwayat);
                        } else {
                            Log.e("Tag", "KeamananRiwayat is null");
                        }
                    }
                    keamananListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error, e.g., log or display a message to the user
                    Log.e("Tag", "Error: " + error.getMessage());
                }
            });
        }
    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}

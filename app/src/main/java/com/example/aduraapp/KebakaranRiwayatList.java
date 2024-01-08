package com.example.aduraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.example.aduraapp.adapters.KebakaranListAdapter;
import com.example.aduraapp.models.KebakaranRiwayat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KebakaranRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    KebakaranListAdapter kebakaranListAdapter;
    ArrayList<KebakaranRiwayat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebakaran_riwayat_list);

        recyclerView = findViewById(R.id.KebakaranRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        kebakaranListAdapter = new KebakaranListAdapter(this, list);
        recyclerView.setAdapter(kebakaranListAdapter);

        // Get the currently logged-in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Use the user's ID to query the database for their security reports
            String userId = currentUser.getUid();
            database = FirebaseDatabase.getInstance().getReference("Laporan_kebakaran").child(userId);

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear(); // Clear the list before adding new data

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("Tag", "DataSnapshot: " + dataSnapshot.toString());

                        KebakaranRiwayat kebakaranRiwayat = dataSnapshot.getValue(KebakaranRiwayat.class);

                        if (kebakaranRiwayat != null) {
                            // Log data for debugging
                            Log.d("Tag", "Data Tanggal: " + kebakaranRiwayat.getTanggalkejadian());
                            Log.d("Tag", "Data Keterangan: " + kebakaranRiwayat.getKeterangan());

                            kebakaranRiwayat.setnextIdLaporan(dataSnapshot.getKey());

                            list.add(kebakaranRiwayat);
                        } else {
                            Log.e("Tag", "KebakaranRiwayat is null");
                        }
                    }
                    kebakaranListAdapter.notifyDataSetChanged();
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

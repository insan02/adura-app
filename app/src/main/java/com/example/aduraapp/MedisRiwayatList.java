package com.example.aduraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.aduraapp.adapters.MedisListAdapter;
import com.example.aduraapp.models.MedisRiwayat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MedisRiwayatList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MedisListAdapter medisListAdapter;
    ArrayList<MedisRiwayat> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medis_riwayat_list);

        recyclerView = findViewById(R.id.MedisRiwayatList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        medisListAdapter = new MedisListAdapter(this,list);
        recyclerView.setAdapter(medisListAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            String userId = currentUser.getUid();
            database = FirebaseDatabase.getInstance().getReference("Laporan_medis").child(userId);

            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();

                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Log.d("Tag", "DataSnapshot: " + dataSnapshot.toString());

                        MedisRiwayat medisRiwayat = dataSnapshot.getValue(MedisRiwayat.class);
                        if(medisRiwayat != null){

                            Log.d("Tag","Data Tanggal: " + medisRiwayat.getTanggalkejadian());
                            Log.d("Tag","Data Keterangan: " + medisRiwayat.getKeterangan());

//                            untuk nyimpan id laporan
                            medisRiwayat.setnextIdLaporan(dataSnapshot.getKey()); // Menggunakan setnextIdLaporan

                            list.add(medisRiwayat);

                        }else{
                            Log.e("Tag", "MedisRiwayat is null");
                        }
                        medisListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Tag", "Error: " + error.getMessage());
                }
            });

        }
    }
}
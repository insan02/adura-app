package com.example.aduraapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeAdminFragment extends Fragment {
    private TextView text_keamanan, text_medis, text_kebakaran;
    private DatabaseReference medisReference;
    private DatabaseReference keamananReference;
    private DatabaseReference kebakaranReference;

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeadmin, container, false);

        // Inisialisasi referensi ke Firebase Realtime Database
        medisReference = FirebaseDatabase.getInstance().getReference("Laporan_medis");
        keamananReference = FirebaseDatabase.getInstance().getReference("Laporan_keamanan");
        kebakaranReference = FirebaseDatabase.getInstance().getReference("Laporan_kebakaran");

        text_keamanan = view.findViewById(R.id.textkeamanan);
        text_medis = view.findViewById(R.id.textmedis);
        text_kebakaran = view.findViewById(R.id.textkebakaran);

        // Tambahkan ValueEventListener untuk mengambil dan mengupdate nilai TextView
        ValueEventListener medisListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalLaporan = 0;

                // Iterasi melalui setiap pengguna di bawah "Laporan_medis"
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Hitung jumlah laporan di bawah setiap pengguna
                    long userLaporanCount = userSnapshot.getChildrenCount();

                    // Akumulasikan jumlah laporan dari setiap pengguna
                    totalLaporan += userLaporanCount;
                }
                text_medis.setText(String.valueOf(totalLaporan));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error jika diperlukan
            }
        };

        ValueEventListener keamananListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalLaporan = 0;

                // Iterasi melalui setiap pengguna di bawah "Laporan_medis"
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Hitung jumlah laporan di bawah setiap pengguna
                    long userLaporanCount = userSnapshot.getChildrenCount();

                    // Akumulasikan jumlah laporan dari setiap pengguna
                    totalLaporan += userLaporanCount;
                }
                text_keamanan.setText(String.valueOf(totalLaporan));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error jika diperlukan
            }
        };

        ValueEventListener kebakaranListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalLaporan = 0;

                // Iterasi melalui setiap pengguna di bawah "Laporan_medis"
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Hitung jumlah laporan di bawah setiap pengguna
                    long userLaporanCount = userSnapshot.getChildrenCount();

                    // Akumulasikan jumlah laporan dari setiap pengguna
                    totalLaporan += userLaporanCount;
                }
                text_kebakaran.setText(String.valueOf(totalLaporan));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        medisReference.addValueEventListener(medisListener);
        keamananReference.addValueEventListener(keamananListener);
        kebakaranReference.addValueEventListener(kebakaranListener);

        return view;
    }
}

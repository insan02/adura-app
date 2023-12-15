package com.example.aduraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedisUpdateData extends AppCompatActivity {
    private EditText kolomnamapelapor;
    private EditText kolomnomorpelapor;
    private EditText kolomtanggalkejadian;
    private EditText kolomlokasikejadian;
    private EditText kolomketerangan;
    private ImageView imageView;
    private DatabaseReference reference;
    private FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medisupdatedata);
        kolomnamapelapor = findViewById(R.id.kolomnamapelapor);
        kolomnomorpelapor = findViewById(R.id.kolomnomorpelapor);
        kolomtanggalkejadian = findViewById(R.id.kolomtanggalkejadian);
        kolomlokasikejadian = findViewById(R.id.kolomlokasikejadian);
        kolomketerangan = findViewById(R.id.kolomketerangan);
        imageView = findViewById(R.id.imageUrl);
        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");
        String imageUrl = getIntent().getStringExtra("imageUrl");

        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");

//        idlaporan
        String nextIdLaporan = getIntent().getStringExtra("primaryKey");
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Laporan_medis").child(idUser).child(nextIdLaporan);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                                            String namapelapor = dataSnapshot.child("namapelapor").getValue(String.class);
                                            String nomorpelapor = dataSnapshot.child("nomorpelapor").getValue(String.class);
                                            String tanggalkejadian = dataSnapshot.child("tanggalkejadian").getValue(String.class);
                                            String lokasikejadian = dataSnapshot.child("lokasikejadian").getValue(String.class);
                                            String keterangan = dataSnapshot.child("keterangan").getValue(String.class);
                                            String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    Log.d("TAG", "pk: "+nextIdLaporan);
                                            Log.d("TAG", "namapelapor: " + namapelapor);
                                            Log.d("TAG", "nomorpelapor: " + nomorpelapor);
                                            Log.d("TAG", "tanggalkejadian: " + tanggalkejadian);
                                            Log.d("TAG", "lokasikejadian: " + lokasikejadian);
                                            Log.d("TAG", "keterangan: " + keterangan);
                                            Log.d("TAG", "imageUrl: " + imageUrl);
                    kolomnamapelapor.setText(namapelapor);
                    kolomtanggalkejadian.setText(tanggalkejadian);
                    kolomnomorpelapor.setText(nomorpelapor);
                    kolomlokasikejadian.setText(lokasikejadian);
                    kolomketerangan.setText(keterangan);
                                            if (!isDestroyed() && !isFinishing()) {
                    Glide.with(MedisUpdateData.this)
                            .load(imageUrl)
                            .fitCenter()
                            .into(imageView);}

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
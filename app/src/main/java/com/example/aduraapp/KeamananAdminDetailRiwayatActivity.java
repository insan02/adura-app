package com.example.aduraapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KeamananAdminDetailRiwayatActivity extends Activity {
    private Button btnterima;
    private Button btntolak;
    private String imageUrl;
    private String imageName;
    private DatabaseReference reference;
    private FirebaseDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamananadmindetailriwayat);

        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");
        imageUrl = getIntent().getStringExtra("imageUrl");
        imageName  = getIntent().getStringExtra("imageName");
        String namapelapor = getIntent().getStringExtra("namapelapor");
        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");
        Log.d("TAG", "imageName: "+imageName);
        String status = getIntent().getStringExtra("status");
        String nextIdLaporan = getIntent().getStringExtra("nextIdLaporan");
        String idUser = getIntent().getStringExtra("idUser");

        TextView tanggalkejadianTextView = findViewById(R.id.kolomtanggalkejadian);
        TextView keteranganTextView = findViewById(R.id.kolomketerangan);
        TextView namapelaporTextView = findViewById(R.id.kolomnamapelapor);
        TextView nomorpelaporTextView = findViewById(R.id.kolomnomorpelapor);
        TextView lokasikejadianTextView = findViewById(R.id.kolomlokasikejadian);
        TextView statusTextView = findViewById(R.id.kolomstatus);
        ImageView imageView = findViewById(R.id.gambar);

        updateTextView(tanggalkejadianTextView, "Tanggal Kejadian: ", tanggalkejadian);
        updateTextView(keteranganTextView, "Keterangan: ", keterangan);
        updateTextView(namapelaporTextView, "Nama Pelapor: ", namapelapor);
        updateTextView(nomorpelaporTextView, "Nomor Pelapor: ", nomorpelapor);
        updateTextView(lokasikejadianTextView, "Lokasi Kejadian: ", lokasikejadian);
        updateTextView(statusTextView, "Status: ", status);
        Log.d(TAG, imageUrl);
        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Laporan_keamanan").child(idUser).child(nextIdLaporan);
        btnterima = findViewById(R.id.btnterima);
        btntolak = findViewById(R.id.btntolak);

        if ("Verified".equals(status) || "Rejected".equals(status)) {
            btnterima.setVisibility(View.GONE);
            btntolak.setVisibility(View.GONE);
        } else {
            btnterima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(KeamananAdminDetailRiwayatActivity.this);
                    builder.setTitle("Konfirmasi");
                    builder.setMessage("Anda yakin ingin menerima laporan ini?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mengupdate status menjadi "verified"
                            reference.child("status").setValue("Verified")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(KeamananAdminDetailRiwayatActivity.this, "Status diterima", Toast.LENGTH_SHORT).show();
                                            // Sembunyikan tombol setelah status diterima
                                            btnterima.setVisibility(View.GONE);
                                            btntolak.setVisibility(View.GONE);

                                            Intent intent = new Intent(KeamananAdminDetailRiwayatActivity.this, KeamananAdminRiwayatList.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Jika gagal, tampilkan pesan error
                                            Toast.makeText(KeamananAdminDetailRiwayatActivity.this, "Gagal mengupdate status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            });
            btntolak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Membuat AlertDialog konfirmasi
                    AlertDialog.Builder builder = new AlertDialog.Builder(KeamananAdminDetailRiwayatActivity.this);
                    builder.setTitle("Konfirmasi");
                    builder.setMessage("Anda yakin ingin menolak laporan ini?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Mengupdate status menjadi "rejected"
                            reference.child("status").setValue("Rejected")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Jika berhasil, tampilkan pesan dan pindah ke activity lain
                                            Toast.makeText(KeamananAdminDetailRiwayatActivity.this, "Status ditolak", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(KeamananAdminDetailRiwayatActivity.this, KeamananAdminRiwayatList.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(KeamananAdminDetailRiwayatActivity.this, "Gagal mengupdate status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Tidak melakukan apa-apa jika pengguna memilih "Tidak"
                        }
                    });
                    builder.show();
                }
            });
        }


    }

    private void updateTextView(TextView textView,String prefix, String value ) {
        if (value != null && !value.isEmpty()) {
            textView.setText(prefix + value);
        } else {
            textView.setVisibility(View.GONE);
        }
    }


    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }

}

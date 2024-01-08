package com.example.aduraapp;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KeamananDetailRiwayatActivity extends Activity {
    private Button btnEdit;
    private Button btnHapus;
    private String imageUrl;
    private String imageName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanandetailriwayat);

        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");
        imageUrl = getIntent().getStringExtra("imageUrl");
        imageName  = getIntent().getStringExtra("imageName");
        String namapelapor = getIntent().getStringExtra("namapelapor");
        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");
        String status = getIntent().getStringExtra("status");

        String nextIdLaporan = getIntent().getStringExtra("nextIdLaporan");
        Log.d("TAG", "id: "+nextIdLaporan);
        Log.d("TAG", "imageName: "+imageName);

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

        btnEdit = findViewById(R.id.btnsimpan);
        btnHapus = findViewById(R.id.btnhapus);

        if ("Verified".equals(status) || "Rejected".equals(status)) {
            btnEdit.setVisibility(View.GONE);
        } else {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Membuka activity KeamananUpdateData dan mengirimkan primary key sebagai data tambahan
                    Intent intent = new Intent(KeamananDetailRiwayatActivity.this, KeamananUpdateData.class);
                    intent.putExtra("primaryKey", nextIdLaporan);
                    intent.putExtra("tanggalkejadian", tanggalkejadian);
                    intent.putExtra("keterangan", keterangan);
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("namapelapor", namapelapor);
                    intent.putExtra("nomorpelapor", nomorpelapor);
                    intent.putExtra("lokasikejadian", lokasikejadian);
                    intent.putExtra("status", status);

                    startActivity(intent);
                }
            });
        }


        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(nextIdLaporan, imageName);
            }
        });

    }

    private void updateTextView(TextView textView,String prefix, String value ) {
        if (value != null && !value.isEmpty()) {
            textView.setText(prefix + value);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void deleteData(String nextIdLaporan, String imageName) {
        // Konfirmasi sebelum menghapus data
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah Anda yakin ingin menghapus laporan ini?");
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Panggil metode performDelete jika pengguna mengonfirmasi penghapusan
                performDelete(nextIdLaporan, imageName);
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Batal menghapus, tidak melakukan apa-apa
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void performDelete(String nextIdLaporan, String imageUrl) {

        String idUser = FirebaseAuth.getInstance().getUid();

        DatabaseReference deleteref = FirebaseDatabase.getInstance().getReference("Laporan_keamanan").child(idUser).child(nextIdLaporan);

        deleteref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Dapatkan URL gambar dari Firebase Database
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Hapus data dari Firebase Database
                    deleteref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            // Hapus gambar dari Firebase Storage menggunakan URL
                            deleteImageFromStorage(imageUrl);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(KeamananDetailRiwayatActivity.this, "Data Gagal Dihapus: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(KeamananDetailRiwayatActivity.this, "Gagal membaca data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteImageFromStorage(String imageUrl) {
        // Dapatkan referensi Firebase Storage menggunakan URL gambar
        StorageReference imageRef = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);

        // Hapus gambar dari Firebase Storage
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(KeamananDetailRiwayatActivity.this, "Data dan gambar Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(KeamananDetailRiwayatActivity.this, "Gagal menghapus gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }

    public void onButtonEditClicked(View view){
        Intent editIntent = new Intent(this,KeamananUpdateData.class);
        startActivity(editIntent);
    }
}

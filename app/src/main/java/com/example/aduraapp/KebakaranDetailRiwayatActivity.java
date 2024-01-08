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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KebakaranDetailRiwayatActivity extends Activity {
    private Button btnEdit;
    private Button btnHapus;
    private String imageUrl;
    private String imageName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kebakarandetailriwayat);

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
                    // Membuka activity KebakaranUpdateData dan mengirimkan primary key sebagai data tambahan
                    Intent intent = new Intent(KebakaranDetailRiwayatActivity.this, KebakaranUpdateData.class);
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

    private void performDelete(String nextIdLaporan, String imageName) {

        String idUser = FirebaseAuth.getInstance().getUid();

        String storagePath = "images/" + imageName;
        DatabaseReference deleteref = FirebaseDatabase.getInstance().getReference("Laporan_kebakaran").child(idUser).child(nextIdLaporan);

        StorageReference storageref = FirebaseStorage.getInstance().getReference().child(storagePath);

        deleteref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                storageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(KebakaranDetailRiwayatActivity.this, "Data dan gambar Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(KebakaranDetailRiwayatActivity.this, "Gagal menghapus gambar" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(KebakaranDetailRiwayatActivity.this, "Data Gagal Dihapus" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }

    public void onButtonEditClicked(View view){
        Intent editIntent = new Intent(this,KebakaranUpdateData.class);
        startActivity(editIntent);
    }
}

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

//        idlaporan
        String nextIdLaporan = getIntent().getStringExtra("nextIdLaporan");
        Log.d("TAG", "id: "+nextIdLaporan);
        Log.d("TAG", "imageName: "+imageName);

        String namapelapor_detail = getString(R.string.pelapor_detail);
        String nomorpelapor_detail = getString(R.string.hp_detail);
        String tanggalkejadian_detail = getString(R.string.tanggal_detail);
        String lokasikejadian_detail = getString(R.string.lokasi_detail);
        String keterangan_detail = getString(R.string.keterangan_detail);

        TextView tanggalkejadianTextView = findViewById(R.id.kolomtanggalkejadian);
        TextView keteranganTextView = findViewById(R.id.kolomketerangan);
        TextView namapelaporTextView = findViewById(R.id.kolomnamapelapor);
        TextView nomorpelaporTextView = findViewById(R.id.kolomnomorpelapor);
        TextView lokasikejadianTextView = findViewById(R.id.kolomlokasikejadian);
        ImageView imageView = findViewById(R.id.imageUrl);

        updateTextView(tanggalkejadianTextView, tanggalkejadian_detail, tanggalkejadian);
        updateTextView(keteranganTextView, keterangan_detail, keterangan);
        updateTextView(namapelaporTextView, namapelapor_detail, namapelapor);
        updateTextView(nomorpelaporTextView, nomorpelapor_detail, nomorpelapor);
        updateTextView(lokasikejadianTextView, lokasikejadian_detail, lokasikejadian);
        Log.d(TAG, imageUrl);
        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .into(imageView);

        btnEdit = findViewById(R.id.btnsimpan);
        btnHapus = findViewById(R.id.btnhapus);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(nextIdLaporan, imageName);
            }
        });
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

                startActivity(intent);
            }
        });
    }

    private void deleteData(String nextIdLaporan, String imageName){
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

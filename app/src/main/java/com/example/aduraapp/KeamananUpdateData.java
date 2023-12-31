package com.example.aduraapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.aduraapp.databinding.ActivityKeamananupdatedataBinding;
import com.example.aduraapp.models.LaporanUpdate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class KeamananUpdateData extends AppCompatActivity {

    ActivityKeamananupdatedataBinding binding;

    FirebaseStorage storage;
    StorageReference storageRef;
    private EditText kolomnamapelapor;
    private EditText kolomnomorpelapor;
    private EditText kolomtanggalkejadian;
    private EditText kolomlokasikejadian;
    private String status;
    private EditText kolomketerangan;
    private ImageView imageView;
    Uri imageUri;
    RelativeLayout.LayoutParams originalParams;
    private DatabaseReference reference;
    private FirebaseDatabase db;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ProgressDialog progressDialog;
    double latitude;
    double longitude;
    private String imageUrl, tipe_laporan, address, nextIdLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeamananupdatedataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        status = "Not Verified";
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memperbarui laporan...");
        progressDialog.setCancelable(false);

        ImageView uploadImageView = findViewById(R.id.selectImagebtn);
        ImageView locationImageView = findViewById(R.id.location);

        originalParams = (RelativeLayout.LayoutParams) uploadImageView.getLayoutParams();

        locationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Periksa dan minta izin lokasi
                checkLocationPermission();
            }
        });

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KeamananUpdateData.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah Anda yakin ingin memperbarui laporan?");
                builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateData();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Batal memperbarui, tidak melakukan apa-apa
                        dismissProgressDialog();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        kolomnamapelapor = findViewById(R.id.kolomnamapelapor);
        kolomnomorpelapor = findViewById(R.id.kolomnomorpelapor);
        kolomtanggalkejadian = findViewById(R.id.kolomtanggalkejadian);
        kolomlokasikejadian = findViewById(R.id.kolomlokasikejadian);
        kolomketerangan = findViewById(R.id.kolomketerangan);
        imageView = findViewById(R.id.uploadImageView);
        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");

        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");


        // Retrieve the user ID from Firebase
        nextIdLaporan = getIntent().getStringExtra("primaryKey");
        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Laporan_keamanan").child(idUser).child(nextIdLaporan);
        Intent intent = getIntent();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    String namapelapor = dataSnapshot.child("namapelapor").getValue(String.class);
                    String nomorpelapor = dataSnapshot.child("nomorpelapor").getValue(String.class);
                    String tanggalkejadian = dataSnapshot.child("tanggalkejadian").getValue(String.class);
                    String lokasikejadian = dataSnapshot.child("lokasikejadian").getValue(String.class);
                    String keterangan = dataSnapshot.child("keterangan").getValue(String.class);
                     imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

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
                    if(intent != null & getIntent().hasExtra("ADDRESS")){
                        address = getIntent().getStringExtra("ADDRESS");
                        latitude = getIntent().getDoubleExtra("LATITUDE", 0.0);
                        longitude = getIntent().getDoubleExtra("LONGITUDE",0.0);
                        kolomlokasikejadian.setText(address);
                        Log.d("TAG", "alamat dari: "+address);
                    }
                    if (!isDestroyed() && !isFinishing()) {
                        Glide.with(KeamananUpdateData.this)
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

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            tipe_laporan = "KeamananUpdate";
            Intent intent = new Intent(KeamananUpdateData.this, MapsActivity.class);
            intent.putExtra("TIPE_LAPORAN", tipe_laporan);
            intent.putExtra("nextidlaporan", nextIdLaporan);
            startActivity(intent);
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                tipe_laporan = "KeamananUpdate";
                Intent intent = new Intent(KeamananUpdateData.this, MapsActivity.class);
                intent.putExtra("TIPE_LAPORAN", tipe_laporan);
                intent.putExtra("nextidlaporan", nextIdLaporan);
                startActivity(intent);
            } else {
                // Izin ditolak, Anda dapat memberikan informasi kepada pengguna atau mengambil tindakan lain yang sesuai
                Toast.makeText(this, "Izin lokasi ditolak. Aplikasi membutuhkan izin ini untuk bekerja dengan baik.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            // Update the imageUri with the newly selected image
            imageUri = data.getData();

            // Clear previous image
            imageView.setImageDrawable(null);

            // Load and display the new image using Glide
            Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("TAG", "Glide Load Failed", e);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            Log.d("TAG", "Glide Load Successful");

                            // Force a redraw of the ImageView
                            imageView.invalidate();

                            return false;
                        }
                    })
                    .into(imageView);
        }
    }


    private void updateData() {
        showProgressDialog();

        String namapelapor = kolomnamapelapor.getText().toString().trim();
        String nomorpelapor = kolomnomorpelapor.getText().toString().trim();
        String tanggalkejadian = kolomtanggalkejadian.getText().toString().trim();
        String lokasikejadian = kolomlokasikejadian.getText().toString().trim();
        String keterangan = kolomketerangan.getText().toString().trim();

        // Pastikan semua kolom diisi sebelum menyimpan
        if (namapelapor.isEmpty() || nomorpelapor.isEmpty() || tanggalkejadian.isEmpty() || lokasikejadian.isEmpty() || keterangan.isEmpty()) {
            Toast.makeText(this, "Isi semua kolom terlebih dahulu", Toast.LENGTH_SHORT).show();
            dismissProgressDialog();
            return;
        }

        // Upload the new image to Firebase Storage
        if (imageUri != null) {
            StorageReference imageRef = storageRef.child(imageUri.getLastPathSegment());
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        // Get the download URL of the uploaded image
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            // Buat objek data baru dengan URL gambar yang baru
                            LaporanUpdate data = new LaporanUpdate(namapelapor, nomorpelapor, tanggalkejadian, lokasikejadian, keterangan, uri.toString(), latitude, longitude, status);

                            // Update data ke Firebase Database
                            reference.setValue(data)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data berhasil diperbarui
                                        Toast.makeText(this, "Laporan berhasil diperbarui", Toast.LENGTH_SHORT).show();
                                        dismissProgressDialog();

                                        Intent intent = new Intent(KeamananUpdateData.this, KeamananRiwayatList.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Gagal memperbarui data
                                        Toast.makeText(this, "Gagal memperbarui laporan " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dismissProgressDialog();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Gagal mengupload gambar baru
                        Toast.makeText(this, "Gagal mengupload gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    });
        } else {
            // Jika user tidak memilih gambar baru, update data tanpa mengganti gambar
            LaporanUpdate data = new LaporanUpdate(namapelapor, nomorpelapor, tanggalkejadian, lokasikejadian, keterangan, imageUrl, latitude, longitude, status);

            // Update data ke Firebase Database
            reference.setValue(data)
                    .addOnSuccessListener(aVoid -> {
                        // Data berhasil diperbarui
                        Toast.makeText(this, "Laporan berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();

                        Intent intent = new Intent(KeamananUpdateData.this, KeamananRiwayatList.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal memperbarui data
                        Toast.makeText(this, "Gagal memperbarui laporan " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    });
        }
    }


    private void showProgressDialog() {
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }


}
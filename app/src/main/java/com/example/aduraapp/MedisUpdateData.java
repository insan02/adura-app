package com.example.aduraapp;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.aduraapp.models.LaporanUpdate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.List;

public class MedisUpdateData extends AppCompatActivity {
    private EditText kolomnamapelapor;
    private EditText kolomnomorpelapor;
    private EditText kolomtanggalkejadian;
    private EditText kolomlokasikejadian;
    private EditText kolomketerangan;
    private ImageView imageView;
    private ImageView selectImageBtn;
    private ImageView location;
    private Button btnkirim;
    private DatabaseReference reference;
    private FirebaseDatabase db;
    FirebaseStorage storage;
    StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri newImageUri;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    double latitude;
    double longitude;
    private ProgressDialog progressDialog;
    private String imageUrl;
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
        selectImageBtn = findViewById(R.id.selectImagebtn);
        location = findViewById(R.id.location);
        btnkirim = findViewById(R.id.btnkirim);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Memperbarui laporan...");
        progressDialog.setCancelable(false);

        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");

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

        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil method untuk memilih gambar dari perangkat
                openFileChooser();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
            }
        });

        btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private void updateData() {
        showProgressDialog();
        String namapelapor = kolomnamapelapor.getText().toString().trim();
        String nomorpelapor = kolomnomorpelapor.getText().toString().trim();
        String tanggalkejadian = kolomtanggalkejadian.getText().toString().trim();
        String lokasikejadian = kolomlokasikejadian.getText().toString().trim();
        String keterangan = kolomketerangan.getText().toString().trim();

        if (namapelapor.isEmpty() || nomorpelapor.isEmpty() || tanggalkejadian.isEmpty() || lokasikejadian.isEmpty() || keterangan.isEmpty()) {
            Toast.makeText(this, "Isi semua kolom terlebih dahulu", Toast.LENGTH_SHORT).show();
            dismissProgressDialog();
            return;
        }

        if(newImageUri!= null){
            StorageReference imageRef = storageRef.child(newImageUri.getLastPathSegment());
            imageRef.putFile(newImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            LaporanUpdate data = new LaporanUpdate(namapelapor, nomorpelapor, tanggalkejadian, lokasikejadian, keterangan, uri.toString(), latitude,longitude);

                            // Update data ke Firebase Database
                            reference.setValue(data)
                                    .addOnSuccessListener(aVoid -> {
                                        // Data berhasil diperbarui
                                        Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                                        dismissProgressDialog();

                                        Intent intent = new Intent(MedisUpdateData.this, MedisRiwayatList.class);
                                        startActivity(intent);
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        // Gagal memperbarui data
                                        Toast.makeText(this, "Gagal memperbarui data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dismissProgressDialog();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Gagal mengupload gambar baru
                        Toast.makeText(this, "Gagal mengupload gambar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    });
        }else{
            // Jika user tidak memilih gambar baru, update data tanpa mengganti gambar
            LaporanUpdate data = new LaporanUpdate(namapelapor, nomorpelapor, tanggalkejadian, lokasikejadian, keterangan, imageUrl, latitude, longitude);

            // Update data ke Firebase Database
            reference.setValue(data)
                    .addOnSuccessListener(aVoid -> {
                        // Data berhasil diperbarui
                        Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();

                        Intent intent = new Intent(MedisUpdateData.this, MedisRiwayatList.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Gagal memperbarui data
                        Toast.makeText(this, "Gagal memperbarui data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        dismissProgressDialog();
                    });
        }
    }

    private void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    private void showProgressDialog() {
        progressDialog.show();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location->{
                        if (location!= null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            getAddressFromLocation(latitude, longitude);
                        }
                    });
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this);
        try{
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses.size()>0){
                Address address = addresses.get(0);
                String addressline = address.getAddressLine(0);

                kolomlokasikejadian.setText(addressline);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getLocation();
            } else {
                // Izin ditolak, Anda dapat memberikan informasi kepada pengguna atau mengambil tindakan lain yang sesuai
                Toast.makeText(this, "Izin lokasi ditolak. Aplikasi membutuhkan izin ini untuk bekerja dengan baik.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            newImageUri = data.getData();
            imageView.setImageURI(newImageUri);
        }
    }
}
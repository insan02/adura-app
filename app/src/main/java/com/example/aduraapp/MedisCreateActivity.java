package com.example.aduraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aduraapp.databinding.ActivityMediscreateBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedisCreateActivity extends AppCompatActivity {

    private ActivityMediscreateBinding binding;
    private Uri imageUri;
    private String kolomnamapelapor, kolomnomorpelapor, kolomtanggalkejadian, kolomlokasikejadian, kolomketerangan, status;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private RelativeLayout.LayoutParams originalParams;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private String tipe_laporan, address;
    private double locX, locY;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediscreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images");

        ImageView uploadImageView = findViewById(R.id.selectImagebtn);
        ImageView locationImageView = findViewById(R.id.location);

        Intent intent = getIntent();
        if (intent != null) {
            address = getIntent().getStringExtra("ADDRESS");
            latitude = getIntent().getDoubleExtra("LATITUDE", 0.0);
            longitude = getIntent().getDoubleExtra("LONGITUDE",0.0);
            binding.kolomlokasikejadian.setText(address);
            Log.d("TAG", "alamat dari: "+address);
        }

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


        binding.btnkirim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                kolomnamapelapor = binding.kolomnamapelapor.getText().toString();
                kolomnomorpelapor = binding.kolomnomorpelapor.getText().toString();
                kolomlokasikejadian = binding.kolomlokasikejadian.getText().toString();
                kolomtanggalkejadian = binding.kolomtanggalkejadian.getText().toString();
                kolomketerangan = binding.kolomketerangan.getText().toString();

                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (kolomketerangan.isEmpty() || kolomtanggalkejadian.isEmpty() || kolomnomorpelapor.isEmpty() || kolomlokasikejadian.isEmpty() || kolomnamapelapor.isEmpty()) {
                    Toast.makeText(MedisCreateActivity.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isValidDate(kolomtanggalkejadian)) {
                        Toast.makeText(MedisCreateActivity.this, "Format tanggal tidak valid. Harap sesuaikan dengan format DD-MM-YYYY", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Laporan_medis");

                    reference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                            long entryCount = dataSnapshot.getChildrenCount();

                            String nextIdLaporan = String.valueOf(entryCount + 1);

                            DatabaseReference userEntryRef = reference.child(idUser).child(nextIdLaporan);

                            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                Date date = inputFormat.parse(kolomtanggalkejadian);
                                kolomtanggalkejadian = outputFormat.format(date);

                                userEntryRef.child("kolomtanggalkejadian").setValue((kolomtanggalkejadian));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String imageUrl = imageUri != null ? imageUri.toString() : "";
                            status = "Not Verified";
                            Map<String, Object> data = new HashMap<>();
                            data.put("namapelapor", kolomnamapelapor);
                            data.put("nomorpelapor", kolomnomorpelapor);
                            data.put("tanggalkejadian", kolomtanggalkejadian);
                            data.put("lokasikejadian", kolomlokasikejadian);
                            data.put("latitude", latitude);
                            data.put("longitude", longitude);
                            data.put("keterangan", kolomketerangan);
                            data.put("status", status);

                            userEntryRef.setValue(data);

                            if (imageUri != null) {
                                saveImageToStorage(imageUri, nextIdLaporan);
                            } else {
                                resetForm();
                            }
                        }

                        private void saveImageToStorage(Uri imageUri, String key) {
                            String timestamp = String.valueOf(System.currentTimeMillis());
                            String imageName = "image_" + timestamp;

                            StorageReference imageRef = storageRef.child(imageName);
                            UploadTask uploadTask = imageRef.putFile(imageUri);

                            uploadTask.addOnSuccessListener(taskSnapshot -> {
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    // Sekarang uri berisi URL gambar yang akan di gunakan
                                    String imageUrl = uri.toString();

                                    // Lanjutkan dengan menyimpan URL gambar ke Realtime Database atau melakukan apa pun yang diperlukan
                                    saveImageUrlToDatabase(key, imageUrl, imageName);
                                });
                            }).addOnFailureListener(e -> {
                                Toast.makeText(MedisCreateActivity.this, "Gagal Mengunggah gambar", Toast.LENGTH_SHORT).show();
                            });
                        }

                        private void saveImageUrlToDatabase(String key, String imageUrl, String imageName) {
                            String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference userEntryRef = reference.child(idUser).child(key);

                            userEntryRef.child("imageUrl").setValue(imageUrl);
                            userEntryRef.child("imageName").setValue(imageName);

                            resetForm();
                        }

                        private void resetForm() {
                            // Tampilkan pesan atau lakukan tindakan lain jika diperlukan
                            Toast.makeText(MedisCreateActivity.this, "Laporan Terkirim", Toast.LENGTH_SHORT).show();

                            // Reset nilai-nilai form
                            binding.kolomnamapelapor.setText("");
                            binding.kolomketerangan.setText("");
                            binding.kolomlokasikejadian.setText("");
                            binding.kolomnomorpelapor.setText("");
                            binding.kolomtanggalkejadian.setText("");

                            // Kembalikan parameter tata letak yang ada
                            if (originalParams != null) {
                                ImageView uploadImageView = findViewById(R.id.selectImagebtn);
                                uploadImageView.setLayoutParams(originalParams);
                            }

                            Intent intent = new Intent(MedisCreateActivity.this, MedisRiwayatList.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(MedisCreateActivity.this, "Error Dalam Pengiriman Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Izin sudah diberikan, dapatkan lokasi
            tipe_laporan = "MedisCreate";
            Intent intent = new Intent(MedisCreateActivity.this, MapsActivity.class);
            intent.putExtra("TIPE_LAPORAN", tipe_laporan);
            startActivity(intent);
        } else {
            // Izin belum diberikan, minta izin
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

//    private void getLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, location -> {
//                        if (location != null) {
//                             latitude = location.getLatitude();
//                             longitude = location.getLongitude();
//
//                            getAddressFromLocation(latitude, longitude);
//                        }
//                    });
//        } else {
//            // Jika izin belum diberikan, minta izin kepada pengguna
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
//        }
//    }
//
//    private void getAddressFromLocation(double latitude, double longitude) {
//        Geocoder geocoder = new Geocoder(this);
//        try {
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses.size() > 0) {
//                // Dapatkan alamat dari hasil geocoder
//                Address address = addresses.get(0);
//                String addressLine = address.getAddressLine(0);
//
//                // Tampilkan alamat di kolom lokasi
//                binding.kolomlokasikejadian.setText(addressLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                tipe_laporan = "MedisCreate";
                Intent intent = new Intent(MedisCreateActivity.this, MapsActivity.class);
                intent.putExtra("TIPE_LAPORAN", tipe_laporan);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();

            ImageView uploadImageView = findViewById(R.id.selectImagebtn);

            originalParams = (RelativeLayout.LayoutParams) uploadImageView.getLayoutParams();

            uploadImageView.setImageURI(imageUri);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            uploadImageView.setLayoutParams(layoutParams);
        }
    }

    private boolean isValidDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(input);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

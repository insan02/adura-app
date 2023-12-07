package com.example.aduraapp;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import android.net.Uri;

import com.example.aduraapp.databinding.ActivityKeamanancreateBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeamananCreateActivity extends Activity {

    ActivityKeamanancreateBinding binding;
    Uri imageUri;
    String kolomnamapelapor, kolomnomorpelapor, kolomtanggalkejadian, kolomlokasikejadian, kolomketerangan;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageRef;

    // Simpan ukuran asli icon selectImagebtn
    RelativeLayout.LayoutParams originalParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeamanancreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images"); // Folder image di firebase store

        // Ambil referensi ke ImageView menggunakan ID selectImagebtn
        ImageView uploadImageView = findViewById(R.id.selectImagebtn);

        // Simpan ukuran asli
        originalParams = (RelativeLayout.LayoutParams) uploadImageView.getLayoutParams();

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.btnkirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kolomnamapelapor = binding.kolomnamapelapor.getText() != null ? binding.kolomnamapelapor.getText().toString() : "";
                kolomnomorpelapor = binding.kolomnomorpelapor.getText() != null ? binding.kolomnomorpelapor.getText().toString() : "";
                kolomtanggalkejadian = binding.kolomtanggalkejadian.getText() != null ? binding.kolomtanggalkejadian.getText().toString() : "";
                kolomlokasikejadian = binding.kolomlokasikejadian.getText() != null ? binding.kolomlokasikejadian.getText().toString() : "";
                kolomketerangan = binding.kolomketerangan.getText() != null ? binding.kolomketerangan.getText().toString() : "";

                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (kolomnamapelapor.isEmpty() || kolomnomorpelapor.isEmpty() || kolomtanggalkejadian.isEmpty() || kolomlokasikejadian.isEmpty() || kolomketerangan.isEmpty() || imageUri == null) {
                    // Tampilkan notifikasi untuk mengisi semua kolom dan memilih gambar
                    Toast.makeText(KeamananCreateActivity.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // Validasi format tanggal
                    if (!isValidDate(kolomtanggalkejadian)) {
                        // Tampilkan notifikasi untuk meminta pengguna menyesuaikan format tanggal
                        Toast.makeText(KeamananCreateActivity.this, "Format tanggal tidak valid. Harap sesuaikan dengan format DD-MM-YYYY", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Ambil referensi ke Firebase Database
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Laporan_keamanan");

                    // Query untuk mendapatkan jumlah entri yang ada
                    reference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Dapatkan jumlah entri yang ada
                            long entryCount = dataSnapshot.getChildrenCount();

                            // Dapatkan idLaporan berikutnya dalam format string (1, 2, 3, ...)
                            String nextIdLaporan = String.valueOf(entryCount + 1);

                            // Gunakan nextIdLaporan dalam DatabaseReference
                            DatabaseReference userEntryRef = reference.child(idUser).child(nextIdLaporan);

                            // Format tanggal ke format yang disimpan di Firebase Database (yyyy-MM-dd)
                            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

                            try {
                                Date date = inputFormat.parse(kolomtanggalkejadian);
                                kolomtanggalkejadian = outputFormat.format(date);

                                // Perbarui kolomtanggalkejadian yang disimpan di Firebase Database
                                userEntryRef.child("kolomtanggalkejadian").setValue(kolomtanggalkejadian);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            // Ambil URL gambar dari Uri gambar
                            String imageUrl = imageUri != null ? imageUri.toString() : "";

                            // Buat objek data dan masukkan nilai ke dalamnya
                            Map<String, Object> data = new HashMap<>();
                            data.put("imageUrl", imageUrl);
                            data.put("namapelapor", kolomnamapelapor);
                            data.put("nomorpelapor", kolomnomorpelapor);
                            data.put("tanggalkejadian", kolomtanggalkejadian);
                            data.put("lokasikejadian", kolomlokasikejadian);
                            data.put("keterangan", kolomketerangan);

                            // Simpan data ke Firebase Database
                            userEntryRef.setValue(data);

                            // Simpan gambar ke Firebase Storage
                            if (imageUri != null) {
                                saveImageToStorage(imageUri, nextIdLaporan);
                            }

                            // Setelah data dikirim, kosongkan gambar yang sudah dipilih
                            uploadImageView.setImageResource(R.drawable.__icon__cloud_download_);
                            imageUri = null;

                            // Kembalikan ukuran asli setelah menghapus gambar
                            uploadImageView.setLayoutParams(originalParams);

                            Toast.makeText(KeamananCreateActivity.this, "Laporan Terkirim", Toast.LENGTH_SHORT).show();
                            binding.kolomnamapelapor.setText("");
                            binding.kolomnomorpelapor.setText("");
                            binding.kolomtanggalkejadian.setText(""); // Setel ulang agar menampilkan tanggal yang sudah diformat
                            binding.kolomlokasikejadian.setText("");
                            binding.kolomketerangan.setText("");
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Tangani kesalahan dalam kueri
                            Toast.makeText(KeamananCreateActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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
            imageUri = data.getData();

            // Ambil referensi ke ImageView menggunakan ID selectImagebtn
            ImageView uploadImageView = findViewById(R.id.selectImagebtn);

            // Set gambar ke ImageView menggunakan setImageURI
            uploadImageView.setImageURI(imageUri);

            // Set layout params untuk mengubah panjang dan lebar gambar
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );

            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);  // Pusatkan gambar di RelativeLayout
            uploadImageView.setLayoutParams(layoutParams);
        }
    }

    private void saveImageToStorage(Uri imageUri, String key) {
        // Simpan gambar ke Firebase Storage
        StorageReference imageRef = storageRef.child(key); // Simpan dengan nama key sebagai nama file
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Tambahkan listener untuk menangani keberhasilan atau kegagalan unggah
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Gambar berhasil diunggah
            // Dapatkan URL gambar dari taskSnapshot.getDownloadUrl() dan tambahkan ke database jika diperlukan
        }).addOnFailureListener(e -> {
            // Gagal mengunggah gambar
            Toast.makeText(KeamananCreateActivity.this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show();
        });
    }

    // Fungsi untuk memeriksa apakah format tanggal benar
    private boolean isValidDate(String input) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try {
            // Parsing input sebagai tanggal
            dateFormat.parse(input);
            return true;
        } catch (ParseException e) {
            // Jika ada kesalahan parsing, tanggal tidak valid
            return false;
        }
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}

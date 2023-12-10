package com.example.aduraapp;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aduraapp.databinding.ActivityKeamanancreateBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class KeamananCreateActivity extends Activity {

    ActivityKeamanancreateBinding binding;
    Uri imageUri;
    String kolomnamapelapor, kolomnomorpelapor, kolomtanggalkejadian, kolomlokasikejadian, kolomketerangan;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageRef;
    RelativeLayout.LayoutParams originalParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeamanancreateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images");

        ImageView uploadImageView = findViewById(R.id.selectImagebtn);

        binding.selectImagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        binding.btnkirim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                kolomnamapelapor = binding.kolomnamapelapor.getText() != null ? binding.kolomnamapelapor.getText().toString() : "";
                kolomnomorpelapor = binding.kolomnomorpelapor.getText() != null ? binding.kolomnomorpelapor.getText().toString() : "";
                kolomlokasikejadian = binding.kolomlokasikejadian.getText() != null ? binding.kolomlokasikejadian.getText().toString() : "";
                kolomtanggalkejadian = binding.kolomtanggalkejadian.getText() != null ? binding.kolomtanggalkejadian.getText().toString() : "";
                kolomketerangan = binding.kolomketerangan.getText() != null ? binding.kolomketerangan.getText().toString() : "";

                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (kolomketerangan.isEmpty() || kolomtanggalkejadian.isEmpty() || kolomnomorpelapor.isEmpty() || kolomlokasikejadian.isEmpty() || kolomnamapelapor.isEmpty()) {
                    Toast.makeText(KeamananCreateActivity.this, "Harap isi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isValidDate(kolomtanggalkejadian)) {
                        Toast.makeText(KeamananCreateActivity.this, "Format tanggal tidak valid. Harap sesuaikan dengan format DD-MM-YYYY", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Laporan_keamanan");

                    reference.child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
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

                            Map<String, Object> data = new HashMap<>();
                            data.put("namapelapor", kolomnamapelapor);
                            data.put("nomorpelapor", kolomnomorpelapor);
                            data.put("tanggalkejadian", kolomtanggalkejadian);
                            data.put("lokasikejadian", kolomlokasikejadian);
                            data.put("keterangan", kolomketerangan);

                            userEntryRef.setValue(data);

                            if (imageUri != null) {
                                saveImageToStorage(imageUri, nextIdLaporan);
                            }else{
                                resetForm();
                            }


                        }

                        private void saveImageToStorage(Uri imageUri, String key) {
                            StorageReference imageRef = storageRef.child(key);
                            UploadTask uploadTask = imageRef.putFile(imageUri);

                            uploadTask.addOnSuccessListener(taskSnapshot -> {
                                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                    // Sekarang uri berisi URL gambar yang dapat Anda gunakan
                                    String imageUrl = uri.toString();

                                    // Lanjutkan dengan menyimpan URL gambar ke Realtime Database atau melakukan apa pun yang diperlukan
                                    saveImageUrlToDatabase(key, imageUrl);
                                });

                            }).addOnFailureListener(e -> {
                                Toast.makeText(KeamananCreateActivity.this, "Gagal Mengunggah gambar", Toast.LENGTH_SHORT).show();
                            });
                        }
                        private void saveImageUrlToDatabase(String key, String imageUrl) {
                            String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference userEntryRef = reference.child(idUser).child(key);

                            userEntryRef.child("imageUrl").setValue(imageUrl);

                            resetForm();
                        }
                        private void resetForm() {
                            // Tampilkan pesan atau lakukan tindakan lain jika diperlukan
                            Toast.makeText(KeamananCreateActivity.this, "Laporan Terkirim", Toast.LENGTH_SHORT).show();

                            // Reset nilai-nilai form
                            binding.kolomnamapelapor.setText("");
                            binding.kolomketerangan.setText("");
                            binding.kolomlokasikejadian.setText("");
                            binding.kolomnomorpelapor.setText("");
                            binding.kolomtanggalkejadian.setText("");
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Toast.makeText(KeamananCreateActivity.this, "Error Dalam Pengiriman Data", Toast.LENGTH_SHORT).show();
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

            ImageView uploadImageView = findViewById(R.id.selectImagebtn);

            // Simpan parameter tata letak yang ada
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

    public void onBackPressed(View view) {
        super.onBackPressed();
        finish();
    }
}

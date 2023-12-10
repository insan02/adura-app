package com.example.aduraapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;


import com.example.aduraapp.databinding.ActivityKeamananupdatedataBinding;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class KeamananUpdateData extends AppCompatActivity {

    ActivityKeamananupdatedataBinding binding;
    Uri imageUri;

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageRef;

    RelativeLayout.LayoutParams originalParams;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKeamananupdatedataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("images_keamanan"); // Folder image di firebase store

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

        binding.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kolomnamapelapor = binding.kolomnamapelapor.getText().toString();
                String kolomnomorpelapor = binding.kolomnomorpelapor.getText().toString();
                String kolomtanggalkejadian = binding.kolomtanggalkejadian.getText().toString();
                String kolomlokasikejadian = binding.kolomlokasikejadian.getText().toString();
                String kolomketerangan = binding.kolomketerangan.getText().toString();
                String imageUrl = imageUri != null ? imageUri.toString() : "";

                updateData(imageUri, kolomnamapelapor, kolomnomorpelapor, kolomtanggalkejadian, kolomlokasikejadian, kolomketerangan, imageUrl);
            }
        });
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private void updateData(Uri imageUri, String kolomnamapelapor, String kolomnomorpelapor, String kolomtanggalkejadian, String kolomlokasikejadian, String kolomketerangan, String imageUrl){
        Map<String, Object> data = new HashMap<>();
        data.put("imageUrl", imageUrl);
        data.put("namapelapor", kolomnamapelapor);
        data.put("nomorpelapor", kolomnomorpelapor);
        data.put("tanggalkejadian", kolomtanggalkejadian);
        data.put("lokasikejadian", kolomlokasikejadian);
        data.put("keterangan", kolomketerangan);

        StorageReference imageRef = storageRef.child(getOriginalFileName(imageUri)); // Simpan dengan nama file asli
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Ambil referensi ke Firebase Database
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Laporan_keamanan");

        String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Query untuk mendapatkan jumlah entri yang ada
        reference.child(idUser).updateChildren(data).addOnCompleteListener(new OnCompleteListener(){
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){

                    binding.kolomnamapelapor.setText("");
                    binding.kolomnomorpelapor.setText("");
                    binding.kolomtanggalkejadian.setText("");
                    binding.kolomlokasikejadian.setText("");
                    binding.kolomketerangan.setText("");

                }else {
                    Toast.makeText(KeamananUpdateData.this,"Gagal mengedit data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getOriginalFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        result = cursor.getString(columnIndex);
                    } else {
                        // If DISPLAY_NAME is not available, use the last segment of the URI
                        result = uri.getLastPathSegment();
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


}



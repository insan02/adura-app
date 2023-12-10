package com.example.aduraapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class KeamananDetailRiwayatActivity extends AppCompatActivity {
    private ImageView imageView;
    private Uri imageUri;
    private String imageUrl; // Add this variable to store the image URL
    private StorageReference storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keamanandetailriwayat);

        // Retrieve data from the intent
        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");
        String namapelapor = getIntent().getStringExtra("namapelapor");
        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");

        // Get the image URL from the intent
        imageUrl = getIntent().getStringExtra("imageUrl");

        // Display data in TextViews and ImageView
        TextView tanggalkejadianTextView = findViewById(R.id.kolomtanggalkejadian);
        TextView keteranganTextView = findViewById(R.id.kolomketerangan);
        TextView namapelaporTextView = findViewById(R.id.kolomnamapelapor);
        TextView nomorpelaporTextView = findViewById(R.id.kolomnomorpelapor);
        TextView lokasikejadianTextView = findViewById(R.id.kolomlokasikejadian);
        imageView = findViewById(R.id.gambar);

        // Additional check for imageUri
        if (getIntent().hasExtra("imageUri")) {
            // Check if the imageUri is not null or empty before parsing
            String imageUriString = getIntent().getStringExtra("imageUri");
            if (imageUriString != null && !imageUriString.isEmpty()) {
                imageUri = Uri.parse(imageUriString);
            }
        }

        updateTextView(tanggalkejadianTextView, "Tanggal Kejadian: ", tanggalkejadian);
        updateTextView(keteranganTextView, "Keterangan: ", keterangan);
        updateTextView(namapelaporTextView, "Nama Pelapor: ", namapelapor);
        updateTextView(nomorpelaporTextView, "Nomor Pelapor: ", nomorpelapor);
        updateTextView(lokasikejadianTextView, "Lokasi Kejadian: ", lokasikejadian);

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images_keamanan");

    }



    // Method to update text in TextView with custom prefix
    private void updateTextView(TextView textView, String prefix, String value) {
        textView.setText(prefix + value);
    }
}

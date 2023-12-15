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

import com.bumptech.glide.Glide;

public class MedisDetailRiwayatActivity extends Activity {
    private Button btnEdit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medisdetailriwayat);

        String tanggalkejadian = getIntent().getStringExtra("tanggalkejadian");
        String keterangan = getIntent().getStringExtra("keterangan");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String namapelapor = getIntent().getStringExtra("namapelapor");
        String nomorpelapor = getIntent().getStringExtra("nomorpelapor");
        String lokasikejadian = getIntent().getStringExtra("lokasikejadian");

//        idlaporan
        String nextIdLaporan = getIntent().getStringExtra("nextIdLaporan");
        Log.d("TAG", "id: "+nextIdLaporan);

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

        btnEdit = findViewById(R.id.btnedit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuka activity MedisUpdateData dan mengirimkan primary key sebagai data tambahan
                Intent intent = new Intent(MedisDetailRiwayatActivity.this, MedisUpdateData.class);
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

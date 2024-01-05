package com.example.aduraapp;

import android.os.Bundle;
import android.text.Html;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PanduanActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        // Inisialisasi SearchView dan TextView
        SearchView searchView = findViewById(R.id.searchView);
        textView = findViewById(R.id.textView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tandai kata yang dicari dengan warna latar belakang kuning
                highlightSearchText(newText);
                return false;
            }
        });
    }

    private void highlightSearchText(String searchText) {
        String text = textView.getText().toString();

        // Gunakan HTML untuk menyoroti kata yang dicari
        String highlightedText = text.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");

        // Terapkan teks yang sudah di-highlight pada TextView
        textView.setText(Html.fromHtml(highlightedText));
    }
}

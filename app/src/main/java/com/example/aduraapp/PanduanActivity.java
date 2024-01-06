package com.example.aduraapp;

import android.os.Bundle;
import android.text.Html;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PanduanActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textViewTitle1, textViewTitle1Point1, textViewTitle1Point2, textViewTitle1Point3, textViewTitle1Point4, textViewTitle1Point5;
    private TextView textViewTitle2, textViewTitle2Point1, textViewTitle2Point2, textViewTitle2Point3;
    private TextView textViewTitle3, textViewTitle3Point1, textViewTitle3Point2, textViewTitle3Point3, textViewTitle3Point4;
    private TextView textViewTitle4, textViewTitle4Point1, textViewTitle4Point2, textViewTitle4Point3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        // Inisialisasi SearchView dan TextView
        SearchView searchView = findViewById(R.id.searchView);
        textViewTitle1 = findViewById(R.id.textViewTitle1);
        textViewTitle1Point1 = findViewById(R.id.textViewTitle1Point1);
        textViewTitle1Point2 = findViewById(R.id.textViewTitle1Point2);
        textViewTitle1Point3 = findViewById(R.id.textViewTitle1Point3);
        textViewTitle1Point4 = findViewById(R.id.textViewTitle1Point4);
        textViewTitle1Point5 = findViewById(R.id.textViewTitle1Point5);
        textViewTitle2 = findViewById(R.id.textViewTitle2);
        textViewTitle2Point1 = findViewById(R.id.textViewTitle2Point1);
        textViewTitle2Point2 = findViewById(R.id.textViewTitle2Point2);
        textViewTitle2Point3 = findViewById(R.id.textViewTitle2Point3);
        textViewTitle3 = findViewById(R.id.textViewTitle3);
        textViewTitle3Point1 = findViewById(R.id.textViewTitle3Point1);
        textViewTitle3Point2 = findViewById(R.id.textViewTitle3Point2);
        textViewTitle3Point3 = findViewById(R.id.textViewTitle3Point3);
        textViewTitle3Point4 = findViewById(R.id.textViewTitle3Point4);
        textViewTitle4 = findViewById(R.id.textViewTitle4);
        textViewTitle4Point1 = findViewById(R.id.textViewTitle4Point1);
        textViewTitle4Point2 = findViewById(R.id.textViewTitle4Point2);
        textViewTitle4Point3 = findViewById(R.id.textViewTitle4Point3);

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
        String text1 = textViewTitle1.getText().toString();
        String text1Point1 = textViewTitle1Point1.getText().toString();
        String text1Point2 = textViewTitle1Point2.getText().toString();
        String text1Point3 = textViewTitle1Point3.getText().toString();
        String text1Point4 = textViewTitle1Point4.getText().toString();
        String text1Point5 = textViewTitle1Point5.getText().toString();
        String text2 = textViewTitle2.getText().toString();
        String text2Point1 = textViewTitle2Point1.getText().toString();
        String text2Point2 = textViewTitle2Point2.getText().toString();
        String text2Point3 = textViewTitle2Point3.getText().toString();
        String text3 = textViewTitle3.getText().toString();
        String text3Point1 = textViewTitle3Point1.getText().toString();
        String text3Point2 = textViewTitle3Point2.getText().toString();
        String text3Point3 = textViewTitle3Point3.getText().toString();
        String text3Point4 = textViewTitle3Point4.getText().toString();
        String text4 = textViewTitle4.getText().toString();
        String text4Point1 = textViewTitle4Point1.getText().toString();
        String text4Point2 = textViewTitle4Point2.getText().toString();
        String text4Point3 = textViewTitle4Point3.getText().toString();

        // Gunakan HTML untuk menyoroti kata yang dicari
        String highlightedText1 = text1.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText1Point1 = text1Point1.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText1Point2 = text1Point2.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText1Point3 = text1Point3.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText1Point4 = text1Point4.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText1Point5 = text1Point5.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText2 = text2.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText2Point1 = text2Point1.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText2Point2 = text2Point2.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText2Point3 = text2Point3.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText3 = text3.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText3Point1 = text3Point1.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText3Point2 = text3Point2.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText3Point3 = text3Point3.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText3Point4 = text3Point4.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText4 = text4.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText4Point1 = text4Point1.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText4Point2 = text4Point2.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");
        String highlightedText4Point3 = text4Point3.replaceAll("(?i)(" + searchText + ")", "<font color='#FFEB3B'>$1</font>");

        // Atur teks HTML yang telah disorot kembali ke TextView
        textViewTitle1.setText(Html.fromHtml(highlightedText1), TextView.BufferType.SPANNABLE);
        textViewTitle1Point1.setText(Html.fromHtml(highlightedText1Point1), TextView.BufferType.SPANNABLE);
        textViewTitle1Point2.setText(Html.fromHtml(highlightedText1Point2), TextView.BufferType.SPANNABLE);
        textViewTitle1Point3.setText(Html.fromHtml(highlightedText1Point3), TextView.BufferType.SPANNABLE);
        textViewTitle1Point4.setText(Html.fromHtml(highlightedText1Point4), TextView.BufferType.SPANNABLE);
        textViewTitle1Point5.setText(Html.fromHtml(highlightedText1Point5), TextView.BufferType.SPANNABLE);
        textViewTitle2.setText(Html.fromHtml(highlightedText2), TextView.BufferType.SPANNABLE);
        textViewTitle2Point1.setText(Html.fromHtml(highlightedText2Point1), TextView.BufferType.SPANNABLE);
        textViewTitle2Point2.setText(Html.fromHtml(highlightedText2Point2), TextView.BufferType.SPANNABLE);
        textViewTitle2Point3.setText(Html.fromHtml(highlightedText2Point3), TextView.BufferType.SPANNABLE);
        textViewTitle3.setText(Html.fromHtml(highlightedText3), TextView.BufferType.SPANNABLE);
        textViewTitle3Point1.setText(Html.fromHtml(highlightedText3Point1), TextView.BufferType.SPANNABLE);
        textViewTitle3Point2.setText(Html.fromHtml(highlightedText3Point2), TextView.BufferType.SPANNABLE);
        textViewTitle3Point3.setText(Html.fromHtml(highlightedText3Point3), TextView.BufferType.SPANNABLE);
        textViewTitle3Point4.setText(Html.fromHtml(highlightedText3Point4), TextView.BufferType.SPANNABLE);
        textViewTitle4.setText(Html.fromHtml(highlightedText4), TextView.BufferType.SPANNABLE);
        textViewTitle4Point1.setText(Html.fromHtml(highlightedText4Point1), TextView.BufferType.SPANNABLE);
        textViewTitle4Point2.setText(Html.fromHtml(highlightedText4Point2), TextView.BufferType.SPANNABLE);
        textViewTitle4Point3.setText(Html.fromHtml(highlightedText4Point3), TextView.BufferType.SPANNABLE);
    }
}

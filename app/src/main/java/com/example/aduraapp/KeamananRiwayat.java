package com.example.aduraapp;

import android.net.Uri;

public class KeamananRiwayat {

    private String nextIdLaporan; // Tambahkan properti idLaporan

    private String namapelapor;

    private String nomorpelapor;
    private String tanggalkejadian;
    private String imageUrl;
    private String keterangan;
    private String lokasikejadian;

    private Uri imageUri;

    // Default constructor required for Firebase
    public KeamananRiwayat() {
    }

    public String getNamapelapor() {
        return namapelapor;
    }

    public void setNamapelapor(String namapelapor) {
        this.namapelapor = namapelapor;
    }

    public String getNomorpelapor() {
        return nomorpelapor;
    }

    public void setNomorpelapor(String nomorpelapor) {
        this.nomorpelapor = nomorpelapor;
    }

    public String getTanggalkejadian() {
        return tanggalkejadian;
    }

    public void setTanggalkejadian(String tanggalkejadian) {
        this.tanggalkejadian = tanggalkejadian;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getLokasikejadian() {
        return lokasikejadian;
    }

    public void setLokasikejadian(String lokasikejadian) {
        this.lokasikejadian = lokasikejadian;
    }

    public String getnextIdLaporan() {
        return nextIdLaporan;
    }

    public void setIdLaporan(String idLaporan) {
        this.nextIdLaporan = nextIdLaporan;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

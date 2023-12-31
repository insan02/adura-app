package com.example.aduraapp.models;

public class LaporanUpdate {
    private String namapelapor;
    private String nomorpelapor;
    private String tanggalkejadian;
    private String lokasikejadian;
    private String keterangan;
    private String imageUrl;
    private String status;


    private Double latitude;
    private Double longitude;

    // Empty constructor (required for Firebase)
    public LaporanUpdate() {
    }

    public LaporanUpdate(String namapelapor, String nomorpelapor, String tanggalkejadian, String lokasikejadian, String keterangan, String imageUrl,Double latitude, Double longitude, String status) {
        this.namapelapor = namapelapor;
        this.nomorpelapor = nomorpelapor;
        this.tanggalkejadian = tanggalkejadian;
        this.lokasikejadian = lokasikejadian;
        this.keterangan = keterangan;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
    }

    // Getter and setter methods
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

    public String getLokasikejadian() {
        return lokasikejadian;
    }

    public void setLokasikejadian(String lokasikejadian) {
        this.lokasikejadian = lokasikejadian;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}


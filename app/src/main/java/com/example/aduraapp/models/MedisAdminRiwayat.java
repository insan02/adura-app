package com.example.aduraapp.models;

public class MedisAdminRiwayat {


    private String namapelapor;
    private String nextIdLaporan;
    private String idUser;

    private String nomorpelapor;
    private String tanggalkejadian;
    private String imageUrl;
    private String imageName;

    private String keterangan;
    private String lokasikejadian;

    // Default constructor required for Firebase
    public MedisAdminRiwayat() {
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
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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

    public void setnextIdLaporan(String nextIdLaporan) {
        this.nextIdLaporan = nextIdLaporan;
    }
    public String getidUser() {
        return idUser;
    }

    public void setidUser(String idUser) {
        this.idUser = idUser;
    }

}

package com.example.aduraapp.models;

public class Menu {
    String gambar;
    String namaMenu;

    public Menu(int ic_keamanan, String keamanan){}

    public Menu(String gambar, String namaMenu) {
        this.gambar = gambar;
        this.namaMenu = namaMenu;
    }
    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }
}

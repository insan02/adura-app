package com.example.aduraapp.models;

public class Menu {
    int gambarId;
    String namaMenu;
    int backgroundColor; // Tambahkan atribut warna latar belakang

    public Menu(int gambarId, String namaMenu, int backgroundColor) {
        this.gambarId = gambarId;
        this.namaMenu = namaMenu;
        this.backgroundColor = backgroundColor;
    }

    public int getGambarId() {
        return gambarId;
    }

    public void setGambarId(int gambarId) {
        this.gambarId = gambarId;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}

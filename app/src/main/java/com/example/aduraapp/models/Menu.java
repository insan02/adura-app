package com.example.aduraapp.models;

public class Menu {
    int gambarId;
    String namaMenu;

    public Menu(int gambarId, String namaMenu) {
        this.gambarId = gambarId;
        this.namaMenu = namaMenu;
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
}

package com.example.aduraapp;

public class MedisRiwayat {
    private int id;
    private String name;
    private String phone;
    private String location;
    private String keterangan;
    public MedisRiwayat(int id, String name, String phone, String location, String keterangan) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return "MedisRiwayat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                ", keterangan='" + keterangan + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}

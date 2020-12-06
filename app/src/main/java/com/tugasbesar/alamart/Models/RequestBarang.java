package com.tugasbesar.alamart.Models;

public class RequestBarang {

    private int id;
    private String nama;
    private String keterangan;
    private int harga;

    public RequestBarang(int id, String nama, String keterangan, int harga) {
        this.id = id;
        this.nama = nama;
        this.keterangan = keterangan;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}

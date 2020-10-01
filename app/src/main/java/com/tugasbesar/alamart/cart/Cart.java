package com.tugasbesar.alamart.cart;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Cart implements Serializable {

    // TODO:
    // Buat model entity cart. Isinya int id, int kode item, int jumlah barang, int total harga
    // Buat model entity cart. Isinya int id, int kode item, int jumlah barang, int total harga
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Kode_item")
    public int KodeItem;

    @ColumnInfo(name = "Jumlah_barang")
    public int jumlahBarang;

    @ColumnInfo(name = "Total_Harga")
    public int TotalHarga;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKodeItem() {
        return KodeItem;
    }

    public void setKodeItem(int KodeItem) {
        this.KodeItem = KodeItem;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public int getTotalHarga() {
        return TotalHarga;
    }

    public void setTotalHarga(int TotalHarga) {
        this.TotalHarga = TotalHarga;
    }

}

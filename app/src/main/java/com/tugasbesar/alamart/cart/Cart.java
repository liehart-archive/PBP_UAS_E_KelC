package com.tugasbesar.alamart.cart;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
public class Cart implements Serializable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "nama_barang")
    public String nama_barang;

    @ColumnInfo(name = "jumlah_barang")
    public int jumlahBarang;

    @ColumnInfo(name = "total_harga")
    public double TotalHarga;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getTotalHarga() {
        return TotalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        TotalHarga = totalHarga;
    }

    @BindingAdapter("showPrice")
    public static void setPriceString(TextView view, double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("in-ID"));
        numberFormat.setMaximumFractionDigits(0);
        view.setText(numberFormat.format(price));
    }
}


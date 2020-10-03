package com.tugasbesar.alamart.cart;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tugasbesar.alamart.item.Item;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
public class Cart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_barang")
    public String id_barang;

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

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
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

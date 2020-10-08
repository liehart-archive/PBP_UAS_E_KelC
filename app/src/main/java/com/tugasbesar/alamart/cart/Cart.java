package com.tugasbesar.alamart.cart;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;
import com.tugasbesar.alamart.R;
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

    @ColumnInfo(name = "nama_barang")
    public String nama_barang;

    @ColumnInfo(name = "image_url")
    public String image_url;

    @ColumnInfo(name = "jumlah_barang")
    public int jumlahBarang;

    @ColumnInfo(name = "total_harga")
    public double TotalHarga;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

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

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, String imageURL) {
        if(imageURL != null) {
            Glide.with(view.getContext())
                    .load(imageURL)
                    .into(view);
        } else {
            view.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_broken_image_24));
        }
    }
}

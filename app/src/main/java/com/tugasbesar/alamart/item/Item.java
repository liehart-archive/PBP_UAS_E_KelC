package com.tugasbesar.alamart.item;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.tugasbesar.alamart.R;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class Item {

    public String uuid;
    public String name;
    public String description;
    public String maker;
    public List<String> category;
    public int size;
    public double price;
    public int stock;
    public int discount;
    public int terjual;
    public List<String> image;

    public Item() {
    }

    public Item(int terjual, int size, double price, String name, String description, int discount, List<String> category, int stock, String uuid, List<String> image) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.maker = maker;
        this.category = category;
        this.size = size;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        this.terjual = 0;
        this.image = image;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addCategory(String s) {
        this.category.add(s);
    }

    public void removeCategory(String s) {
        this.category.remove(s);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTerjual() {
        return terjual;
    }

    public void setTerjual(int terjual) {
        this.terjual = terjual;
    }

    public void addTerjual(int jumlah) {
        this.terjual += jumlah;
    }

    @BindingAdapter("showPrice")
    public static void setPriceString(TextView view, double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("in-ID"));
        numberFormat.setMaximumFractionDigits(0);
        view.setText(numberFormat.format(price));
    }

    @BindingAdapter("showDiscount")
    public static void setDiscount(LinearLayout view, int discount) {
        if (discount > 0) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter({"discount", "price"})
    public static void setDiscountedPrice(TextView view, int discount, double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("in-ID"));
        numberFormat.setMaximumFractionDigits(0);
        if (discount > 0) {
            view.setText(numberFormat.format(price - (price * discount / 100)));
        } else {
            view.setText(numberFormat.format(price));
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView view, String imageURL) {
        if (imageURL != null) {
            Glide.with(view.getContext())
                    .load(imageURL)
                    .into(view);
        } else {
            view.setImageDrawable(view.getContext().getDrawable(R.drawable.ic_baseline_broken_image_24));
        }
    }
}

package com.tugasbesar.alamart.item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.tugasbesar.alamart.R;

import java.text.NumberFormat;

public class ItemDetail extends AppCompatActivity {

    Item item;
    TextView itemTitle, itemPrice, itemDiscount, itemDiscountPrice, itemDescription;
    NumberFormat numberFormat;
    private MaterialButton btnFav, btnShare;
    private boolean btnFavState;
    private String priceString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeWithToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setMaximumFractionDigits(0);

        btnFav = findViewById(R.id.btn_item_favourite);
        btnShare = findViewById(R.id.btn_share);

        itemTitle = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemDiscount = findViewById(R.id.itemDiscount);
        itemDiscountPrice = findViewById(R.id.itemDiscountPrice);
        itemDescription = findViewById(R.id.itemDescription);

        String strItem = getIntent().getStringExtra("objItem");
        Gson gson = new Gson();
        item = gson.fromJson(strItem, Item.class);

        double price = item.price;

        if(item.discount > 0) {
            itemDiscount.setVisibility(View.VISIBLE);
            itemDiscountPrice.setVisibility(View.VISIBLE);
            price -= ((price * item.discount) / 100);
            itemDiscount.setText(Integer.toString(item.discount) + " %");
            itemDiscountPrice.setText(numberFormat.format(item.price));
        }

        priceString = numberFormat.format(price);

        itemTitle.setText(item.name);
        itemPrice.setText(numberFormat.format(price));
        itemDescription.setText(item.description);
        actionBar.setTitle(item.name);

        btnFavState = false;

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFavState = !btnFavState;
                if (btnFavState) {
                    btnFav.setIconTint(ColorStateList.valueOf(Color.parseColor("#E3242B")));
                    Toast.makeText(ItemDetail.this, "Ditambahkan sebagai favorit", Toast.LENGTH_SHORT).show();
                } else {
                    btnFav.setIconTint(ColorStateList.valueOf(Color.parseColor("#aaaaaa")));
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String share = "Cek sekarang juga! " + item.name + " hanya " + priceString + " di Alamart.";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, share);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
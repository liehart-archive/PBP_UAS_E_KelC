package com.tugasbesar.alamart.item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartAppDatabase;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;

import java.text.NumberFormat;
import java.util.Locale;

public class ItemDetail extends AppCompatActivity {

    Item item;
    TextView itemTitle, itemPrice, itemDiscount, itemDiscountPrice, itemDescription;
    NumberFormat numberFormat;
    private MaterialButton btnFav, btnShare, btnAdd;
    private boolean btnFavState;
    private String priceString;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeWithToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        numberFormat = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("in-ID"));
        numberFormat.setMaximumFractionDigits(0);

        btnFav = findViewById(R.id.btn_item_favourite);
        btnShare = findViewById(R.id.btn_share);
        btnAdd = findViewById(R.id.btn_add);

        itemTitle = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemDiscount = findViewById(R.id.itemDiscount);
        itemDiscountPrice = findViewById(R.id.itemDiscountPrice);
        itemDescription = findViewById(R.id.itemDescription);
        imageView = findViewById(R.id.imageContent);

        String strItem = getIntent().getStringExtra("objItem");
        Gson gson = new Gson();
        item = gson.fromJson(strItem, Item.class);

        double price = item.price;

        if (item.discount > 0) {
            itemDiscount.setVisibility(View.VISIBLE);
            itemDiscountPrice.setVisibility(View.VISIBLE);
            price -= ((price * item.discount) / 100);
            itemDiscount.setText(Integer.toString(item.discount) + " %");
            itemDiscountPrice.setText(numberFormat.format(item.price));
        }

        if (item.image != null) {
            Glide.with(imageView.getContext())
                    .load(item.image.get(0))
                    .into(imageView);
        } else {
            imageView.setImageDrawable(imageView.getContext().getDrawable(R.drawable.ic_baseline_broken_image_24));
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add2Cart(item);
            }
        });
    }

    private void add2Cart(Item i) {

        final Item item = i;

        class AddCart extends AsyncTask<Void, Void, Void> {

            public String message;

            @Override
            protected Void doInBackground(Void... voids) {
                CartDao client = CartDatabaseClient.getInstance(getApplicationContext()).getDatabase().cartDao();
                Cart daoCart = client.getCartByUUID(item.uuid);

                if (daoCart == null) {
                    Cart cart = new Cart();
                    cart.setNama_barang(item.name);
                    cart.setId_barang(item.uuid);
                    if (item.image != null) {
                        cart.setImage_url(item.image.get(0));
                    }
                    cart.setJumlahBarang(1);
                    cart.setTotalHarga((item.price - (item.price * item.discount / 100)) * 1);
                    client.insert(cart);
                    message = "Produk berhasil ditambahkan";
                } else {
                    daoCart.setJumlahBarang(daoCart.jumlahBarang++);
                    daoCart.setTotalHarga(item.price * daoCart.jumlahBarang++);
                    client.update(daoCart);
                    message = "Kuantitas produk berhasil ditambah";
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        AddCart addCart = new AddCart();
        addCart.execute();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
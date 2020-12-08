package com.tugasbesar.alamart.barang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.tugasbesar.alamart.MainActivity;
import com.tugasbesar.alamart.Models.RequestBarang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.requestitem.EditRequestItemActivity;

public class ShowBarangFragment extends DialogFragment {

    private TextView twNama, twHarga, twDeskripsi;
    private Button btnAdd;
    private ImageButton ibClose;

    public ShowBarangFragment() {
    }


    public static ShowBarangFragment newInstance() {
        ShowBarangFragment fragment = new ShowBarangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_barang, container, false);

        btnAdd = v.findViewById(R.id.btnAdd);

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        twNama = v.findViewById(R.id.twNama);
        twHarga = v.findViewById(R.id.twHarga);
        twDeskripsi = v.findViewById(R.id.twDeskripsi);

        String requestBarangJson = getArguments().getString("item");
        Gson gson = new Gson();
        Barang barang = gson.fromJson(requestBarangJson, Barang.class);

        twNama.setText(barang.getNama());
        twHarga.setText(String.valueOf(barang.getHarga()));
        twDeskripsi.setText(barang.getDeskripsi());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add2Cart(barang);
            }
        });

        return v;
    }

    private void add2Cart(Barang i) {

        final Barang item = i;

        class AddCart extends AsyncTask<Void, Void, Void> {

            public String message;

            @Override
            protected Void doInBackground(Void... voids) {
                CartDao client = CartDatabaseClient.getInstance(getActivity().getApplicationContext()).getDatabase().cartDao();
                Cart daoCart = client.getCartByUUID(item.getId());

                if (daoCart == null) {
                    Cart cart = new Cart();
                    cart.setNama_barang(item.getNama());
                    cart.setId(item.getId());
                    cart.setJumlahBarang(1);
                    cart.setTotalHarga(item.getHarga());
                    client.insert(cart);
                    message = "Produk berhasil ditambahkan";
                } else {
                    daoCart.setJumlahBarang(daoCart.jumlahBarang++);
                    daoCart.setTotalHarga(item.getHarga() * daoCart.jumlahBarang++);
                    client.update(daoCart);
                    message = "Kuantitas produk berhasil ditambah";
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                dismiss();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        AddCart addCart = new AddCart();
        addCart.execute();
    }
}
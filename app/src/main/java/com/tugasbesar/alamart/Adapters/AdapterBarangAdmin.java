package com.tugasbesar.alamart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.barang.AdminShowBarangFragment;
import com.tugasbesar.alamart.barang.Barang;
import com.tugasbesar.alamart.barang.ShowBarangFragment;
import com.tugasbesar.alamart.requestitem.EditRequestItemActivity;
import com.tugasbesar.alamart.requestitem.ShowRequestBarangFragment;
import com.tugasbesar.alamart.views.auth.AdminActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterBarangAdmin extends RecyclerView.Adapter<AdapterBarangAdmin.AdapterBarangAdminViewHolder> {
    private final List<Barang> list;
    private final Context context;
    private View view;

    public AdapterBarangAdmin(Context context, List<Barang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterBarangAdmin.AdapterBarangAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_barang, parent, false);
        return new AdapterBarangAdmin.AdapterBarangAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarangAdminViewHolder holder, int position) {
        final Barang barang = list.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");

        holder.txtNama.setText(barang.getNama());
        holder.txtDeskripsi.setText(barang.getDeskripsi());
        holder.txtHarga.setText("Rp " + formatter.format(barang.getHarga()));

        String image = barang.getImage();

        if (image.length() > 0) {
            Glide.with(context).load(image).into(holder.ivBarang);
        } else {
            holder.ivBarang.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_broken_image_24));
        }
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminActivity myActivity = (AdminActivity)context;

                FragmentManager manager = myActivity.getSupportFragmentManager();
                AdminShowBarangFragment dialog = new AdminShowBarangFragment();
                dialog.show(manager, "dialog");

                Gson gson = new Gson();
                String requestItemJson = gson.toJson(barang);

                Bundle args = new Bundle();
                args.putString("item", requestItemJson);
                dialog.setArguments(args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdapterBarangAdminViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtNama;
        private final TextView txtDeskripsi;
        private final TextView txtHarga;
        private final ImageView ivBarang;
        private final MaterialCardView mParent;

        public AdapterBarangAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.tvItemName);
            txtDeskripsi = itemView.findViewById(R.id.tvItemDescription);
            txtHarga = itemView.findViewById(R.id.tvItemPrice);
            ivBarang = itemView.findViewById(R.id.ivBarang);
            mParent = itemView.findViewById(R.id.parentAdapter);
        }
    }
}

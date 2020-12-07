package com.tugasbesar.alamart.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tugasbesar.alamart.Models.Barang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;
import com.tugasbesar.alamart.barang.DetailBarangFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.adapterBarangViewHolder> {
    private List<Barang> list;
    private Context context;
    private View view;

    public AdapterBarang(Context context, List<Barang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterBarang.adapterBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_barang, parent, false);
        return new AdapterBarang.adapterBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterBarangViewHolder holder, int position) {
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
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                DetailBarangFragment dialog = new DetailBarangFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", String.valueOf(barang.getId()));
                dialog.setArguments(args);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class adapterBarangViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtDeskripsi, txtHarga;
        private ImageView ivBarang;
        private LinearLayout mParent;

        public adapterBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.tvItemName);
            txtDeskripsi = itemView.findViewById(R.id.tvItemDescription);
            txtHarga = itemView.findViewById(R.id.tvItemPrice);
            ivBarang = itemView.findViewById(R.id.ivBarang);
            mParent = itemView.findViewById(R.id.linearLayout);
        }
    }
}

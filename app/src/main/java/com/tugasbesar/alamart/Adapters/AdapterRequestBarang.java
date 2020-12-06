package com.tugasbesar.alamart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tugasbesar.alamart.Models.RequestBarang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterRequestBarang extends RecyclerView.Adapter<AdapterRequestBarang.adapterRequestBarangViewHolder> {
    private List<RequestBarang> list;
    private Context context;
    private View view;

    public AdapterRequestBarang(Context context, List<RequestBarang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterRequestBarang.adapterRequestBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_request_barang, parent, false);
        return new AdapterRequestBarang.adapterRequestBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterRequestBarangViewHolder holder, int position) {
        final RequestBarang RequestBarang = list.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");

        holder.txtNama.setText(RequestBarang.getNama());
        holder.txtDeskripsi.setText(RequestBarang.getKeterangan());
        holder.txtHarga.setText("Rp " + formatter.format(RequestBarang.getHarga()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class adapterRequestBarangViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama, txtDeskripsi, txtHarga;
        private ImageView ivRequestBarang;

        public adapterRequestBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.tvItemName);
            txtDeskripsi = itemView.findViewById(R.id.tvItemDescription);
            txtHarga = itemView.findViewById(R.id.tvItemPrice);
        }
    }
}

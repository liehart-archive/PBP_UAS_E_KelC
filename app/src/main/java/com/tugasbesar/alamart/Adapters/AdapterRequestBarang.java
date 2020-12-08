package com.tugasbesar.alamart.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.tugasbesar.alamart.Models.RequestBarang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.requestitem.ShowRequestBarangFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterRequestBarang extends RecyclerView.Adapter<AdapterRequestBarang.adapterRequestBarangViewHolder> {
    private final List<RequestBarang> list;
    private final Context context;
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
        holder.txtHarga.setText("Perkiraan harga Rp " + formatter.format(RequestBarang.getHarga()));
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                ShowRequestBarangFragment dialog = new ShowRequestBarangFragment();
                dialog.show(manager, "dialog");

                Bundle args = new Bundle();
                args.putString("id", String.valueOf(RequestBarang.getId()));
                dialog.setArguments(args);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class adapterRequestBarangViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtNama;
        private final TextView txtDeskripsi;
        private final TextView txtHarga;
        private final MaterialCardView mParent;

        public adapterRequestBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.tvItemName);
            txtDeskripsi = itemView.findViewById(R.id.tvItemDescription);
            txtHarga = itemView.findViewById(R.id.tvItemPrice);
            mParent = itemView.findViewById(R.id.parentAdapter);
        }
    }
}

package com.tugasbesar.alamart.barang;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.tugasbesar.alamart.Models.Barang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.UserResponse;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailBarangFragment extends DialogFragment {

    private TextView txtNama, txtDeskripsi, txtHarga;
    private ImageView ivBarang;
    private String sIdBarang;
    private String sNama;
    private String sDeskripsi;
    private int sHarga;
    private ImageButton ibClose;
    private ProgressDialog progressDialog;
    private Button btnDelete, btnEdit;
    private List<Barang> list;

    public static DetailBarangFragment newInstance() { return new DetailBarangFragment(); }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_barang, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        txtNama = v.findViewById(R.id.tvItemName);
        txtDeskripsi = v.findViewById(R.id.tvItemDescription);
        txtHarga = v.findViewById(R.id.tvItemPrice);
        ivBarang = v.findViewById(R.id.ivBarang);

        btnDelete = v.findViewById(R.id.btnDelete);
        btnEdit = v.findViewById(R.id.btnEdit);

        sIdBarang= getArguments().getString("id", "");
        loadUserById(sIdBarang);

        return v;
    }

    private void loadUserById(String id) {
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserResponse> add = apiService.getBarangById(id, "data");

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                sNama = response.body().getBarang().get(0).getNama();
                sDeskripsi = response.body().getBarang().get(0).getDeskripsi();
                sHarga = response.body().getBarang().get(0).getHarga();

                txtNama.setText(sNama);
                txtDeskripsi.setText(sDeskripsi);
                txtHarga.setText(sHarga);

                progressDialog.dismiss();

                list = response.body().getBarang();
                final Barang barang = list.get(0);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteBarang(sIdBarang);
                    }
                });

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), EditBarang.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", list.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void deleteBarang(final String sIdBarang) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Are you sure, You wanted to make decision");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
                        Call<UserResponse> call = apiService.deleteBarang(sIdBarang);

                        call.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                Toast.makeText(getContext(), "User berhasil dihapus", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
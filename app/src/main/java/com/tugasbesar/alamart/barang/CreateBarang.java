package com.tugasbesar.alamart.barang;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBarang extends AppCompatActivity {
    private ImageButton ibBack;
    private EditText etNama, etDeskripsi, etHarga;
    private MaterialButton btnCancel, btnCreate;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_barang);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        etNama = findViewById(R.id.etNama);
        etHarga = findViewById(R.id.etHarga);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        btnCancel = findViewById(R.id.btnCancel);
        btnCreate = findViewById(R.id.btnUpdate);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNama.getText().toString().isEmpty()) {
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                } else if (etDeskripsi.getText().toString().isEmpty()) {
                    etDeskripsi.setError("Isikan dengan benar");
                    etDeskripsi.requestFocus();
                } else if (etHarga.getText().toString().isEmpty()) {
                    etHarga.setError("Isikan dengan benar");
                    etHarga.requestFocus();
                } else {
                    progressDialog.show();
                    saveBarang();
                }
            }
        });
    }

    private void saveBarang() {
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserResponse> add = apiService.createBarang(etNama.getText().toString(), etHarga.getText().toString(), etDeskripsi.getText().toString());

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Toast.makeText(CreateBarang.this, "Berhasil menambah barang", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(CreateBarang.this, "Gagal menambah barang", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}

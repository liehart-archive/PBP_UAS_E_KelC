package com.tugasbesar.alamart.requestitem;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AddRequestItemActivity extends AppCompatActivity {

    private ImageButton ibBack;
    private EditText etNama, etHarga, etKeterangan;
    private MaterialButton btnCancel, btnEdit;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request_item);

        progressDialog = new ProgressDialog(this);

        ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        etNama = findViewById(R.id.etNama);
        etHarga = findViewById(R.id.etHarga);
        etKeterangan = findViewById(R.id.etKetrangan);

        btnCancel = findViewById(R.id.btnCancel);
        btnEdit = findViewById(R.id.btnEdit);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNama.getText().toString().isEmpty()) {
                    etNama.setError("Isikan dengan benar");
                    etNama.requestFocus();
                } else if (etHarga.getText().toString().isEmpty()) {
                    etHarga.setError("Isikan dengan benar");
                    etHarga.requestFocus();
                } else if (etKeterangan.getText().toString().isEmpty()) {
                    etKeterangan.setError("Isikan dengan benar", null);
                    etKeterangan.requestFocus();
                } else {
                    progressDialog.show();
                    saveBarang();
                }


            }
        });
    }

    private void saveBarang() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AlamartAPI.REQUEST_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("success").equals("true")) {
                        Toast.makeText(AddRequestItemActivity.this, obj.getString("message"), Toast.LENGTH_SHORT)
                                .show();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    try {
                        JSONObject obj = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        System.out.println(obj.toString());
                        if (obj.getString("success").equals("true"))
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", etNama.getText().toString());
                params.put("keterangan", etKeterangan.getText().toString());
                params.put("harga", etHarga.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}

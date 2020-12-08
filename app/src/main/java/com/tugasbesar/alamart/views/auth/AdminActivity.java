package com.tugasbesar.alamart.views.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tugasbesar.alamart.Adapters.AdapterBarang;
import com.tugasbesar.alamart.Adapters.AdapterBarangAdmin;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;
import com.tugasbesar.alamart.barang.AddBarangActivity;
import com.tugasbesar.alamart.barang.Barang;
import com.tugasbesar.alamart.requestitem.AddRequestItemActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private AdapterBarangAdmin adapter;
    private RecyclerView recyclerView;
    private List<Barang> list = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        loadBarang();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AddBarangActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }



    private void loadBarang() {
        setAdapter();
        getBarang();
    }

    private void getBarang() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data barang");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, AlamartAPI.BARANG_API
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!list.isEmpty())
                        list.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String id = jsonObject.optString("id");
                        String nama = jsonObject.optString("nama");
                        String deskripsi = jsonObject.optString("deskripsi");
                        String harga = jsonObject.optString("harga");
                        String image = jsonObject.optString("image");

                        Barang barang = new Barang(Integer.parseInt(id), nama, deskripsi, Integer.parseInt(harga), image);

                        list.add(barang);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }

    private void setAdapter() {

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new AdapterBarangAdmin(AdminActivity.this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}
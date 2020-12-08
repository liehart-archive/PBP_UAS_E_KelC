package com.tugasbesar.alamart.barang;

import android.app.ProgressDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tugasbesar.alamart.MainActivity;
import com.tugasbesar.alamart.Models.RequestBarang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.requestitem.EditRequestItemActivity;

import org.json.JSONObject;

import java.util.function.UnaryOperator;

public class AdminShowBarangFragment extends DialogFragment {

    private TextView twNama, twHarga, twDeskripsi;
    private Button btnEdit, btnDelete;
    private ImageButton ibClose;

    public AdminShowBarangFragment() {
    }


    public static AdminShowBarangFragment newInstance() {
        AdminShowBarangFragment fragment = new AdminShowBarangFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_show_barang, container, false);

        btnEdit = v.findViewById(R.id.btnEdit);
        btnDelete = v.findViewById(R.id.btnDelte);

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus Data")
                        .setMessage("Yakin untuk menghapus " + barang.getNama() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteUser(barang.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String requestItemJson = gson.toJson(barang);
                Intent i = new Intent(getActivity(), EditBarangActivity.class);
                i.putExtra("item", requestItemJson);
                startActivityForResult(i, 0);
            }
        });

        return v;
    }

    private void deleteUser(int id) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data RequestBarang");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE, AlamartAPI.BARANG_API + "/" + id
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();

                dismiss();

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }
}
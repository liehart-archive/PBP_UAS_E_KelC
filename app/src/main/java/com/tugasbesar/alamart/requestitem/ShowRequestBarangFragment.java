package com.tugasbesar.alamart.requestitem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tugasbesar.alamart.Models.RequestBarang;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ShowRequestBarangFragment extends DialogFragment {

    private TextView twNama, twHarga, twKeterangan;
    private Button btnEdit, btnDelete;
    private RequestBarang requestBarang;
    private String sIdRequestItem;
    private ImageButton ibClose;

    public ShowRequestBarangFragment() {
    }

    public static ShowRequestBarangFragment newInstance() {
        return new ShowRequestBarangFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_request_barang, container, false);

        btnDelete = v.findViewById(R.id.btnDelete);
        btnEdit = v.findViewById(R.id.btnEdit);

        btnEdit.setEnabled(false);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus Data")
                        .setMessage("Yakin untuk menghapus " + requestBarang.getNama() + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteUser(requestBarang.getId());
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String requestItemJson = gson.toJson(requestBarang);
                Intent i = new Intent(getActivity(), EditRequestItemActivity.class);
                i.putExtra("item", requestItemJson);
                startActivityForResult(i, 0);
            }
        });

        ibClose = v.findViewById(R.id.ibClose);
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        twNama = v.findViewById(R.id.twNama);
        twHarga = v.findViewById(R.id.twHarga);
        twKeterangan = v.findViewById(R.id.twKeterangn);

        sIdRequestItem = getArguments().getString("id", "");
        loadRequestBarang(sIdRequestItem);

        return v;

    }

    private void loadRequestBarang(String sIdRequestItem) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data RequestBarang");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, AlamartAPI.REQUEST_API + "/" + sIdRequestItem
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = response.getJSONObject("data");

                    String id = jsonObject.optString("id");
                    String nama = jsonObject.optString("nama");
                    String deskripsi = jsonObject.optString("keterangan");
                    String harga = jsonObject.optString("harga");

                    requestBarang = new RequestBarang(Integer.parseInt(id), nama, deskripsi, Integer.parseInt(harga));

                    NumberFormat formatter = new DecimalFormat("#,###");
                    twNama.setText(nama);
                    twHarga.setText("Rp " + formatter.format(Integer.parseInt(harga)));
                    twKeterangan.setText(deskripsi);
                    btnEdit.setEnabled(true);

                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
                Toast.makeText(getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
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

    private void deleteUser(int id) {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data RequestBarang");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.DELETE, AlamartAPI.REQUEST_API + "/" + sIdRequestItem
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
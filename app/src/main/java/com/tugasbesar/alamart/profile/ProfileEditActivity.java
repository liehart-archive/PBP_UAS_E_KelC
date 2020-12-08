package com.tugasbesar.alamart.profile;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tugasbesar.alamart.Models.User;
import com.tugasbesar.alamart.Models.UserDatabaseClient;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private Button btnCancel, btnSave;
    private TextInputEditText nameTxt, phoneTxt, alamatTxt, dobTxt;
    private TextInputLayout nameLayout, phoneLayout, alamatLayout, dobLayout;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        nameLayout = findViewById(R.id.inputNameLayout);
        phoneLayout = findViewById(R.id.inputNomorTeleponLayout);
        alamatLayout = findViewById(R.id.inputAddressLayout);
        dobLayout = findViewById(R.id.inputTanggalLahirLayout);

        nameTxt = findViewById(R.id.inputName);
        phoneTxt = findViewById(R.id.inputNomorTelepon);
        alamatTxt = findViewById(R.id.inputAddress);
        dobTxt = findViewById(R.id.inputTanggalLahir);

        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        get();

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Masukkan tanggal lahir kamu");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        dobTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "material_date_picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                if ((long) selection > Instant.now().getEpochSecond() * 1000) {
                    dobLayout.setError("Kamu tidak bisa lahir di masa depan");
                } else {
                    dobLayout.setError(null);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dobTxt.setText(simpleDateFormat.format(selection));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBeforeSave();
            }
        });

    }

    private void checkBeforeSave() {
        if (dobTxt.getText().length() <= 0) {
            dobLayout.setError("Tanggal lahir tidak boleh kosong");
        } else {
            dobLayout.setError(null);
        }

        if (nameTxt.getText().length() <= 0) {
            nameTxt.setError("Nama tidak boleh kosong");
        } else {
            nameTxt.setError(null);
        }

        if (alamatTxt.getText().length() <= 0) {
            alamatTxt.setError("Alamat tidak boleh kosong");
        } else {
            alamatTxt.setError(null);
        }

        if (phoneTxt.getText().length() <= 0) {
            phoneTxt.setError("Nomor telepon tidak boleh kosong");
        } else {
            phoneTxt.setError(null);
        }

        if (dobTxt.getText().length() > 0
                && nameTxt.getText().length() > 0
                && alamatTxt.getText().length() > 0
                && phoneTxt.getText().length() > 0) {
            save();
        }
    }

    private void save() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, AlamartAPI.AUTH_API + "/" + user.id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.getString("success").equals("true")) {
                        saveToDatabase();
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    try {
                        JSONObject obj = new JSONObject(new String(error.networkResponse.data, StandardCharsets.UTF_8));
                        if (obj.getString("success").equals("true"))
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nameTxt.getText().toString());
                params.put("telephone", phoneTxt.getText().toString());
                params.put("address", alamatTxt.getText().toString());
                params.put("dob", dobTxt.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void saveToDatabase() {
        user.setName(nameTxt.getText().toString());
        user.setAddress(alamatTxt.getText().toString());
        user.setDob(dobTxt.getText().toString());
        user.setTelephone(phoneTxt.getText().toString());

        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                return UserDatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .update(user);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
            }
        }

        SaveUser saveUser = new SaveUser();
        saveUser.execute();
    }

    private void get() {

        class GetUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                return UserDatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .getUser();
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                try {
                    setUser(user);
                    if (user.name == "") {
                        nameTxt.setText("-");
                    } else {
                        nameTxt.setText(user.name);
                    }

                    if (user.telephone == "") {
                        phoneTxt.setText("-");
                    } else {
                        phoneTxt.setText(user.telephone);
                    }

                    if (user.address == "") {
                        alamatTxt.setText("-");
                    } else {
                        alamatTxt.setText(user.address);
                    }

                    if (user.dob == "") {
                        dobTxt.setText("-");
                    } else {
                        dobTxt.setText(user.dob);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        GetUser getUser = new GetUser();
        getUser.execute();
    }

    private void setUser(User user) {
        this.user = user;
    }

}
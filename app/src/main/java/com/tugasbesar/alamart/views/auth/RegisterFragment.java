package com.tugasbesar.alamart.views.auth;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import com.google.gson.JsonIOException;
import com.google.type.DateTime;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class RegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private ConstraintLayout registerForm;
    private LinearLayout registerSuccess;

    private Button btnRegister, btnLogin;
    private TextView back2login;
    private TextInputEditText inputEmail, inputPassword, inputTanggalLahir, inputNama, inputAlamat, inputPhone;
    private TextInputLayout inputEmailLayout, inputPasswordLayout, inputTanggalLahirLayout, inputNamaLayout, inputAlamatLayout, inputPhoneLayout;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogin = view.findViewById(R.id.btnLogin);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputTanggalLahir = view.findViewById(R.id.inputTanggalLahir);
        inputNama = view.findViewById(R.id.inputName);
        inputPhone = view.findViewById(R.id.inputNomorTelepon);
        inputAlamat = view.findViewById(R.id.inputAddress);
        inputEmailLayout = view.findViewById(R.id.inputEmailLayout);
        inputPasswordLayout = view.findViewById(R.id.inputPasswordLayout);
        inputTanggalLahirLayout = view.findViewById(R.id.inputTanggalLahirLayout);
        inputNamaLayout = view.findViewById(R.id.inputNameLayout);
        inputAlamatLayout = view.findViewById(R.id.inputAddressLayout);
        inputPhoneLayout = view.findViewById(R.id.inputNomorTeleponLayout);
        back2login = view.findViewById(R.id.linkLogin);

        registerForm = view.findViewById(R.id.registerForm);
        registerSuccess = view.findViewById(R.id.registerSuccess);

        inputTanggalLahir.setKeyListener(null);

        btnRegister.setEnabled(true);

        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Masukkan tanggal lahir kamu");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        inputTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(), "material_date_picker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                if ((long) selection > Instant.now().getEpochSecond() * 1000) {
                    inputTanggalLahirLayout.setError("Kamu tidak bisa lahir di masa depan");
                } else {
                    inputTanggalLahirLayout.setError(null);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                inputTanggalLahir.setText(simpleDateFormat.format(selection));
                refreshButton();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        back2login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, loginFragment)
                        .commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, loginFragment)
                        .commit();
            }
        });

        inputEmail.addTextChangedListener(new TextValidator(inputEmailLayout, inputEmail) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Email tidak boleh kosong.");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    textInputLayout.setError("Email tidak valid");
                } else {
                    textInputLayout.setError(null);
                    refreshButton();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                refreshButton();
            }
        });

        inputPassword.addTextChangedListener(new TextValidator(inputPasswordLayout, inputPassword) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Password tidak boleh kosong.");
                } else if (s.length() < 6) {
                    textInputLayout.setError("Pasword harus memiliki minimal 6 karakter");
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                refreshButton();
            }
        });

        inputNama.addTextChangedListener(new TextValidator(inputNamaLayout, inputNama) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Nama tidak boleh kosong.");
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                refreshButton();
            }
        });

        inputAlamat.addTextChangedListener(new TextValidator(inputAlamatLayout, inputAlamat) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Alamat tidak boleh kosong.");
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                refreshButton();
            }
        });

        inputPhone.addTextChangedListener(new TextValidator(inputPhoneLayout, inputPhone) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Nomor telepon tidak boleh kosong.");
                } else {
                    textInputLayout.setError(null);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                refreshButton();
            }
        });
    }

    private void refreshButton() {

        btnRegister.setEnabled(false);

        if (Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().toLowerCase()).matches()
                && inputPassword.getText().toString().length() >= 6
                && inputNama.getText().toString().length() > 0
                && inputAlamat.getText().toString().length() > 0
                && inputPhone.getText().toString().length() > 0
                && inputTanggalLahirLayout.getError() == null
                && inputTanggalLahir.getText().toString().length() > 0) {
            btnRegister.setEnabled(true);
        }
    }

    private void add() {

        final String email = inputEmail.getText().toString().toLowerCase();
        final String password = inputPassword.getText().toString();

        btnRegister.setEnabled(false);
        inputPasswordLayout.setError(null);
        inputEmailLayout.setError(null);
        inputTanggalLahirLayout.setError(null);
        inputAlamatLayout.setError(null);
        inputTanggalLahirLayout.setError(null);
        inputEmail.clearFocus();
        inputPassword.clearFocus();
        inputTanggalLahir.clearFocus();
        inputNama.clearFocus();
        inputAlamat.clearFocus();
        btnRegister.setEnabled(false);
        btnRegister.setText("Loading...");

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AlamartAPI.AUTH_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("success").equals("true"))
                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                btnRegister.setEnabled(true);
                btnRegister.setText("Buat Akun");

                registerForm.setVisibility(View.GONE);
                registerSuccess.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    try {
                        JSONObject obj = new JSONObject(new String(error.networkResponse.data, "utf-8"));
                        if(obj.getString("success").equals("true"))
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        System.out.println(obj.toString());
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                btnRegister.setEnabled(true);
                btnRegister.setText("Buat Akun");
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", inputNama.getText().toString());
                params.put("email", inputEmail.getText().toString());
                params.put("password", inputPassword.getText().toString());
                params.put("telephone", inputPhone.getText().toString());
                params.put("address", inputAlamat.getText().toString());
                params.put("dob", inputTanggalLahir.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
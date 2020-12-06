package com.tugasbesar.alamart.views.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.tugasbesar.alamart.MainActivity;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.AlamartAPI;
import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.UserResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private Button btnLogin;
    private TextView back2register;
    private TextInputEditText inputEmail, inputPassword;
    private TextInputLayout inputEmailLayout, inputPasswordLayout;
    private SharedPreferences sharedPreferences;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        btnLogin = view.findViewById(R.id.btnLogin);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputEmailLayout = view.findViewById(R.id.inputEmailLayout);
        inputPasswordLayout = view.findViewById(R.id.inputPasswordLayout);
        back2register = view.findViewById(R.id.linkSignup);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInput();
            }
        });

        back2register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment registerFragment = new RegisterFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_layout, registerFragment)
                        .commit();
            }
        });
     }

    private void validateInput() {
        if (inputPassword.getText().toString().isEmpty()) {
            inputPassword.setError("Password tidak boleh kosong");
        }
        if (inputEmail.getText().toString().isEmpty()) {
            inputEmail.setError("Email tidak boleh kosong");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().toLowerCase()).matches()) {
            inputEmail.setError("Email tidak valid");
        }
        if (inputPassword.getText().toString().length() < 6) {
            inputPassword.setError("Password minimal 6 karakter");
        }
        if (Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().toLowerCase()).matches()
                && inputPassword.getText().toString().length() >= 6) {
            login();
        }
    }

    private void login() {

        btnLogin.setEnabled(false);
        final String email = inputEmail.getText().toString().toLowerCase();
        final String password = inputPassword.getText().toString();

        inputPasswordLayout.setError(null);
        inputEmailLayout.setError(null);
        inputEmail.clearFocus();
        inputPassword.clearFocus();

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AlamartAPI.LOGIN_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("success").equals("true")) {
                        Toast.makeText(getActivity().getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                        ((AuthActivity) getActivity()).addNotification(
                                "Login Berhasil",
                                "Selamat anda telah berhasil login."
                        );

                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        sharedPreferences = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("value", obj.getJSONObject("data").getString("token"));
                        editor.apply();

                        startActivity(intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                btnLogin.setEnabled(true);
                btnLogin.setText("Login");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    try {
                        JSONObject obj = new JSONObject(new String(error.networkResponse.data, "utf-8"));
                        if(obj.getString("message").equals("Unauthorised")) {
                            inputEmailLayout.setError("Email tidak terdaftar");
                            Toast.makeText(getContext(), "Email tidak terdaftar", Toast.LENGTH_SHORT).show();
                        } else if (obj.getString("message").equals("Password Incorrect")) {
                            inputPasswordLayout.setError("Password Salah");
                            Toast.makeText(getContext(), "Password Salah", Toast.LENGTH_SHORT).show();
                        } else if (obj.getString("message").equals("Validation error")) {
                            inputEmailLayout.setError("Email tidak valid");
                            Toast.makeText(getContext(), "Email tidak valid", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                btnLogin.setEnabled(true);
                btnLogin.setText("Login");
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", inputEmail.getText().toString());
                params.put("password", inputPassword.getText().toString());

                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
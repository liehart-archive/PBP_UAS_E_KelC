package com.tugasbesar.alamart.views.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserResponse> add = apiService.loginUser(email, password);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    String message = response.body().getMessage();
                    if (message.equals("Unauthorised")) {
                        message = "User tidak terdaftar";
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
                btnLogin.setEnabled(true);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(getActivity(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                btnLogin.setEnabled(true);
            }
        });
    }
}
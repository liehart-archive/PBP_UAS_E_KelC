package com.tugasbesar.alamart.auth;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import com.tugasbesar.alamart.R;

public class RegisterFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private Button btnRegister;
    private TextView back2login;
    private TextInputEditText inputEmail, inputPassword;
    private TextInputLayout inputEmailLayout, inputPasswordLayout;

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
        inputEmail = view.findViewById(R.id.inputEmail);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputEmailLayout = view.findViewById(R.id.inputEmailLayout);
        inputPasswordLayout = view.findViewById(R.id.inputPasswordLayout);
        back2login = view.findViewById(R.id.linkLogin);

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
    }

    private void refreshButton() {

        btnRegister.setEnabled(false);

        if (Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().toLowerCase()).matches()
                && inputPassword.getText().toString().length() >= 6) {
            btnRegister.setEnabled(true);
        }
    }

    private void add() {

        final String email = inputEmail.getText().toString().toLowerCase();
        final String password = inputPassword.getText().toString();

        btnRegister.setEnabled(false);
        inputPasswordLayout.setError(null);
        inputEmailLayout.setError(null);
        inputEmail.clearFocus();
        inputPassword.clearFocus();
        btnRegister.setEnabled(false);
        btnRegister.setText("Loading...");

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.hide(RegisterFragment.this).commit();
                            Toast.makeText(getActivity().getApplicationContext(), "Register success", Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                inputEmailLayout.setError("Email telah digunakan");
                                inputEmail.requestFocus();
                                imm.showSoftInput(inputEmail, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    }
                });

    }
}
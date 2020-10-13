package com.tugasbesar.alamart.auth;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import com.tugasbesar.alamart.MainActivity;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartDao;
import com.tugasbesar.alamart.cart.CartDatabaseClient;
import com.tugasbesar.alamart.item.Item;
import com.tugasbesar.alamart.profile.Profile;
import com.tugasbesar.alamart.profile.ProfileDao;
import com.tugasbesar.alamart.profile.ProfileDatabaseClient;

public class LoginFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private Button btnLogin;
    private TextView back2register;
    private TextInputEditText inputEmail, inputPassword;
    private TextInputLayout inputEmailLayout, inputPasswordLayout;

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
                login();
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

        btnLogin.setEnabled(false);

        if (Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().toLowerCase()).matches()
                && inputPassword.getText().toString().length() >= 6) {
            btnLogin.setEnabled(true);
        }
    }


    private void login() {

        final String email = inputEmail.getText().toString().toLowerCase();
        final String password = inputPassword.getText().toString();

        inputPasswordLayout.setError(null);
        inputEmailLayout.setError(null);
        inputEmail.clearFocus();
        inputPassword.clearFocus();
        btnLogin.setEnabled(false);
        btnLogin.setText("Loading...");

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ((AuthActivity) getActivity()).addNotification(
                                    "Login Berhasil",
                                    "Selamat anda telah berhasil login."
                            );
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(getActivity().getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                            Profile profile = new Profile();
                            profile.setEmail(firebaseAuth.getCurrentUser().getEmail());
                            setProfile(profile);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                inputEmailLayout.setError("Email tidak ditemukan");
                                inputEmail.requestFocus();
                                imm.showSoftInput(inputEmail, InputMethodManager.SHOW_IMPLICIT);
                            }
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                inputPasswordLayout.setError("Password salah");
                                inputPassword.requestFocus();
                                imm.showSoftInput(inputPassword, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }
                    }
                });

    }

    private void setProfile(final Profile profile) {

        class SetProfile extends AsyncTask<Void, Void, Void> {

            public String message;

            @Override
            protected Void doInBackground(Void... voids) {
                ProfileDao profileDao = ProfileDatabaseClient.getInstance(getContext()).getDatabase().profileDao();
                profileDao.insert(profile);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SetProfile setProfile = new SetProfile();
        setProfile.execute();
    }
}
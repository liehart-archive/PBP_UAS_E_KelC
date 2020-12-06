package com.tugasbesar.alamart.unittest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tugasbesar.alamart.R;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private Button btnLogin;
    private TextInputEditText txtEmail, txtPassword;
    private TextInputLayout txtEmailLayout, txtPasswordLayout;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.inputEmail);
        txtPassword = findViewById(R.id.inputPassword);
        txtEmailLayout = findViewById(R.id.inputEmailLayout);
        txtPasswordLayout = findViewById(R.id.inputPasswordLayout);

        presenter = new LoginPresenter(this, new LoginService());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLoginClicked();
            }
        });
    }

    @Override
    public String getEmail() {
        return txtEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return txtPassword.getText().toString();
    }

    @Override
    public void showEmailError(String error) {
        txtEmailLayout.setError(error);
    }

    @Override
    public void showPasswordError(String error) {
        txtPasswordLayout.setError(error);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorResponse(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}

package com.tugasbesar.alamart.unittest;

import android.content.Context;

public interface LoginView {

    String getEmail();

    void showEmailError(String message);

    String getPassword();

    void showPasswordError(String message);

    void startMainActivity();

    void showLoginError(String message);

    void showErrorResponse(String message);

}
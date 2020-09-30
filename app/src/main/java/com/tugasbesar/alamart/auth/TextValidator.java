package com.tugasbesar.alamart.auth;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public abstract class TextValidator implements TextWatcher {

    private final TextInputEditText textInputEditText;
    private final TextInputLayout textInputLayout;

    protected TextValidator(TextInputLayout textInputLayout, TextInputEditText textInputEditText) {
        this.textInputEditText = textInputEditText;
        this.textInputLayout = textInputLayout;
    }

    public abstract void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s);

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String content = textInputEditText.getText().toString();
        validator(textInputLayout, textInputEditText, content);
    }
}
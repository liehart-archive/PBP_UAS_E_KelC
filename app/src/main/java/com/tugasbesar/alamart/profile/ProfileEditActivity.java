package com.tugasbesar.alamart.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.auth.TextValidator;
import com.tugasbesar.alamart.cart.Cart;
import com.tugasbesar.alamart.cart.CartDatabaseClient;

import java.util.List;

public class ProfileEditActivity extends AppCompatActivity {

    private Button btnCancel, btnSave;
    private TextInputEditText nameTxt, emailTxt, phoneTxt, alamatTxt;
    private TextInputLayout nameLayout, emailLayout, phoneLayout, alamatLayout;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        nameLayout = findViewById(R.id.inputNamaLayout);
        emailLayout = findViewById(R.id.inputEmailLayout);
        phoneLayout = findViewById(R.id.inputPhoneLayout);
        alamatLayout = findViewById(R.id.inputAddressLayout);

        nameTxt = findViewById(R.id.inputNama);
        emailTxt = findViewById(R.id.inputEmail);
        phoneTxt = findViewById(R.id.inputPhone);
        alamatTxt = findViewById(R.id.inputAddress);

        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getProfile();


        nameTxt.addTextChangedListener(new TextValidator(nameLayout, nameTxt) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Nama tidak boleh kosong.");
                } else {
                    textInputLayout.setError(null);
                }
                refreshButton();
            }
        });

        alamatTxt.addTextChangedListener(new TextValidator(alamatLayout, alamatTxt) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Alamat tidak boleh kosong.");
                } else {
                    textInputLayout.setError(null);
                }
                refreshButton();
            }
        });

        phoneTxt.addTextChangedListener(new TextValidator(phoneLayout, phoneTxt) {
            @Override
            public void validator(TextInputLayout textInputLayout, TextInputEditText textInputEditText, String s) {
                if (s.trim().isEmpty()) {
                    textInputLayout.setError("Telephone tidak boleh kosong.");
                } else if (s.length() > 13 || s.length() < 10) {
                    textInputLayout.setError("Nomor telepon tidak valid.");
                } else {
                    textInputLayout.setError(null);
                }
                refreshButton();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata(profile);
            }
        });

    }

    private void savedata(Profile profile) {

        profile.setAddress(alamatTxt.getText().toString());
        profile.setName(nameTxt.getText().toString());
        profile.setTelephone(phoneTxt.getText().toString());
        updateProfile(profile);

    }

    private void updateProfile(Profile profile) {
        class UpdateProfile extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                ProfileDatabaseClient.getInstance(getApplicationContext().getApplicationContext()).getDatabase()
                        .profileDao()
                        .update(profile);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext().getApplicationContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        UpdateProfile update = new UpdateProfile();
        update.execute();
    }

    private void refreshButton() {

        btnSave.setEnabled(false);

        if (!nameTxt.getText().toString().trim().isEmpty() && !phoneTxt.getText().toString().trim().isEmpty()
        && !alamatTxt.getText().toString().trim().isEmpty() && phoneTxt.getText().toString().length() <= 13 &&
    phoneTxt.getText().toString().length() >= 10) {
            btnSave.setEnabled(true);
        }
    }

    private void getProfile() {
        class GetProfile extends AsyncTask<Void, Void, Profile> {

            @Override
            protected Profile doInBackground(Void... voids) {
                Profile profile = ProfileDatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .profileDao()
                        .getProfile();

                return profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                super.onPostExecute(profile);
                setProfile(profile);
                try {

                    if (profile.name == "") {
                        nameTxt.setText("-");
                    } else {
                        nameTxt.setText(profile.name);
                    }

                    if (profile.email == "") {
                        emailTxt.setText("-");
                    } else {
                        emailTxt.setText(profile.email);
                    }

                    if (profile.telephone == "") {
                        phoneTxt.setText("-");
                    } else {
                        phoneTxt.setText(profile.telephone);
                    }

                    if (profile.address == "") {
                        alamatTxt.setText("-");
                    } else {
                        alamatTxt.setText(profile.address);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        GetProfile getProfile = new GetProfile();
        getProfile.execute();
    }

    private void setProfile(Profile profile) {
        this.profile = profile;
    }

}
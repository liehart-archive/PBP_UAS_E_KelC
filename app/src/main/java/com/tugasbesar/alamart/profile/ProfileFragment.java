package com.tugasbesar.alamart.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.auth.User;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.utilities.CameraActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private MaterialToolbar toolbar;
    private Profile profile;
    private TextView emailField, nameField, telephoneField, addressField;
    CircleImageView circleImageView;

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        toolbar = view.findViewById(R.id.profileTopAppBar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailField = view.findViewById(R.id.emailUser);
        nameField = view.findViewById(R.id.nameUser);
        telephoneField = view.findViewById(R.id.phoneUser);
        addressField = view.findViewById(R.id.placeUser);
        circleImageView = view.findViewById(R.id.profile_image);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_setting:
                        Intent intent = new Intent(getActivity(), UserSettingActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
        getProfile();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProfile() {
        class GetProfile extends AsyncTask<Void, Void, Profile> {

            @Override
            protected Profile doInBackground(Void... voids) {
                Profile profile = ProfileDatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .profileDao()
                        .getProfile();

                return profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                super.onPostExecute(profile);
                try {

                    if (profile.name == "") {
                        nameField.setText("-");
                    } else {
                        nameField.setText(profile.name);
                    }

                    if (profile.email == "") {
                        emailField.setText("-");
                    } else {
                        emailField.setText(profile.email);
                    }

                    if (profile.telephone == "") {
                        telephoneField.setText("-");
                    } else {
                        telephoneField.setText(profile.telephone);
                    }

                    System.out.println(profile.address);

                    if (profile.address == "") {
                        addressField.setText("-");
                    } else {
                        addressField.setText(profile.address);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        GetProfile getProfile = new GetProfile();
        getProfile.execute();
    }
}
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

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.auth.User;
import com.tugasbesar.alamart.R;

public class ProfileFragment extends Fragment {

    private MaterialToolbar toolbar;
    private Profile profile;
    private TextView emailField;

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
                setProfileHere(profile);
            }
        }

        GetProfile getProfile = new GetProfile();
        getProfile.execute();
    }

    private void setProfileHere(Profile profile) {
        this.profile = profile;
        emailField.setText(profile.email);
    }

}
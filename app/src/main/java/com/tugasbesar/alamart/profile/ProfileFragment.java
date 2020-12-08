package com.tugasbesar.alamart.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.tugasbesar.alamart.Models.User;
import com.tugasbesar.alamart.Models.UserDatabaseClient;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.utilities.CameraActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView circleImageView;
    int LAUNCH_PROFILE = 1;
    private MaterialToolbar toolbar;
    private Profile profile;
    private User user;
    private TextView emailField, nameField, telephoneField, addressField, dobField;

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
        dobField = view.findViewById(R.id.dobUser);
        circleImageView = view.findViewById(R.id.profile_image);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.profile_setting:
                        Intent intent = new Intent(getActivity(), UserSettingActivity.class);
                        startActivityForResult(intent, LAUNCH_PROFILE);
                        break;
                }
                return false;
            }
        });

        get();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    private void get() {

        class GetUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                return UserDatabaseClient
                        .getInstance(getContext())
                        .getDatabase()
                        .userDao()
                        .getUser();
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                setUser(user);
                try {

                    if (user.name == "") {
                        nameField.setText("-");
                    } else {
                        nameField.setText(user.name);
                    }

                    if (user.email == "") {
                        emailField.setText("-");
                    } else {
                        emailField.setText(user.email);
                    }

                    if (user.telephone == "") {
                        telephoneField.setText("-");
                    } else {
                        telephoneField.setText(user.telephone);
                    }

                    if (user.address == "") {
                        addressField.setText("-");
                    } else {
                        addressField.setText(user.address);
                    }

                    if (user.dob == "") {
                        dobField.setText("-");
                    } else {
                        dobField.setText(user.dob);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        GetUser getUser = new GetUser();
        getUser.execute();
    }

    private void setUser(User user) {
        this.user = user;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_PROFILE) {
            if (resultCode == Activity.RESULT_CANCELED) {
                get();
            }
        }
    }
}
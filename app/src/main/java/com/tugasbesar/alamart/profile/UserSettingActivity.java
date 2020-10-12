package com.tugasbesar.alamart.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.google.firebase.auth.FirebaseAuth;
import com.tugasbesar.alamart.MainActivity;
import com.tugasbesar.alamart.R;
import com.tugasbesar.alamart.auth.AuthActivity;

public class UserSettingActivity extends AppCompatActivity {

    TextView semVer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeWithToolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        semVer = findViewById(R.id.semVer);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle("Pengaturan");
        semVer.setText("Alamart 1.0");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        SharedPreferences sharedPreferences;
        String currentLang;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            PreferenceScreen screen = getPreferenceScreen();
            Preference exit_preference = getPreferenceManager().findPreference(getString(R.string.exit_app));
            sharedPreferences = getContext().getSharedPreferences("locale", MODE_PRIVATE);

            ListPreference listPreference = getPreferenceManager().findPreference("languageKey");
            listPreference.setSummary(sharedPreferences.getString("value", ""));

            switch (sharedPreferences.getString("value", "")) {
                case "in_ID":
                    listPreference.setValue("in_ID");
                    currentLang = "in_ID";
                    break;
                case "en_US":
                    listPreference.setValue("en_US");
                    currentLang = "en_US";
                    break;
            }

            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals(currentLang)) {
                        return false;
                    }
                    currentLang = newValue.toString();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("value", currentLang);
                    editor.commit();
                    Toast.makeText(getContext(), "Bahasa telah diganti", Toast.LENGTH_SHORT).show();
                    ListPreference listPreference = getPreferenceManager().findPreference("languageKey");
                    listPreference.setSummary(sharedPreferences.getString("value", ""));
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    return true;
                }
            });



            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                screen.removePreference(exit_preference);
            } else {
                exit_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), AuthActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(getContext(), "Anda telah keluar", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }
    }
}
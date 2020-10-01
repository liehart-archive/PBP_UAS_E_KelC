package com.tugasbesar.alamart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tugasbesar.alamart.auth.AuthActivity;
import com.tugasbesar.alamart.databinding.ActivityMainBinding;
import com.tugasbesar.alamart.item.Item;
import com.tugasbesar.alamart.item.ItemAdapter;
import com.tugasbesar.alamart.item.ItemList;
import com.tugasbesar.alamart.profile.ProfileFragment;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private ItemAdapter adapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ActivityMainBinding binding;
    BottomNavigationView navigationView;

    SharedPreferences sharedPreferences;

    boolean doubleTapToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("locale", MODE_PRIVATE);
        String locale = sharedPreferences.getString("value", null);
        if(locale == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("value", getResources().getConfiguration().getLocales().get(0).toString());
            editor.commit();
        }

        if (locale.equals("en_US")) {
            setApplicationLocale("en");
        } else {
            setApplicationLocale("id");
        }


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        items = new ItemList().items;

        adapter = new ItemAdapter(MainActivity.this, items);
        binding.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        loadFragment(new HomeFragment());
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.home_page:
                        fragment = new HomeFragment();
                        break;
                    case R.id.search_page:
                        break;
                    case R.id.cart_page:
                        if (user != null) {
                            fragment = new EmptyCartFragment();
                        } else {
                            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.profile_page:
                        fragment = new ProfileFragment();
                        break;
                }
                return loadFragment(fragment);
            }
        });

//        Testing buat cart nanti

//        BadgeDrawable badgeDrawable = navigationView.getOrCreateBadge(R.id.cart_page);
//        badgeDrawable.isVisible();
//        badgeDrawable.setNumber(99);
    }

    @Override
    public void onBackPressed() {
        if (doubleTapToExit) {
            super.onBackPressed();
            return;
        }

        this.doubleTapToExit = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleTapToExit=false;
            }
        }, 2000);
    }

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void setApplicationLocale(String locale) {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(new Locale(locale.toLowerCase()));
        } else {
            config.locale = new Locale(locale.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
    }

}
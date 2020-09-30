package com.tugasbesar.alamart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
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

public class MainActivity extends AppCompatActivity {

    private ArrayList<Item> items;
    private ItemAdapter adapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ActivityMainBinding binding;
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
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
                            fragment = new HomeFragment();
                        } else {
                            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.profile_page:
                        if (user != null) {

                        } else {
//                            Intent intent = new Intent(MainActivity.this, AuthActivity.class);
//                            startActivity(intent);
                            fragment = new ProfileFragment();
                        }
                        break;
                }
                return loadFragment(fragment);
            }
        });

//        Testing buat cart nanti

        BadgeDrawable badgeDrawable = navigationView.getOrCreateBadge(R.id.cart_page);
        badgeDrawable.isVisible();
        badgeDrawable.setNumber(99);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
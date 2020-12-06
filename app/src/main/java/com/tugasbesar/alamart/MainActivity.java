package com.tugasbesar.alamart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tugasbesar.alamart.api.AlamartAPI;
import com.tugasbesar.alamart.Models.User;
import com.tugasbesar.alamart.Models.UserDatabaseClient;
import com.tugasbesar.alamart.views.auth.AuthActivity;
import com.tugasbesar.alamart.map.MapsActivity;
import com.tugasbesar.alamart.profile.ProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private User user;
    BottomNavigationView navigationView;
    Intent intent;
    SharedPreferences sharedPreferences;
    boolean doubleTapToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "Channel 1";
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("promotion")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String mag = "Welcome to Alamart";
                        if (!task.isSuccessful()) {
                            mag = "Failed";
                        }
                        Toast.makeText(MainActivity.this, mag, Toast.LENGTH_SHORT).show();
                    }
                });

        getCurrentUser();

        sharedPreferences = getSharedPreferences("locale", MODE_PRIVATE);
        String locale = sharedPreferences.getString("value", null);
        if (locale == null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("value", getResources().getConfiguration().getLocales().get(0).toString());
            editor.commit();
        }

        if (locale.equals("en_US")) {
            setApplicationLocale("en");
        } else {
            setApplicationLocale("id");
        }

        setContentView(R.layout.activity_main);


        loadFragment(new HomeFragment());
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.home_page:
                        fragment = new HomeFragment();
                        break;
//                    case R.id.search_page:
//                        intent = new Intent(MainActivity.this, SearchActivity.class);
//                        startActivity(intent);
//                        break;
                    case R.id.map_page:
                        intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.cart_page:
                        fragment = new CartFragment();
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

    private void getCurrentUser() {
        sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
        String token = sharedPreferences.getString("value", null);
        if (token == null) {
            intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
        }

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AlamartAPI.AUTH_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getString("success").equals("true")) {
                        JSONObject jUser = obj.getJSONObject("data");
                        Gson gson = new GsonBuilder().create();
                        user = gson.fromJson(jUser.toString(), User.class);
                        save();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void save() {

        final User user = this.user;

        class SaveUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                UserDatabaseClient
                        .getInstance(getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .insert(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveUser saveUser = new SaveUser();
        saveUser.execute();
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
                doubleTapToExit = false;
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
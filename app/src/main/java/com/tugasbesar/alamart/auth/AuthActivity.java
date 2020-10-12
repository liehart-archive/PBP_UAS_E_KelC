package com.tugasbesar.alamart.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.tugasbesar.alamart.R;

public class AuthActivity extends AppCompatActivity {

    private String CHANNEL_ID = "Auth Channel";

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    boolean doubleTapToExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            finish();
        } else {
            createNotificationChannel();

            Fragment loginFragment = new LoginFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_layout, loginFragment)
                    .commit();
        }
    }

    public void goToRegister(View view) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Bundle bundle = new Bundle();
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setArguments(bundle);
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, registerFragment)
                .commit();
    }

    private void createNotificationChannel() {
        CharSequence name = "Auth Channel";
        String description = "This is Auth Channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public void addNotification(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, AuthActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    @Override
    public void onBackPressed() {
        if (doubleTapToExit) {
            finishAffinity();
        }

        this.doubleTapToExit = true;
        Toast.makeText(this, R.string.exit_toast, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTapToExit = false;
            }
        }, 2000);
    }
}
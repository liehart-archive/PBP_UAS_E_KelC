package com.tugasbesar.alamart.unittest;

import android.content.Context;
import android.content.Intent;

import com.tugasbesar.alamart.MainActivity;

public class ActivityUtil {
    private final Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity() {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
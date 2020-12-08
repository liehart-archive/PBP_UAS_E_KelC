package com.tugasbesar.alamart.Models;

import android.content.Context;

import androidx.room.Room;

public class UserDatabaseClient {
    private static UserDatabaseClient databaseClient;
    private final Context context;
    private final UserAppDatabase database;

    private UserDatabaseClient(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, UserAppDatabase.class, "user").build();
    }

    public static synchronized UserDatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new UserDatabaseClient(context);
        }
        return databaseClient;
    }

    public UserAppDatabase getDatabase() {
        return database;
    }
}

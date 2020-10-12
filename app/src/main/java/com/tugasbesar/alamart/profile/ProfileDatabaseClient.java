package com.tugasbesar.alamart.profile;

import android.content.Context;

import androidx.room.Room;

public class ProfileDatabaseClient {
    private Context context;
    private static ProfileDatabaseClient databaseClient;

    private ProfileAppDatabase database;

    private ProfileDatabaseClient(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, ProfileAppDatabase.class, "profile").build();
    }

    public static synchronized ProfileDatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new ProfileDatabaseClient(context);
        }
        return databaseClient;
    }

    public ProfileAppDatabase getDatabase() {
        return database;
    }
}

package com.tugasbesar.alamart.profile;

import android.content.Context;

import androidx.room.Room;

public class ProfileDatabaseClient {
    private static ProfileDatabaseClient databaseClient;
    private final Context context;
    private final ProfileAppDatabase database;

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

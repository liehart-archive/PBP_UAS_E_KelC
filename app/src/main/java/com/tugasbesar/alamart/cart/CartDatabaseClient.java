package com.tugasbesar.alamart.cart;

import android.content.Context;

import androidx.room.Room;

public class CartDatabaseClient {
    private static CartDatabaseClient databaseClient;
    private final Context context;
    private final CartAppDatabase database;

    private CartDatabaseClient(Context context) {
        this.context = context;
        database = Room.databaseBuilder(context, CartAppDatabase.class, "cart").build();
    }

    public static synchronized CartDatabaseClient getInstance(Context context) {
        if (databaseClient == null) {
            databaseClient = new CartDatabaseClient(context);
        }
        return databaseClient;
    }

    public CartAppDatabase getDatabase() {
        return database;
    }
}

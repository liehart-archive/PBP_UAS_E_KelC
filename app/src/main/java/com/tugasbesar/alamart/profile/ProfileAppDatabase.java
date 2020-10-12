package com.tugasbesar.alamart.profile;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class}, version = 1)
public abstract class ProfileAppDatabase extends RoomDatabase {
    public abstract ProfileDao profileDao();
}
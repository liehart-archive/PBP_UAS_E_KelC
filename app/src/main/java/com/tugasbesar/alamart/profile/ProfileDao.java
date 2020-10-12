package com.tugasbesar.alamart.profile;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProfileDao {
    @Query("SELECT * FROM profile")
    Profile getProfile();

    @Insert
    void insert(Profile profile);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Profile profile);

    @Delete
    void delete(Profile profile);
}
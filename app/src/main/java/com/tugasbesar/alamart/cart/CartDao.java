package com.tugasbesar.alamart.cart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE id = :uuid LIMIT 1")
    Cart getCartByUUID(int uuid);

    @Query("SELECT * FROM cart")
    List<Cart> getAll();

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getLiveDAO();

    @Insert
    void insert(Cart cart);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("DELETE FROM cart")
    void deleteAll();
}
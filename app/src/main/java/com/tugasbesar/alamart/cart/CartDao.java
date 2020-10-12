package com.tugasbesar.alamart.cart;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.tugasbesar.alamart.item.Item;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart WHERE id_barang = :uuid LIMIT 1")
    Cart getCartByUUID(String uuid);

    @Query("SELECT * FROM cart")
    LiveData<List<Cart>> getAll();

    @Insert
    void insert(Cart cart);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

}
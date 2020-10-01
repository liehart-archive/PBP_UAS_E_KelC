package com.tugasbesar.alamart.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import com.tugasbesar.alamart.cart.Cart;

@Dao
public interface CartDao{

    @Query("SELECT * FROM Cart")
    List<Cart> getAll();

    @Insert
    void insert(Cart cart);

    @Update
    void update(Cart cart);

    @Delete
    void delete(Cart cart);

}
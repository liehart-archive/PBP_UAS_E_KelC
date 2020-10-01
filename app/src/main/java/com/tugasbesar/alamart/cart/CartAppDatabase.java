package com.tugasbesar.alamart.cart;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.tugasbesar.alamart.cart.Cart;

@Database(entities = {Cart.class}, version = 1)
public abstract class CartAppDatabase extends RoomDatabase{
    public abstract CartDao cartDao();
}

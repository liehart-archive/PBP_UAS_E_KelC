package com.tugasbesar.alamart.cart;

public interface CartClickListener {
    void addQty(Cart cart);

    void minQty(Cart cart);

    void delCart(Cart cart);
}
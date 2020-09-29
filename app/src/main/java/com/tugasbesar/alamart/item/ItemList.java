package com.tugasbesar.alamart.item;

import java.util.ArrayList;

public class ItemList {

    public ArrayList<Item> items;

    public ItemList() {
        items = new ArrayList();
        ArrayList<String> category = new ArrayList();
        category.add("Pocky");
        category.add("Biscuit");
        category.add("Chocolate");
        items.add(new Item(
                "Glico Pocky - Chocolate",
                "A crunchy and delicious biscuit stick covered in a chocolate creamy frosting",
                "Glico",
                category,
                47,
                8800,
                500
                ));
        category.clear();
        category.add("Meiji");
        category.add("Biscuit");
        items.add(new Item(
                "Meiji Hello Panda Double Chocolate Biscuits",
                "Tasty, chocolatey, and adorable.",
                "Meiji",
                category,
                50,
                9700,
                500
        ));
    }
}

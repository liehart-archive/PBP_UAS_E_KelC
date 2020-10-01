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
                26234,
                500,
                99
                ));
        category.clear();
        category.add("Biscuit");
        category.add("Pocky");
        items.add(new Item(
                "Glico Pocky - Matcha Green Tea",
                "Satisfy two Japanese food cravings at the same time with Glico's Pocky matcha green tea flavoured chocolate biscuit sticks. ",
                "Glico",
                category,
                39,
                25284,
                500,
                0
        ));
        category.clear();
        category.add("Pocky");
        category.add("Biscuit");
        items.add(new Item(
                "Meiji Hello Panda Double Chocolate Biscuits",
                "Tasty, chocolatey, and adorable.",
                "Meiji",
                category,
                50,
                19290,
                500,
                25
        ));
        category.clear();
        category.add("Pocky");
        category.add("Biscuit");
        items.add(new Item(
                "Glico Pocky - Almond Crush",
                "A match made in Heaven! This box contains two single-serve packets of premium quality Pocky.",
                "Glico",
                category,
                46,
                87490,
                500,
                0
        ));
        items.add(new Item(
                "Glico Pocky - Almond Crush",
                "A match made in Heaven! This box contains two single-serve packets of premium quality Pocky.",
                "Glico",
                category,
                46,
                87490,
                500,
                0
        ));
    }
}

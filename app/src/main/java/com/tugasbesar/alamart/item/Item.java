package com.tugasbesar.alamart.item;

import java.util.ArrayList;

public class Item {

    public String name;
    public String description;
    public String maker;
    public ArrayList<String> category;
    public int size;
    public int price;
    public int stock;

    public Item(String name, String description, String maker, ArrayList<String> category, int size, int price, int stock) {
        this.name = name;
        this.description = description;
        this.maker = maker;
        this.category = category;
        this.size = size;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void addCategory(String s) {
        this.category.add(s);
    }

    public void removeCategory(String s) {
        this.category.remove(s);
    }
}

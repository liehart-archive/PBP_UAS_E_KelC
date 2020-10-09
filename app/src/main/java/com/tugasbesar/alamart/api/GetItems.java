package com.tugasbesar.alamart.api;

import com.google.gson.annotations.SerializedName;
import com.tugasbesar.alamart.item.Item;

import java.util.List;

public class GetItems {

    @SerializedName("count")
    int count;

    @SerializedName("next")
    String next;

    @SerializedName("previous")
    String previous;

    @SerializedName("results")
    List<Item> itemList;

    public boolean isLast() {
        if (next == null) {
            return true;
        }
        return false;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}

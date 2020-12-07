package com.tugasbesar.alamart.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tugasbesar.alamart.Models.Barang;

import java.util.List;

public class UserResponse {
    @SerializedName("success")
    Boolean success;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    @Expose
    private List<Barang> data = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Barang> getBarang() {
        return data;
    }

    public void setData(List<Barang> users) {
        this.data = data;
    }
}
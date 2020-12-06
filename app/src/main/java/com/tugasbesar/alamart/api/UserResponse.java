package com.tugasbesar.alamart.api;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("success")
    Boolean success;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    String data = null;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
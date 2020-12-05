package com.tugasbesar.alamart.models;

import com.google.type.DateTime;

import java.io.Serializable;

public class User implements Serializable {

    private String name, email, address, telephone, image;
    private DateTime dob;

    public User(String name, String email, String address, String telephone, String image, DateTime dob) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.image = image;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DateTime getDob() {
        return dob;
    }

    public void setDob(DateTime dob) {
        this.dob = dob;
    }
}

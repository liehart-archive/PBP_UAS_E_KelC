package com.tugasbesar.alamart.profile;

import java.io.StringBufferInputStream;
import java.util.Date;
import java.util.List;

public class ProfileDao {
    private String profileImageUrl, nameUser, emailUser, phoneUser, placeUser, token;
    private Date birthdayUser;

    public ProfileDAO(String profileImageUrl, String nameUser, String emailUser, String phoneUser, String placeUser, String token, Date birthdayUser){
        this.profileImageUrl = profileImageUrl;
        this.nameUser = nameUser;
        this.emailUser = emailUser;
        this.phoneUser = phoneUser;
        this.placeUser = placeUser;
        this.token = token;
        this.birthdayUser = birthdayUser;
    }

    public String getProfileImageUrl() { return  profileImageUrl; }

    public String getNameUser() { return nameUser; }

    public String getPhoneUser() { return phoneUser; }

    public String getPlaceUser() { return placeUser; }

    public String getEmailUser() { return emailUser; }

    public String getToken() { return token; }

    public Date getBirthdayUser() { return birthdayUser; }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setPhoneUser(String phoneUser) {
        this.phoneUser = phoneUser;
    }

    public void setPlaceUser(String placeUser) {
        this.placeUser = placeUser;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setBirthdayUser(Date birthdayUser) {
        this.birthdayUser = birthdayUser;
    }
}

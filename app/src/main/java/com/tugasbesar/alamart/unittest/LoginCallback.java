package com.tugasbesar.alamart.unittest;

import com.tugasbesar.alamart.models.User;

public interface LoginCallback {

    void onSuccess(boolean value);
    void onError();

}

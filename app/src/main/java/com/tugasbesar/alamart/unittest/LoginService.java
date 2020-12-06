package com.tugasbesar.alamart.unittest;

import android.widget.Toast;

import com.tugasbesar.alamart.api.ApiClient;
import com.tugasbesar.alamart.api.ApiInterface;
import com.tugasbesar.alamart.api.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginService {
    public void login(final LoginView view, String email, String password, final LoginCallback callback) {
        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<UserResponse> add = apiService.loginUser(email, password);

        add.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.code() == 200) {
                    String message = response.body().getMessage();
                    if (message.equals("Unauthorised")) {
                        message = "User tidak terdaftar";
                        callback.onError();
                        view.showLoginError(message);
                    }
                    callback.onSuccess(true);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showErrorResponse(t.getMessage());
                callback.onError();
            }
        });
    }

    public Boolean getValid(final LoginView view, String email, String password) {
        final Boolean[] bool = new Boolean[1];
        login(view, email, password, new LoginCallback() {
            @Override
            public void onSuccess(boolean value) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
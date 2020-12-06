package com.tugasbesar.alamart.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<UserResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("items")
    Call<GetItems> getItems(
            @Query("page") int pageIndex
    );

}

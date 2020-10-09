package com.tugasbesar.alamart.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("items")
    Call<GetItems> getItems(
            @Query("page") int pageIndex
    );

}

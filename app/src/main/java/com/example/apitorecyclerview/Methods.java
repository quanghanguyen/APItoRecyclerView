package com.example.apitorecyclerview;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("api/user?page2")
    Call<Model> getAllData();

}

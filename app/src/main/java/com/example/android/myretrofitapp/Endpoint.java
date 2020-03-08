package com.example.android.myretrofitapp;

import com.example.android.myretrofitapp.data.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Endpoint {

    @GET("/users")
    Call<List<User>> getAllUsers();

}

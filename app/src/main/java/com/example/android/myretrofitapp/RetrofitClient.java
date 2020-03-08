package com.example.android.myretrofitapp;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    //Define the base URL
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    //create the retrofit instance
    public static Retrofit getRetrofitInstance(){
        if( retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL + "/users/")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
/*

Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .build();

GitHubService service = retrofit.create(GitHubService.class);


Retrofit retrofit = new Retrofit.Builder()
    .baseUrl("https://api.github.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build();

GitHubService service = retrofit.create(GitHubService.class);
 */
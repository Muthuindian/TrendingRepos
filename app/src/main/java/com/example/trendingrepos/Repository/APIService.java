package com.example.trendingrepos.Repository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("repositories")
    Call<String> makeRequest();
}

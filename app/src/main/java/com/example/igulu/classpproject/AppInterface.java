package com.example.igulu.classpproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by igulu on 14.01.2018.
 */

public interface AppInterface {
    @GET("api/v1/json/home/MeaningOfName/{name}")
    Call<Object> Search(@Path("name") String name);
}

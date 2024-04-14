package com.karaoke.karaokebook;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LibraryAPI {
    @GET("/users/{id}")
    Call<User> user(@Path("id") int id);

    @POST("/users")
    Call<User> assignUser(@Body User user);

    @POST("/users/{id}")
    Call<User> addAccount(@Body User user);
}

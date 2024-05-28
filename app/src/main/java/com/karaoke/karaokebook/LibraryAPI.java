package com.karaoke.karaokebook;

import com.karaoke.karaokebook.CellData.BookmarkCellData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LibraryAPI {
    @GET("users/{id}")
    Call<User> user(@Path("id") int id);
    @GET("users")
    Call<User> findAssignedEmail(@Query("email") String email);
    @POST("users")
    Call<User> assignUser(@Body User user);

    @POST("users/{id}")
    Call<User> addAccount(@Body User user);

    @GET("folder/{id}")
    Call<User> getFolderList(@Path("id") int id);

    @GET("bookmark/{id}")
    Call<BookmarkCellData> getBookmarkList(@Path("id") int id);
}

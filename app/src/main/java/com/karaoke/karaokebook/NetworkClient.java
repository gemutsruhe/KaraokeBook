package com.karaoke.karaokebook;

import android.content.Context;

import retrofit2.Retrofit;

public class NetworkClient {
    public static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://localhost")
                    .build();
        }

        return retrofit;
    }
}

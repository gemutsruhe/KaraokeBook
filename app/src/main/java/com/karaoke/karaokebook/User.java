package com.karaoke.karaokebook;

import com.google.gson.annotations.SerializedName;

public class User {
    private static User userInstance = null;
    
    public static User getInstance() {
        if(userInstance == null) {
            userInstance = new User();
        }
        return userInstance;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("nickname")
    private String nickname;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}

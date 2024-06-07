package com.karaoke.karaokebook.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    //private static User userInstance = null;

    User() {
    }
    User(int id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
    /*public static User getInstance(String email, String nickname) {
        if(userInstance == null) {
            userInstance = new User(email, nickname);
        }
        return userInstance;
    }
    public static User getInstance() {
        if(userInstance == null) {
            userInstance = new User();
        }
        return userInstance;
    }*/

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nickname")
    @Expose
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

    @NonNull
    @Override
    public String toString() {
        return "{id: " + id + ", email: " + email + ", nickname: " + nickname + "}";
    }
}

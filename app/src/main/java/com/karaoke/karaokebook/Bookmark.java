package com.karaoke.karaokebook;

import com.google.gson.annotations.SerializedName;

public class Bookmark {
    @SerializedName("song_num")
    private int songNumber;
    @SerializedName("song_name")
    private String songName;
    @SerializedName("pitch")
    private int pitch;

    public int getSongNumber() {
        return songNumber;
    }

    public String getSongName() {
        return songName;
    }

    public int getPitch() {
        return pitch;
    }
}

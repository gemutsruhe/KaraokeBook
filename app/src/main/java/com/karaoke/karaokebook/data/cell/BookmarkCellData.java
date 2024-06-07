package com.karaoke.karaokebook.data.cell;

import com.google.gson.annotations.SerializedName;

public class BookmarkCellData implements CellData {
    @SerializedName("song_num")
    private int songNumber;
    @SerializedName("song_name")
    private String songName;
    @SerializedName("singer")
    private String singer;
    @SerializedName("pitch")
    private int pitch;

    public int getSongNumber() {
        return songNumber;
    }

    public String getSongName() {
        return songName;
    }
    public String getSinger() { return singer; }

    public int getPitch() {
        return pitch;
    }
}

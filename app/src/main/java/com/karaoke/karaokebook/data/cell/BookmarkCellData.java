package com.karaoke.karaokebook.data.cell;

import com.google.gson.annotations.SerializedName;
import com.karaoke.karaokebook.data.model.Bookmark;

public class BookmarkCellData implements CellData {
    @SerializedName("number")
    private int number;
    @SerializedName("name")
    private String name;
    @SerializedName("singer")
    private String singer;
    @SerializedName("pitch")
    private int pitch;


    public BookmarkCellData(Bookmark bookmark) {
        name = bookmark.name;
        singer = bookmark.singer;
        number = Integer.parseInt(bookmark.number);
    }

    public int getSongNumber() {
        return number;
    }

    public String getSongName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public int getPitch() {
        return pitch;
    }
}

package com.karaoke.karaokebook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;

@Entity
public class Bookmark {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int number;
    public String name;
    public String singer;
    @ColumnInfo(defaultValue = "0")
    public int pitch;
    @ColumnInfo(defaultValue = "0")
    public int parent;

    public Bookmark() {

    }

    public Bookmark(BookmarkCellData cellData) {
        number = cellData.getSongNumber();
        name = cellData.getSongName();
        singer = cellData.getSinger();
    }

    public Bookmark(SongCellData cellData) {
        number = cellData.getNumber();
        name = cellData.getTitle();
        singer = cellData.getSinger();
        pitch = cellData.getPitch();
    }
}

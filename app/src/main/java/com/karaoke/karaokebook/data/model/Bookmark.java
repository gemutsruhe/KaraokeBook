package com.karaoke.karaokebook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "number")
    public String number;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "singer")
    public String singer;

}

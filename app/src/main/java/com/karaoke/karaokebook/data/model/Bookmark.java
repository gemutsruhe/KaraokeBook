package com.karaoke.karaokebook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {
    @PrimaryKey
    public int id;

    public String number;
    public String name;
    public String singer;

    @ColumnInfo(defaultValue = "0")
    public int parent;
}

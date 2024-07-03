package com.karaoke.karaokebook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Folder {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "bookmarkNum")
    public int bookmarkNum;
    @ColumnInfo(name = "parent")
    public int parent;
}

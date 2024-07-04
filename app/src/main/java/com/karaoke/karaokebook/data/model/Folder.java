package com.karaoke.karaokebook.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Folder {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    @ColumnInfo(defaultValue = "0")
    public int parent;
}

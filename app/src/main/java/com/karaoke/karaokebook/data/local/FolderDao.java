package com.karaoke.karaokebook.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.karaoke.karaokebook.data.model.Folder;

import java.util.List;

@Dao
public interface FolderDao {
    @Insert
    void insert(Folder folder);

    @Query("SELECT * FROM folder")
    List<Folder> getAll();
}

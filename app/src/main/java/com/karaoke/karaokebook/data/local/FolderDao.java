package com.karaoke.karaokebook.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.karaoke.karaokebook.data.model.Folder;

import java.util.List;

@Dao
public interface FolderDao {
    @Insert
    void insert(Folder folder);

    @Update
    void update(Folder folder);

    @Delete
    void delete(Folder folder);

    @Query("SELECT * FROM folder")
    List<Folder> getAll();

    @Query("SELECT * FROM folder WHERE parent = :parent")
    List<Folder> get(int parent);
}

package com.karaoke.karaokebook.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.karaoke.karaokebook.data.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insert(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);

    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAll();

    @Query("SELECT * FROM bookmark WHERE parent = :parent")
    List<Bookmark> get(int parent);

    @Query("DELETE FROM bookmark WHERE number = :songNumber")
    void delete(int songNumber);

    @Query("UPDATE bookmark SET parent = :parent WHERE number = :number")
    void update(int number, int parent);
}

package com.karaoke.karaokebook.data.cell;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.model.Bookmark;

public class SongCellData implements CellData {
    private final int number;
    private final String title;
    private final String singer;
    private final int pitch;
    private final MutableLiveData<Boolean> bookmark = new MutableLiveData<>(false);
    private final int parent;

    public SongCellData(int number, String title, String singer, boolean bookmark) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmark.postValue(bookmark);
        this.pitch = 0;
        this.parent = -1;
    }

    public SongCellData(int number, String title, String singer, boolean bookmark, Integer pitch) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmark.postValue(bookmark);
        this.pitch = pitch;
        this.parent = -1;
    }

    public SongCellData(Bookmark bookmark) {
        this.number = bookmark.number;
        this.title = bookmark.name;
        this.singer = bookmark.singer;
        this.pitch = bookmark.pitch;
        this.bookmark.postValue(true);
        this.parent = bookmark.parent;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }

    public Integer getPitch() {
        return pitch;
    }

    public boolean isBookmark() {
        return bookmark.getValue();
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark.postValue(bookmark);
    }

    public LiveData<Boolean> getBookmark() {
        return bookmark;
    }
}
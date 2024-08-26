package com.karaoke.karaokebook.data.cell;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.model.Bookmark;

public class SongCellData implements CellData {
    private final int number;
    private final String title;
    private final String singer;
    private final Integer pitch;
    private final MutableLiveData<Boolean> bookmarked = new MutableLiveData<>(false);

    public SongCellData(int number, String title, String singer, boolean bookmarked) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmarked.postValue(bookmarked);
        pitch = 0;
    }

    public SongCellData(int number, String title, String singer, boolean bookmarked, Integer pitch) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmarked.postValue(bookmarked);
        this.pitch = pitch;
    }

    public SongCellData(Bookmark bookmark) {
        this.number = bookmark.number;
        this.title = bookmark.name;
        this.singer = bookmark.singer;
        this.pitch = bookmark.pitch;
        this.bookmarked.postValue(true);
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

    public boolean isBookmarked() {
        return bookmarked.getValue();
    }

    public void setBookmark(boolean bookmark) {
        bookmarked.postValue(bookmark);
    }

    public LiveData<Boolean> getBookmarked() {
        return bookmarked;
    }
}
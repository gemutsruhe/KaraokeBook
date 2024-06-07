package com.karaoke.karaokebook.data.cell;

public class SongCellData implements CellData {
    private String number;
    private String title;
    private String singer;
    private Integer pitch;
    private boolean bookmarked;

    public SongCellData(String number, String title, String singer, boolean bookmarked) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmarked = bookmarked;
        pitch = 0;
    }

    public SongCellData(String number, String title, String singer, boolean bookmarked, Integer pitch) {
        this.number = number;
        this.title = title;
        this.singer = singer;
        this.bookmarked = bookmarked;
        this.pitch = pitch;
    }

    public String getNumber() {
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
        return bookmarked;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmarked = bookmark;
    }
}
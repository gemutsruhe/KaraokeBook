package com.karaoke.karaokebook.bookmark;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;

import java.util.ArrayList;

public class BookmarkFolder {
    private ArrayList<BookmarkCellData> bookmarkCellDataList;
    private String name;

    BookmarkFolder(ArrayList<BookmarkCellData> bookmarkCellDataList) {

    }

    public String getName() {
        return name;
    }

    public void addBookmark(BookmarkCellData bookmarkCellData) {
        bookmarkCellDataList.add(bookmarkCellData);
    }

    public ArrayList<BookmarkCellData> getBookmarkList() {
        return bookmarkCellDataList;
    }
}

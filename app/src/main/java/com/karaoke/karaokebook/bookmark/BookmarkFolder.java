package com.karaoke.karaokebook.bookmark;

import com.karaoke.karaokebook.Bookmark;

import java.util.ArrayList;

public class BookmarkFolder {
    private ArrayList<Bookmark> bookmarkList;
    private String name;

    BookmarkFolder(ArrayList<Bookmark> bookmarkList) {

    }

    public String getName() {
        return name;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarkList.add(bookmark);
    }

    public ArrayList<Bookmark> getBookmarkList() {
        return bookmarkList;
    }
}

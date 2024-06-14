package com.karaoke.karaokebook.data.model;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;

import java.util.ArrayList;

public class BookmarkFolder {
    private String name;
    private int itemNum;

    BookmarkFolder(String name, int itemNum) {
        this.name = name;
        this.itemNum = itemNum;
    }

    public String getName() {
        return name;
    }
    public int getItemNum() {
        return itemNum;
    }
}

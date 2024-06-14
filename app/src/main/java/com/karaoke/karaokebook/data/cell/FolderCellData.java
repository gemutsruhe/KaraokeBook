package com.karaoke.karaokebook.data.cell;

import com.google.gson.annotations.SerializedName;

public class FolderCellData implements CellData {
    @SerializedName("folder_name")
    private String folderName;
    @SerializedName("bookmark_count")
    private int bookmarkCount;

    public FolderCellData(String folderName, int bookmarkCount) {
        this.folderName = folderName;
        this.bookmarkCount = bookmarkCount;
    }
    public String getFolderName() {
        return folderName;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

}

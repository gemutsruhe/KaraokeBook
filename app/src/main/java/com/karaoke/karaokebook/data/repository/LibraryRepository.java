package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

public class LibraryRepository {
    public static LibraryRepository instance;
    private final ListLiveData<FolderCellData> folderList;
    private final ListLiveData<BookmarkCellData> bookmarkList;
    private final MutableLiveData<Integer> folder;

    private LibraryRepository() {
        folderList = new ListLiveData<>();
        bookmarkList = new ListLiveData<>();
        folder = new MutableLiveData<>(0);
    }

    public static LibraryRepository getInstance() {
        if (instance == null) {
            instance = new LibraryRepository();
        }

        return instance;
    }

    public ListLiveData<FolderCellData> getFolderList() {
        return folderList;
    }

    public ListLiveData<BookmarkCellData> getItemList() {
        return bookmarkList;
    }

    public LiveData<Integer> getFolder() {
        return folder;
    }
}

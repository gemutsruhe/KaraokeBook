package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibraryRepository {
    public static LibraryRepository instance;
    private final ListLiveData<FolderCellData> folderList;
    private final ListLiveData<Integer> bookmarkList;
    private final Set<Integer> bookmarkSet;
    private final HashMap<Integer, List<FolderCellData>> folderTree;
    private final HashMap<Integer, List<BookmarkCellData>> bookmarkTree;
    private final MutableLiveData<Integer> folder;
    private final ListLiveData<FolderCellData> crtFolderList;
    private final ListLiveData<BookmarkCellData> crtBookmarkList;

    private LibraryRepository() {

        folderList = new ListLiveData<>();
        bookmarkList = new ListLiveData<>();
        folderTree = new HashMap<>();
        bookmarkTree = new HashMap<>();
        crtFolderList = new ListLiveData<>();
        crtBookmarkList = new ListLiveData<>();
        folder = new MutableLiveData<>(0);
        bookmarkSet = new HashSet<>();
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

    public ListLiveData<Integer> getBookmarkList() {
        return bookmarkList;
    }

    public LiveData<Integer> getFolder() {
        return folder;
    }

    public void setFolder(int folder) {
        this.folder.postValue(folder);
    }

    public HashMap<Integer, List<FolderCellData>> getFolderTree() {
        return folderTree;
    }

    public HashMap<Integer, List<BookmarkCellData>> getBookmarkTree() {
        return bookmarkTree;
    }

    public ListLiveData<FolderCellData> getCrtFolderList() {
        return crtFolderList;
    }

    public ListLiveData<BookmarkCellData> getCrtBookmarkList() {
        return crtBookmarkList;
    }

    public Set<Integer> getBookmarkSet() {
        return bookmarkSet;
    }
}

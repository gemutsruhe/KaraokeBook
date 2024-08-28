package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryRepository {

    private static LibraryRepository instance;
    private final Map<Integer, FolderCellData> folderDataMap;

    private final MutableLiveData<Integer> crtFolder;

    private final HashMap<Integer, List<Integer>> folderTree;
    private final HashMap<Integer, List<Integer>> bookmarkTree;

    private final ListLiveData<Integer> folderList;
    private final ListLiveData<Integer> bookmarkList;

    private final ListLiveData<Integer> crtFolderList;
    private final ListLiveData<Integer> crtBookmarkList;

    private LibraryRepository() {
        folderDataMap = new HashMap<>();
        crtFolder = new MutableLiveData<>(0);

        folderList = new ListLiveData<>();
        bookmarkList = new ListLiveData<>();

        folderTree = new HashMap<>();
        bookmarkTree = new HashMap<>();

        crtFolderList = new ListLiveData<>();
        crtBookmarkList = new ListLiveData<>();
    }

    public static LibraryRepository getInstance() {
        if (instance == null) {
            instance = new LibraryRepository();
        }

        return instance;
    }

    public Map<Integer, FolderCellData> getFolderDataMap() {
        return folderDataMap;
    }

    public ListLiveData<Integer> getFolderList() {
        return folderList;
    }

    public ListLiveData<Integer> getBookmarkList() {
        return bookmarkList;
    }

    public LiveData<Integer> getCrtFolder() {
        return crtFolder;
    }

    public void setFolder(int folder) {
        this.crtFolder.postValue(folder);
    }

    public HashMap<Integer, List<Integer>> getFolderTree() {
        return folderTree;
    }

    public HashMap<Integer, List<Integer>> getBookmarkTree() {
        return bookmarkTree;
    }

    public ListLiveData<Integer> getCrtFolderList() {
        return crtFolderList;
    }

    public ListLiveData<Integer> getCrtBookmarkList() {
        return crtBookmarkList;
    }

    public void setBookmarkList(List<Integer> bookmarkList) {
        this.bookmarkList.clear(false);
        this.bookmarkList.addAll(bookmarkList);
    }
}

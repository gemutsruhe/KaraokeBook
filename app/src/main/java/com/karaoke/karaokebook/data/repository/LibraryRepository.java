package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryRepository {

    private static LibraryRepository instance;
    private final Map<Integer, FolderCellData> folderDataMap;

    private final MutableLiveData<Integer> parentFolder;
    private final MutableLiveData<Integer> crtFolder;

    private final Map<Integer, Set<Integer>> folderTree;
    private final Map<Integer, Set<Integer>> bookmarkTree;

    private final ListLiveData<Integer> folderList;
    private final ListLiveData<Integer> bookmarkList;

    private final ListLiveData<Integer> crtFolderList;
    private final ListLiveData<Integer> crtBookmarkList;

    private LibraryRepository() {
        folderDataMap = new HashMap<>();

        parentFolder = new MutableLiveData<>(-1);
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

    public Map<Integer, Set<Integer>> getFolderTree() {
        return folderTree;
    }

    public Map<Integer, Set<Integer>> getBookmarkTree() {
        return bookmarkTree;
    }

    public ListLiveData<Integer> getCrtFolderList() {
        return crtFolderList;
    }

    public ListLiveData<Integer> getCrtBookmarkList() {
        return crtBookmarkList;
    }

    public LiveData<Integer> getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(int parent) {
        this.parentFolder.postValue(parent);
    }

    public void setBookmarkList(List<Integer> bookmarkList) {
        this.bookmarkList.clear(false);
        this.bookmarkList.addAll(bookmarkList);
    }

    public void addFolderData(FolderCellData data) {
        folderDataMap.remove(data.getId());
        folderDataMap.put(data.getId(), data);
    }

    public void setFolderList(List<Integer> folderList) {
        this.folderList.clear(false);
        this.folderList.addAll(folderList);
    }
}

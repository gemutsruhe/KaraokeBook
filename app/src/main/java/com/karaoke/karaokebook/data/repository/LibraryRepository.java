package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

public class LibraryRepository {
    public static LibraryRepository instance;
    private final ListLiveData<FolderCellData> folderList;
    private final ListLiveData<CellData> crtItemList;
    private LibraryRepository() {
        folderList = new ListLiveData<>();
        crtItemList = new ListLiveData<>();
    }
    public static LibraryRepository getInstance() {
        if(instance == null) {
            instance = new LibraryRepository();
        }

        return instance;
    }

    public ListLiveData<FolderCellData> getFolderList() {
        return folderList;
    }

    public ListLiveData<CellData> getCrtItemList() {
        return crtItemList;
    }
}

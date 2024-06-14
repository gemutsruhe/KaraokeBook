package com.karaoke.karaokebook.viewModel;

import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;

public class LibraryViewModel extends ViewModel {
    ListLiveData<FolderCellData> folderList;
    ListLiveData<CellData> crtItemList;
    public LibraryViewModel() {
        LibraryRepository libraryRepository = LibraryRepository.getInstance();
        folderList = libraryRepository.getFolderList();
        crtItemList = libraryRepository.getCrtItemList();

    }

    public void addFolder(String folderName) {
        FolderCellData data = new FolderCellData(folderName, 0);
        folderList.add(data);
    }
}
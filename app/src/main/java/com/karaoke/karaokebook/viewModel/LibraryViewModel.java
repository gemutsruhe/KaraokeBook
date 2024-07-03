package com.karaoke.karaokebook.viewModel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.Folder;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LibraryViewModel extends ViewModel {
    private AppDatabase appDatabase;
    ListLiveData<FolderCellData> folderList;
    ListLiveData<CellData> crtItemList;
    private final Executor executor = Executors.newSingleThreadExecutor();
    public LibraryViewModel(Context context) {
        LibraryRepository libraryRepository = LibraryRepository.getInstance();
        folderList = libraryRepository.getFolderList();
        crtItemList = libraryRepository.getCrtItemList();

        appDatabase = AppDatabase.getInstance(context);

    }

    private void loadFolders() {
        executor.execute(() -> {
            List<Folder> list = appDatabase.folderDao().getAll();
            //folderList.addAll();
        });
    }

    public void addFolder(String folderName) {
        FolderCellData data = new FolderCellData(folderName, 0);
        folderList.add(data);
    }
}
package com.karaoke.karaokebook.viewModel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.Bookmark;
import com.karaoke.karaokebook.data.model.Folder;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryViewModel extends ViewModel {
    private final AppDatabase appDatabase;
    LibraryRepository libraryRepository;
    ListLiveData<FolderCellData> folderList;
    ListLiveData<BookmarkCellData> songList;
    ArrayList<ArrayList<CellData>> folderItemList;
    MutableLiveData<Integer> crtFolder;
    ArrayList<SortedMap<String, View>> folderViewMap;
    ArrayList<SortedMap<String, View>> bookmarkViewMap;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LibraryViewModel(Context context) {
        libraryRepository = LibraryRepository.getInstance();
        folderList = libraryRepository.getFolderList();
        songList = libraryRepository.getItemList();
        appDatabase = AppDatabase.getInstance(context);
    }

    private void updateFolderList() {
        List<Folder> folderList = appDatabase.folderDao().getAll();
        ArrayList<FolderCellData> folderCellDataList = new ArrayList<>();
        for (Folder folder : folderList) {
            folderCellDataList.add(new FolderCellData(folder));
        }

        libraryRepository.getFolderList().clear(false);
        libraryRepository.getFolderList().addAll(folderCellDataList);
    }

    private void updateBookmarkList() {
        List<Bookmark> bookmarkList = appDatabase.bookmarkDao().getAll();
        ArrayList<BookmarkCellData> bookmarkCellDataList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            bookmarkCellDataList.add(new BookmarkCellData(bookmark));
        }

        libraryRepository.getItemList().clear(false);
        libraryRepository.getItemList().addAll(bookmarkCellDataList);
    }

    public void addFolder(String folderName, int parent) {
        executor.execute(() -> {
            Folder newFolder = new Folder();
            newFolder.name = folderName;
            newFolder.parent = parent;

            appDatabase.folderDao().insert(newFolder);
        });
    }

    public void updateItemList(int folder) {
        executor.execute(() -> {
            updateFolderList();
            updateBookmarkList();
            //appDatabase.folderDao().get(folder);

            //appDatabase.bookmarkDao().get(folder);
        });

    }
}
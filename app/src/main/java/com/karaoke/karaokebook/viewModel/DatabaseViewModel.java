package com.karaoke.karaokebook.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.Bookmark;
import com.karaoke.karaokebook.data.model.Folder;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;

    private final SongRepository songRepository;
    private final LibraryRepository libraryRepository;

    Map<Integer, SongCellData> songDataMap;
    Map<Integer, FolderCellData> folderDataMap;

    LiveData<Integer> crtFolder;
    /*private final ListLiveData<Integer> folderList;*/
    private final ListLiveData<Integer> bookmarkList;

    private final ExecutorService executor;

    public DatabaseViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance();

        songRepository = SongRepository.getInstance();
        libraryRepository = LibraryRepository.getInstance();

        songDataMap = songRepository.getSongDataMap();
        folderDataMap = libraryRepository.getFolderDataMap();

        crtFolder = libraryRepository.getCrtFolder();

        bookmarkList = libraryRepository.getBookmarkList();

        executor = Executors.newSingleThreadExecutor();
    }

    public void getDBFolderList() {
        executor.execute(() -> {
            List<Folder> dbFolderList = appDatabase.folderDao().getAll();

            List<Integer> folderList = new ArrayList<>();
            for (Folder folder : dbFolderList) {
                FolderCellData folderCellData = new FolderCellData(folder);
                libraryRepository.addFolderData(folderCellData);
                folderList.add(folder.id);
            }
            libraryRepository.setFolderList(folderList);
        });
    }

    public void addFolder(String folderName, int parent) {
        executor.execute(() -> {
            Folder newFolder = new Folder();
            newFolder.name = folderName;
            newFolder.parent = parent;

            appDatabase.folderDao().insert(newFolder);
            getDBFolderList();
        });
    }

    public void deleteFolder(int id) {
        executor.execute(() -> {
            appDatabase.folderDao().deleteById(id);
            getDBFolderList();
        });
    }

    public void getDBBookmarkList() {
        executor.execute(() -> {
            List<Bookmark> dbBookmarkList = appDatabase.bookmarkDao().getAll();
            List<Integer> bookmarkList = new ArrayList<>();
            for (Bookmark bookmark : dbBookmarkList) {

                SongCellData songCellData = new SongCellData(bookmark);
                songCellData.setBookmark(true);
                songRepository.addSongData(songCellData);

                bookmarkList.add(bookmark.number);
            }
            libraryRepository.setBookmarkList(bookmarkList);
        });
    }

    public void addBookmark(SongCellData data) {
        executor.execute(() -> {
            appDatabase.bookmarkDao().insert(new Bookmark(data));
            getDBBookmarkList();
        });
    }

    public void deleteBookmark(SongCellData data) {
        executor.execute(() -> {
            appDatabase.bookmarkDao().delete(data.getNumber());
            songRepository.getSongData(data.getNumber()).setBookmark(false);
            getDBBookmarkList();
        });
    }

    public void moveFolder(int child, int parent) {
        executor.execute(() -> {
            appDatabase.folderDao().update(child, parent);
            getDBFolderList();
        });
    }

    public void moveBookmark(int songNumber, int parentFolder) {
        executor.execute(() -> {
            appDatabase.bookmarkDao().update(songNumber, parentFolder);
            getDBBookmarkList();
        });
    }
}

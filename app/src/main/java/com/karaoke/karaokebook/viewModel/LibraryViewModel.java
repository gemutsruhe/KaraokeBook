package com.karaoke.karaokebook.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.Bookmark;
import com.karaoke.karaokebook.data.model.Folder;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryViewModel extends ViewModel {
    private final AppDatabase appDatabase;
    SongRepository songRepository;
    LibraryRepository libraryRepository;
    ListLiveData<FolderCellData> folderList;
    ListLiveData<Integer> bookmarkList;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LibraryViewModel(Context context) {
        songRepository = SongRepository.getInstance();
        libraryRepository = LibraryRepository.getInstance();
        folderList = libraryRepository.getFolderList();
        bookmarkList = libraryRepository.getBookmarkList();
        appDatabase = AppDatabase.getInstance(context);

        getDBFolderList();
    }

    public void getDBFolderList() {
        executor.execute(() -> {
            List<Folder> dbFolderList = appDatabase.folderDao().getAll();
            List<FolderCellData> folderCellDataList = new ArrayList<>();
            for(Folder folder : dbFolderList) {
                FolderCellData folderCellData = new FolderCellData(folder);
                folderCellDataList.add(folderCellData);
            }
            folderList.clear(false);
            folderList.addAll(folderCellDataList);
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

    public void updateCrtFolderList(int crtFolder) {
        if(libraryRepository.getFolderTree().containsKey(crtFolder)) {
            List<FolderCellData> folderCellDataList = libraryRepository.getFolderTree().get(crtFolder);
            libraryRepository.getCrtFolderList().clear(false);
            libraryRepository.getCrtFolderList().addAll(folderCellDataList);
        } else {
            Log.e("TEST", crtFolder + " not Contains");
        }

    }

    public void updateCrtBookmarkList(int crtFolder) {

    }

    public void updateItemList(int folder) {
        executor.execute(() -> {
            updateCrtFolderList(folder);
            updateCrtBookmarkList(folder);
        });
    }

    public void createFolderTree(List<FolderCellData> folderList) {
        libraryRepository.getFolderTree().clear();
        HashMap<Integer, List<FolderCellData>> folderTree = libraryRepository.getFolderTree();
        for(FolderCellData folder : folderList) {
            int parent = folder.getParent();

            if(folderTree.containsKey(parent)) {
                folderTree.get(parent).add(folder);
            } else {
                folderTree.put(parent, new ArrayList<>());
            }
        }
    }

    public void createBookmarkTree(List<BookmarkCellData> bookmarkList) {
        HashMap<Integer, List<BookmarkCellData>> bookmarkTree = libraryRepository.getBookmarkTree();
        for(BookmarkCellData bookmark : bookmarkList) {
            int parent = bookmark.getParent();

            if(bookmarkTree.containsKey(parent)) {
                bookmarkTree.get(parent).add(bookmark);
            } else {
                bookmarkTree.put(parent, new ArrayList<>());
            }
        }
    }

    public void moveFolder(int folder) {
        libraryRepository.setFolder(folder);
    }

    public void getDBBookmarkList() {
        executor.execute(() -> {
            List<Bookmark> dbFolderList = appDatabase.bookmarkDao().getAll();

            List<Integer> bookmarkSongNumberList = new ArrayList<>();
            for(Bookmark bookmark : dbFolderList) {
                SongCellData songCellData = new SongCellData(bookmark);
                songCellData.setBookmark(true);
                songRepository.addSongData(songCellData);

                bookmarkSongNumberList.add(bookmark.number);
            }

            bookmarkList.clear(false);
            bookmarkList.addAll(bookmarkSongNumberList);
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
}
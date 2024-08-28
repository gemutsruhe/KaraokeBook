package com.karaoke.karaokebook.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;

    private final SongRepository songRepository;
    private final LibraryRepository libraryRepository;

    Map<Integer, SongCellData> songDataMap;
    Map<Integer, FolderCellData> folderDataMap;

    LiveData<Integer> crtFolder;
    /*private final ListLiveData<Integer> folderList;*/
    private final ListLiveData<Integer> bookmarkList;

    private final ExecutorService executor;

    public LibraryViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance();

        songRepository = SongRepository.getInstance();
        libraryRepository = LibraryRepository.getInstance();

        songDataMap = songRepository.getSongDataMap();
        folderDataMap = libraryRepository.getFolderDataMap();

        crtFolder = libraryRepository.getCrtFolder();

        bookmarkList = libraryRepository.getBookmarkList();

        executor = Executors.newSingleThreadExecutor();


        //getDBFolderList();
    }




    public void updateCrtFolderList(int crtFolder) {

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
        HashMap<Integer, List<Integer>> folderTree = libraryRepository.getFolderTree();
        for (FolderCellData folder : folderList) {
            int parent = folder.getParent();

            if (folderTree.containsKey(parent)) {
                //folderTree.get(parent).add(folder);
            } else {
                folderTree.put(parent, new ArrayList<>());
            }
        }
    }

    public void createBookmarkTree(List<BookmarkCellData> bookmarkList) {
        Map<Integer, List<Integer>> bookmarkTree = libraryRepository.getBookmarkTree();
        for (BookmarkCellData bookmark : bookmarkList) {
            int parent = bookmark.getParent();

            if (bookmarkTree.containsKey(parent)) {
                bookmarkTree.get(parent).add(bookmark.getId());
            } else {
                bookmarkTree.put(parent, new ArrayList<>());
            }
        }
    }

    public void moveFolder(int folder) {
        libraryRepository.setFolder(folder);
    }



//    public ListLiveData<Integer> getBookmarkList() {
//        return bookmarkList;
//    }

    public Map<Integer, SongCellData> getSongDataMap() {
        return songDataMap;
    }

    public ListLiveData<Integer> getBookmarkList() {
        return bookmarkList;
    }

    public LiveData<Integer> getCrtFolder() {
        return crtFolder;
    }
}
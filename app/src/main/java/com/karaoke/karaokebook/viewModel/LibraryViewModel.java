package com.karaoke.karaokebook.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.local.AppDatabase;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryViewModel extends AndroidViewModel {
    private final AppDatabase appDatabase;

    private final SongRepository songRepository;
    private final LibraryRepository libraryRepository;

    private final Map<Integer, SongCellData> songDataMap;
    private final Map<Integer, FolderCellData> folderDataMap;

    private final ListLiveData<Integer> folderList;
    private final ListLiveData<Integer> bookmarkList;

    private final LiveData<Integer> parentFolder;
    private final LiveData<Integer> crtFolder;
    private final ListLiveData<Integer> crtFolderList;
    private final ListLiveData<Integer> crtBookmarkList;

    private final Map<Integer, Set<Integer>> folderTree;
    private final Map<Integer, Set<Integer>> bookmarkTree;

    private final ExecutorService executor;

    public LibraryViewModel(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getInstance();

        songRepository = SongRepository.getInstance();
        libraryRepository = LibraryRepository.getInstance();

        folderDataMap = libraryRepository.getFolderDataMap();
        songDataMap = songRepository.getSongDataMap();

        folderList = libraryRepository.getFolderList();
        bookmarkList = libraryRepository.getBookmarkList();

        parentFolder = libraryRepository.getParentFolder();
        crtFolder = libraryRepository.getCrtFolder();
        crtFolderList = libraryRepository.getCrtFolderList();
        crtBookmarkList = libraryRepository.getCrtBookmarkList();

        folderTree = libraryRepository.getFolderTree();
        bookmarkTree = libraryRepository.getBookmarkTree();

        executor = Executors.newSingleThreadExecutor();
    }

    public void updateItemList(int folder) {
        executor.execute(() -> {
            updateCrtFolderList(folder);
            updateCrtBookmarkList(folder);
        });
    }

    public void updateCrtFolderList(int crtFolder) {
        if (folderTree.containsKey(crtFolder)) {
            crtFolderList.clear(true);

            List<Integer> list = new ArrayList<>(folderTree.get(crtFolder));
            List<FolderCellData> dataList = new ArrayList<>();
            for(int id : list) {
                dataList.add(folderDataMap.get(id));
            }

            dataList.sort(Comparator.comparing(FolderCellData::getName));
            List<Integer> sorted = new ArrayList<>();
            for(FolderCellData folder: dataList) {
                sorted.add(folder.getId());
            }
            crtFolderList.addAll(sorted);
        }
    }

    public void updateCrtBookmarkList(int crtFolder) {
        crtBookmarkList.clear(true);
        if(bookmarkTree.containsKey(crtFolder)) {
            List<Integer> list = new ArrayList<>(bookmarkTree.get(crtFolder));
            List<SongCellData> dataList = new ArrayList<>();
            for (int songNum : list) {
                dataList.add(songDataMap.get(songNum));
            }
            //dataList.sort(Comparator.comparingInt(SongCellData::getNumber));
            dataList.sort(Comparator.comparing(SongCellData::getTitle));
            List<Integer> sorted = new ArrayList<>();
            for(SongCellData song : dataList) {
                sorted.add(song.getNumber());
            }
            crtBookmarkList.addAll(sorted);
        }
    }

    public void createFolderTree(List<Integer> folderList) {
        folderTree.clear();

        for (Integer folder : folderList) {
            int parent = folderDataMap.get(folder).getParent();

            bookmarkTree.put(folder, new HashSet<>());
            if(!folderTree.containsKey(folder)) {
                folderTree.put(folder, new HashSet<>());
            }

            if (!folderTree.containsKey(parent)) {
                folderTree.put(parent, new HashSet<>());
            }
            folderTree.get(parent).add(folder);
        }

        updateCrtFolderList(crtFolder.getValue());
    }

    public void createBookmarkTree(List<Integer> bookmarkList) {
        bookmarkTree.clear();
        for (Integer bookmark : bookmarkList) {
            int parent = songDataMap.get(bookmark).getParent();

            if (!bookmarkTree.containsKey(parent)) {
                bookmarkTree.put(parent, new HashSet<>());
            }
            bookmarkTree.get(parent).add(bookmark);
        }

        updateCrtBookmarkList(crtFolder.getValue());
    }

    public void moveFolder(int folder) {
        if(folder == 0) {
            libraryRepository.setFolder(0);
            libraryRepository.setParentFolder(-1);
        } else {
            libraryRepository.setFolder(folder);
            libraryRepository.setParentFolder(folderDataMap.get(folder).getParent());
        }
    }

    public Map<Integer, SongCellData> getSongDataMap() {
        return songDataMap;
    }

    public ListLiveData<Integer> getBookmarkList() {
        return bookmarkList;
    }

    public ListLiveData<Integer> getFolderList() {
        return folderList;
    }

    public LiveData<Integer> getCrtFolder() {
        return crtFolder;
    }



    public Map<Integer, FolderCellData> getFolderDataMap() {
        return folderDataMap;
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
}
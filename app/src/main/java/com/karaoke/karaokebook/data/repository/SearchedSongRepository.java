package com.karaoke.karaokebook.data.repository;

import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.cell.SongCellData;

import java.util.List;

public class SearchedSongRepository {
    private static SearchedSongRepository instance;
    private ListLiveData<SongCellData> liveData;

    private SearchedSongRepository() {
        liveData = new ListLiveData<>();
    }

    public static SearchedSongRepository getInstance() {
        if (instance == null) {
            instance = new SearchedSongRepository();
        }

        return instance;
    }

    public ListLiveData<SongCellData> getSongCellDataList() {
        return liveData;
    }

    public void addData(SongCellData songCellData) {
        liveData.add(songCellData);
    }

    public void addDataList(List<SongCellData> songCellDataList) {
        liveData.addAll(songCellDataList);
    }
}

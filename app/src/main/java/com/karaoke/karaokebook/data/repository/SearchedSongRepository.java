package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.List;

public class SearchedSongRepository {
    private static SearchedSongRepository instance;
    private ListLiveData<SongCellData> liveData;
    private MutableLiveData<Boolean> searchState;

    private SearchedSongRepository() {
        liveData = new ListLiveData<>();
        searchState = new MutableLiveData<>(false);
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

    public MutableLiveData<Boolean> getSearchState() {
        return searchState;
    }

    public void addData(SongCellData songCellData) {
        liveData.add(songCellData);
    }

    public void addDataList(List<SongCellData> songCellDataList) {
        liveData.addAll(songCellDataList);
    }
}

package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.List;

public class SearchedSongRepository {
    private static SearchedSongRepository instance;
    private final ListLiveData<Integer> liveData;
    private final MutableLiveData<Boolean> searchState;

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

    public ListLiveData<Integer> getSongNumberList() {
        return liveData;
    }

    public MutableLiveData<Boolean> getSearchState() {
        return searchState;
    }

    public void addData(Integer songNumber) {
        liveData.add(songNumber);
    }

    public void addDataList(List<Integer> songCellDataList) {
        liveData.addAll(songCellDataList);
    }
}

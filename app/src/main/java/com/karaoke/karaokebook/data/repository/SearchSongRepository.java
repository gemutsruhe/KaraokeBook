package com.karaoke.karaokebook.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.List;

public class SearchSongRepository {
    private static SearchSongRepository instance;
    private final ListLiveData<Integer> liveData;
    private final MutableLiveData<Boolean> searchState;

    private SearchSongRepository() {
        liveData = new ListLiveData<>();
        searchState = new MutableLiveData<>(false);
    }

    public static SearchSongRepository getInstance() {
        if (instance == null) {
            instance = new SearchSongRepository();
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

    public Integer getSongNumber(int pos) {
        return liveData.getValue().get(pos);
    }
}

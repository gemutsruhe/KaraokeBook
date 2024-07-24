package com.karaoke.karaokebook.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.remote.SearchSong;
import com.karaoke.karaokebook.data.repository.SearchedSongRepository;

public class SearchedSongViewModel extends ViewModel {
    private SearchedSongRepository searchedSongRepository;
    private ListLiveData<SongCellData> songCellDataList;
    private MutableLiveData<Boolean> searchState;

    public SearchedSongViewModel() {
        searchedSongRepository = SearchedSongRepository.getInstance();
        songCellDataList = searchedSongRepository.getSongCellDataList();
        searchState = searchedSongRepository.getSearchState();
    }

    public ListLiveData<SongCellData> getSongCellDataList() {
        return songCellDataList;
    }

    public MutableLiveData<Boolean> getSearchState() {
        return searchState;
    }

    public void search(String searchType, String natName, String searchText) {
        new Thread(() -> {
            searchState.postValue(true);
            SearchSong.search(searchType, natName, searchText);
        }).start();
    }

    public void stopSearch() {
        searchState.postValue(false);
    }
}

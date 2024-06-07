package com.karaoke.karaokebook.viewModel;

import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.repository.SearchedSongRepository;

import java.util.List;

public class SearchedSongViewModel extends ViewModel {
    private SearchedSongRepository searchedSongRepository;
    private ListLiveData<SongCellData> songCellDataList;

    public SearchedSongViewModel() {
        searchedSongRepository = SearchedSongRepository.getInstance();
        songCellDataList = searchedSongRepository.getSongCellDataList();
    }

    public ListLiveData<SongCellData> getSongCellDataList() {
        return songCellDataList;
    }

    public void addDataList(List<SongCellData> dataList) {
    }
}

package com.karaoke.karaokebook.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.remote.SearchSong;
import com.karaoke.karaokebook.data.repository.SearchSongRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchViewModel extends AndroidViewModel {
    private final SongRepository songRepository;
    private final SearchSongRepository searchSongRepository;

    private final ListLiveData<Integer> songNumberList;
    private final MutableLiveData<Boolean> searchState;
    private final ExecutorService executor;

    public SearchViewModel(@NonNull Application application) {
        super(application);

        songRepository = SongRepository.getInstance();
        searchSongRepository = SearchSongRepository.getInstance();

        songNumberList = searchSongRepository.getSongNumberList();
        searchState = searchSongRepository.getSearchState();
        executor = Executors.newSingleThreadExecutor();
    }


    public ListLiveData<Integer> getSearchList() {
        return songNumberList;
    }

    public MutableLiveData<Boolean> getSearchState() {
        return searchState;
    }

    public void search(String searchType, String natName, String searchText) {
        executor.execute(() -> {
            searchState.postValue(true);
            SearchSong.search(searchType, natName, searchText);
            searchState.postValue(false);
        });
    }

    public void stopSearch() {
        searchState.postValue(false);
    }

    public void clearSearchSongList() {
        songNumberList.clear(false);
    }

    public Map<Integer, SongCellData> getSongDataMap() {
        return songRepository.getSongDataMap();
    }
}

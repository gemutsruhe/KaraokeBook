package com.karaoke.karaokebook.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.remote.GetPopularSong;
import com.karaoke.karaokebook.data.repository.PopularSongRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import java.util.HashMap;
import java.util.Map;

public class PopularViewModel extends AndroidViewModel {
    private final SongRepository songRepository;
    private final PopularSongRepository popularSongRepository;

    public PopularViewModel(@NonNull Application application) {
        super(application);
        songRepository = SongRepository.getInstance();
        popularSongRepository = PopularSongRepository.getInstance();
    }


    public void getPopularList() {
        for (int i = 0; i <= 2; i++) {
            GetPopularSong.get(i);
        }
    }

    public ListLiveData<Integer> getPopularList(int type) {
        return popularSongRepository.getPopularList(type);
    }

    public Map<Integer, SongCellData> getSongDataMap() {
        return songRepository.getSongDataMap();
    }
}

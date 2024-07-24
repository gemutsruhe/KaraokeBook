package com.karaoke.karaokebook.viewModel;

import androidx.lifecycle.ViewModel;

import com.karaoke.karaokebook.data.remote.GetPopularSong;
import com.karaoke.karaokebook.data.repository.PopularChartRepository;

public class PopularChartViewModel extends ViewModel {
    PopularChartRepository popularChartRepository;

    public PopularChartViewModel() {
        popularChartRepository = PopularChartRepository.getInstance();
    }

    public void getPopularList() {
        for (int i = 0; i <= 2; i++) {
            GetPopularSong.get(i);
        }
    }
}

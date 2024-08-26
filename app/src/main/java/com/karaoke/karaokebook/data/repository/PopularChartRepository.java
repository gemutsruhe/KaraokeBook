package com.karaoke.karaokebook.data.repository;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.ArrayList;

public class PopularChartRepository {
    private static PopularChartRepository instance;

    ArrayList<ListLiveData<Integer>> popularList;

    private PopularChartRepository() {
        initRepository();
    }

    public static PopularChartRepository getInstance() {
        if (instance == null) {
            instance = new PopularChartRepository();
        }
        return instance;
    }

    private void initRepository() {
        popularList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            popularList.add(new ListLiveData<>());
        }
    }

    public ListLiveData<Integer> getPopularList(int i) {
        return popularList.get(i);
    }
}

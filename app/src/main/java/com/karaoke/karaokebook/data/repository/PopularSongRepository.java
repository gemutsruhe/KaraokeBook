package com.karaoke.karaokebook.data.repository;

import com.karaoke.karaokebook.data.model.ListLiveData;

import java.util.ArrayList;

public class PopularSongRepository {
    private static PopularSongRepository instance;

    ArrayList<ListLiveData<Integer>> popularList;

    private PopularSongRepository() {
        initRepository();
    }

    public static PopularSongRepository getInstance() {
        if (instance == null) {
            instance = new PopularSongRepository();
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

    public int getSongNumber(int type, int pos) {
        return popularList.get(type).getValue().get(pos);
    }
}

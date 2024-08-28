package com.karaoke.karaokebook.data.repository;

import com.karaoke.karaokebook.data.cell.SongCellData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongRepository {
    private static SongRepository instance;

    private final Map<Integer, SongCellData> songDataMap;

    private SongRepository() {
        songDataMap = new HashMap<>();
    }

    public static SongRepository getInstance() {
        if(instance == null) {
            instance = new SongRepository();
        }

        return instance;
    }

    public void addSongData(int number, String title, String singer) {
        if(!songDataMap.containsKey(number)) {
            SongCellData songCellData = new SongCellData(number, title, singer, false);
            songDataMap.put(number, songCellData);
        }
    }

    public void addSongData(SongCellData data) {
        if(!songDataMap.containsKey(data.getNumber())) {
            songDataMap.put(data.getNumber(), data);
        } else {
            songDataMap.get(data.getNumber()).setBookmark(true);
        }
    }

    public SongCellData getSongData(int songNumber) {
        return songDataMap.getOrDefault(songNumber, null);
    }

    public Map<Integer, SongCellData> getSongDataMap() {
        return songDataMap;
    }
}

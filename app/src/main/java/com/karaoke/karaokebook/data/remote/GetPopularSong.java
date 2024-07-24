package com.karaoke.karaokebook.data.remote;

import android.net.Uri;
import android.util.Log;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.ListLiveData;
import com.karaoke.karaokebook.data.repository.PopularChartRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GetPopularSong {

    public static String buildURL(int type) {
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("www.tjmedia.com")
                .appendPath("tjsong")
                .appendPath("song_monthPopular.asp")
                .appendQueryParameter("strType", String.valueOf(type));

        return builder.toString();
    }

    public static void get(int type) {
        new Thread(() -> {
            String url = buildURL(type + 1);
            ListLiveData<SongCellData> data = PopularChartRepository.getInstance().getPopularList(type);

            ArrayList<SongCellData> popularSongList = new ArrayList<>();
            while (true) {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.select("div#BoardType1 table.board_type1 tbody tr");

                    for (int i = 1; i < elements.size(); i++) {
                        Element element = elements.get(i);

                        String songNum = element.child(1).text();
                        String songTitle = element.child(2).text();
                        String singer = element.child(3).text();

                        SongCellData songCellData = new SongCellData(songNum, songTitle, singer, false);
                        popularSongList.add(songCellData);
                    }
                    data.addAll(popularSongList);
                    break;
                } catch (IOException e) {
                    Log.e("TEST", Objects.requireNonNull(e.getMessage()));
                }
            }
        }).start();

    }
}

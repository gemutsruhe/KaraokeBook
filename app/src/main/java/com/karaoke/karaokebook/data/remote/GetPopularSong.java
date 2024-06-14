package com.karaoke.karaokebook.data.remote;

import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

import com.karaoke.karaokebook.data.cell.SongCellData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    public static List<SongCellData> get(int type) {
        String url = buildURL(type);

        ArrayList<SongCellData> popularSongList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div#BoardType1 table.board_type1 tbody tr");
            Log.e("TEST", String.valueOf(elements.size()));
            for (Element element : elements) {
                String songNum = element.child(1).text();
                String songTitle = element.child(2).text();
                String singer = element.child(3).text();

                SongCellData songCellData = new SongCellData(songNum, songTitle, singer, false);
                popularSongList.add(songCellData);

                Log.e("TEST", songTitle);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return popularSongList;
    }
}

package com.karaoke.karaokebook.data.remote;

import android.net.Uri;

import com.karaoke.karaokebook.data.repository.SearchSongRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchSong {

    private static final SongRepository songRepository = SongRepository.getInstance();

    private static final HashMap<String, String> searchTypeMap = new HashMap<String, String>() {
        {
            put("제목", "1");
            put("가수", "2");
            put("가사", "6");
        }
    };

    private static final HashMap<String, String> natTypeMap = new HashMap<String, String>() {
        {
            put("한국", "KOR");
            put("팝송", "ENG");
            put("일본", "JPN");
        }
    };

    private static String buildURL(String searchType, String natType, String searchText) {
        URI uri = URI.create("https://www.tjmedia.com/tjsong/song_search_list.asp");
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("www.tjmedia.com")
                .appendPath("tjsong")
                .appendPath("song_search_list.asp")
                .appendQueryParameter("strType", searchTypeMap.get(searchType))
                .appendQueryParameter("natType", natTypeMap.get(natType))
                .appendQueryParameter("strText", searchText)
                .appendQueryParameter("intPage", "");

        return builder.toString();
    }

    public static void search(String searchType, String natType, String searchText) {
        String url = buildURL(searchType, natType, searchText);

        SearchSongRepository searchSongRepository = SearchSongRepository.getInstance();

        int pageNum = 1;
        while (Boolean.TRUE.equals(searchSongRepository.getSearchState().getValue())) {
            ArrayList<Integer> songNumberList = new ArrayList<>();

            String pageUrl = url + pageNum;

            try {
                Document doc = Jsoup.connect(pageUrl).get();

                Elements elements = doc.select("div#BoardType1 table.board_type1 tbody tr td");

                int songNum = elements.size() / 6;

                if (songNum == 0) break;

                for (int i = 0; i < songNum; i++) {
                    int firstIdx = i * 6;
                    String number = elements.get(firstIdx).text();
                    String title = elements.get(firstIdx + 1).text();
                    String singer = elements.get(firstIdx + 2).text();
                    songRepository.addSongData(Integer.parseInt(number), title, singer);

                    songNumberList.add(Integer.parseInt(number));
                }
                searchSongRepository.addDataList(songNumberList);
                pageNum++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //SearchSongRepository.getInstance().getSearchState().postValue(false);
    }
}

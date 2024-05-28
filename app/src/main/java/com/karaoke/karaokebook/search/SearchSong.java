package com.karaoke.karaokebook.search;

import android.net.Uri;
import android.util.Log;

import com.karaoke.karaokebook.BookmarkDB;
import com.karaoke.karaokebook.CellData.SongCellData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class SearchSong implements Callable<ArrayList<SongCellData>> {
    private String searchType;
    private String natType;
    private String searchText;
    public SearchSong(String searchType, String natType, String searchText) {
        this.searchType = searchType;
        this.natType = natType;
        this.searchText = searchText;
    }

    private static HashMap<String, String> searchTypeMap = new HashMap<String, String>() {{
        put("제목", "1");
        put("가수", "2");
        put("가사", "6");
    }
    };

    private static HashMap<String, String> natTypeMap = new HashMap<String, String>() {{
        put("한국", "KOR");
        put("팝송", "ENG");
        put("일본", "JPN");
    }
    };

    private String buildURL() {
        URI uri = URI.create("https://www.tjmedia.com/tjsong/song_search_list.asp");
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("www.tjmedia.com")
                .appendPath("tjsong")
                .appendPath("song_search_list.asp")
                .appendQueryParameter("strType",searchTypeMap.get(searchType))
                .appendQueryParameter("natType", natTypeMap.get(natType))
                .appendQueryParameter("strText", searchText)
                .appendQueryParameter("intPage", "");

        return builder.toString();
    }

    @Override
    public ArrayList<SongCellData> call() throws Exception {
        String url = buildURL();

        BookmarkDB bookmarkDB = BookmarkDB.getInstance();
        ArrayList<SongCellData> songCellDataList = new ArrayList<>();

        int pageNum = 1;
        while(true) {
            String pageUrl = url + pageNum;
            Log.e("TEST", pageUrl);
            try {
                Document doc = Jsoup.connect(pageUrl).get();

                Elements elements = doc.select("div#BoardType1 table.board_type1 tbody tr td");

                int songNum = elements.size() / 6;
                Log.e("TEST", String.valueOf(songNum));
                if(songNum == 0) {

                    break;
                }

                for(int i = 0; i < songNum; i++) {
                    int firstIdx = i * 6;
                    String number = elements.get(firstIdx).text();
                    String title = elements.get(firstIdx + 1).text();
                    String singer = elements.get(firstIdx + 2).text();
                    //Boolean is_bookmarked = bookmarkDB.getBookmark(number);
                    SongCellData songCellData = new SongCellData(number, title, singer, false);
                    songCellDataList.add(songCellData);
                    Log.e("TEST", title);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            pageNum++;
        }

        return songCellDataList;
    }
}

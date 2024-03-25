package com.karaoke.karaokebook;

import android.net.Uri;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class SearchSong implements Callable<ArrayList<SongInfo>> {
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
    /*
    public static ArrayList<SongInfo> search(String searchType, String natType, String str) {
        //https://www.tjmedia.com/tjsong/song_search_list.asp?strType=1&natType=JPN&strText=%EC%95%84%EC%9D%B4%EB%8F%8C&strCond=0&searchOrderType=&searchOrderItem=&intPage=2
        String url = "https://www.tjmedia.com/tjsong/song_search_list.asp";
        url = url + "?strType=" + searchTypeMap.get(searchType);
        url = url + "&natType=" + natTypeMap.get(natType);
        url = url + "&strText=" + str;
        url = url + "&strSize02=100";
        url = url + "&searchOrderType=&searchOrderItem=&intPage=";

        //Log.e("TEST", url);
        BookmarkDB bookmarkDB = BookmarkDB.getInstance();
        ArrayList<SongInfo> songInfoList = new ArrayList<>();

        String finalUrl = url;
        MutableLiveData<ArrayList<SongInfo>> searchedSongsLiveData = null;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int pageNum = 1;
                while(true) {
                    String pageUrl = finalUrl + pageNum;
                    Log.e("TEST", pageUrl);
                    try {
                        Document doc = Jsoup.connect(pageUrl).get();
                        Element boardType1 = doc.selectFirst("div#BoardType1");
                        Element boardTable = boardType1.selectFirst("table.board_type1");

                        Elements elements = boardTable.select("tbody > tr > td");
                        int songNum = elements.size() / 6;
                        //Log.e("TEST2", String.valueOf(songNum));
                        if(songNum == 0) break;

                        for(int i = 0; i < songNum; i++) {
                            int firstIdx = i * 6;
                            String number = elements.get(firstIdx).text();
                            String title = elements.get(firstIdx + 1).text();
                            String singer = elements.get(firstIdx + 2).text();
                            Boolean is_bookmarked = bookmarkDB.getBookmarkList(number);
                            SongInfo songInfo = new SongInfo(number, title, singer, is_bookmarked);
                            songInfoList.add(songInfo);
                            Log.e("TEST", title);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pageNum++;
                }
            }
        });
        thread.start();

        return searchedSongsLiveData.getValue();
    }*/

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
                .appendQueryParameter("strSize02", "100");

        /*String url = "https://www.tjmedia.com/tjsong/song_search_list.asp";
        url = url + "?strType=" + searchTypeMap.get(searchType);
        url = url + "&natType=" + natTypeMap.get(natType);
        url = url + "&strText=" + searchText;
        url = url + "&strSize02=100";
        url = url + "&searchOrderType=&searchOrderItem=&intPage=";*/

        return builder.toString();
    }

    @Override
    public ArrayList<SongInfo> call() throws Exception {
        String url = buildURL();

        BookmarkDB bookmarkDB = BookmarkDB.getInstance();
        ArrayList<SongInfo> songInfoList = new ArrayList<>();

        int pageNum = 1;
        while(true) {
            String pageUrl = url + pageNum;
            Log.e("TEST", pageUrl);
            try {
                Document doc = Jsoup.connect(pageUrl).get();

                Elements elements = doc.select("div#BoardType1 table.board_type1 tbody tr td");

                int songNum = elements.size() / 6;
                //Log.e("TEST2", String.valueOf(songNum));
                if(songNum == 0) break;

                for(int i = 0; i < songNum; i++) {
                    int firstIdx = i * 6;
                    String number = elements.get(firstIdx).text();
                    String title = elements.get(firstIdx + 1).text();
                    String singer = elements.get(firstIdx + 2).text();
                    Boolean is_bookmarked = bookmarkDB.getBookmark(number);
                    SongInfo songInfo = new SongInfo(number, title, singer, is_bookmarked);
                    songInfoList.add(songInfo);
                    Log.e("TEST", title);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            pageNum++;
        }

        return songInfoList;
    }
}

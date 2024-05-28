package com.karaoke.karaokebook.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.karaoke.karaokebook.BookmarkDB;
import com.karaoke.karaokebook.LibraryAPI;
import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.CellFactory.SearchedSongCellFactory;
import com.karaoke.karaokebook.CellData.SongCellData;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class SearchedSongListLayout extends LinearLayout {
    LayoutInflater inflater;
    BookmarkDB bookmarkDB;

    String sharedPrefKey = getResources().getString(R.string.user_id);

    Retrofit client;
    LibraryAPI api;
    SharedPreferences sharedPref;
    public SearchedSongListLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //bookmarkDB = BookmarkDB.getInstance();
        sharedPref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        Resources resources = getResources();
    }

    public void addSearchedSongs(ArrayList<SongCellData> searchedSongCellDataList) {
        this.removeAllViews();

        if(searchedSongCellDataList != null) {
            for(int i = 0; i < searchedSongCellDataList.size(); i++) {
                //LinearLayout searchedSongCell = SearchedSongCellFactory.create(searchedSongCellDataList.get(i));
                //this.addView(searchedSongCell, i);
            }
        }
    }
}

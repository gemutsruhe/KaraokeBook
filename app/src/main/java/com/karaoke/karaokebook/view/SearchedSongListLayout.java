package com.karaoke.karaokebook.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.LifecycleOwner;

import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.data.remote.LibraryAPI;
import com.karaoke.karaokebook.data.repository.SearchedSongRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.factory.CellFactoryProvider;
import com.karaoke.karaokebook.factory.SongCellFactory;

import java.util.List;

import retrofit2.Retrofit;

public class SearchedSongListLayout extends LinearLayout {
    LayoutInflater inflater;

    String sharedPrefKey = getResources().getString(R.string.user_id);

    Retrofit client;
    LibraryAPI api;
    SharedPreferences sharedPref;
    CellFactoryProvider cellFactoryProvider;

    public SearchedSongListLayout(Context context, LifecycleOwner lifecycleOwner) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //bookmarkDB = BookmarkDB.getInstance();
        sharedPref = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        cellFactoryProvider = new CellFactoryProvider(new SongCellFactory(context, lifecycleOwner, this));

        SearchedSongRepository searchedSongRepository = SearchedSongRepository.getInstance();


    }

    public void addSearchedSongs(List<Integer> songNumberList) {
        //this.removeAllViews();

        if (songNumberList != null) {
            for (int i = this.getChildCount(); i < songNumberList.size(); i++) {
                View searchedSongCell = cellFactoryProvider.createCell(SongRepository.getInstance().getSongData(songNumberList.get(i)));
                this.addView(searchedSongCell, i);
            }
        }
    }
}

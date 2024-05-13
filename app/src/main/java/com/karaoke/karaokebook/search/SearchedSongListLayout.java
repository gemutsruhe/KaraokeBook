package com.karaoke.karaokebook.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.karaoke.karaokebook.BookmarkDB;
import com.karaoke.karaokebook.LibraryAPI;
import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.SongInfo;
import com.karaoke.karaokebook.databinding.CellSearchedSongBinding;

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
    }

    public void addSearchedSongs(ArrayList<SongInfo> searchedSongInfoList) {
        this.removeAllViews();

        if(searchedSongInfoList != null) {
            for(int i = 0; i < searchedSongInfoList.size(); i++) {
                CellSearchedSongBinding binding = createSongInfoView(searchedSongInfoList.get(i));

                this.addView(binding.getRoot(), i);
            }
        }
    }

    public CellSearchedSongBinding createSongInfoView(SongInfo songInfo){

        //View cell = inflater.inflate(R.layout.cell_searched_song, this, false);
        CellSearchedSongBinding binding = CellSearchedSongBinding.inflate(inflater, this, false);
        setNumber(binding.songNumTextView, songInfo.getNumber());
        setTitle(binding.songTitleTextView, songInfo.getTitle());
        setSinger(binding.songSingerTextView, songInfo.getSinger());
        setBookmark(binding.bookmarkImageView, songInfo, songInfo.isBookmarked());
        return binding;
    }

    private void setNumber(TextView songNumTextView, String number){
        songNumTextView.setText(number);
    }
    private void setTitle(TextView songTitleTextView, String title) {
        songTitleTextView.setText(title);
        songTitleTextView.setHorizontallyScrolling(true);
        songTitleTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void setSinger(TextView songSingerTextView, String singer) {
        songSingerTextView.setText(singer);
        songSingerTextView.setHorizontallyScrolling(true);
        songSingerTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setBookmark(ImageView bookmarkImageView, SongInfo songInfo, boolean isBookmarked) {
        if(isBookmarked) bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
        bookmarkImageView.setEnabled(true);
        bookmarkImageView.setOnClickListener(view -> {
            Drawable uncheckedBookmark = getResources().getDrawable(R.drawable.unchecked_bookmark, getContext().getTheme());
            if(bookmarkImageView.getDrawable().getConstantState().equals(uncheckedBookmark.getConstantState())) {
                bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
                bookmarkDB.addBookmark(songInfo);
            } else {
                bookmarkImageView.setImageResource(R.drawable.unchecked_bookmark);
                bookmarkDB.removeBookmark(songInfo);
            }
        });
    }
}

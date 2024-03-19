package com.example.karaokebook;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchedSongListLayout extends LinearLayout {
    LayoutInflater inflater;
    BookmarkDB bookmarkDB;
    public SearchedSongListLayout(Context context) {
        super(context);
        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bookmarkDB = BookmarkDB.getInstance();
    }

    public void addSearchedSongs(ArrayList<SongInfo> searchedSongInfoList) {
        this.removeAllViews();

        if(searchedSongInfoList != null) {
            for(int i = 0; i < searchedSongInfoList.size(); i++) {
                View cell = createSongInfoView(searchedSongInfoList.get(i));
                this.addView(cell, i);
            }
        }
    }

    public View createSongInfoView(SongInfo songInfo){

        View cell = inflater.inflate(R.layout.cell_seaarched_song, this, false);

        setNumber(cell, songInfo.getNumber());
        setTitle(cell, songInfo.getTitle());
        setSinger(cell, songInfo.getSinger());
        setBookmark(cell, songInfo, songInfo.isBookmarked());

        return cell;
    }

    private void setNumber(View cell, String number){
        TextView songNumTextView = cell.findViewById(R.id.songNumTextView);
        songNumTextView.setText(number);
    }
    private void setTitle(View cell, String title) {
        TextView songTitleTextView = cell.findViewById(R.id.songTitleTextView);
        songTitleTextView.setText(title);
        songTitleTextView.setHorizontallyScrolling(true);
        songTitleTextView.setMovementMethod(new ScrollingMovementMethod());

    }

    private void setSinger(View cell, String singer) {
        TextView songSingerTextView = cell.findViewById(R.id.songSingerTextView);
        songSingerTextView.setText(singer);
        songSingerTextView.setHorizontallyScrolling(true);
        songSingerTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setBookmark(View cell, SongInfo songInfo, boolean isBookmarked) {
        ImageView bookmarkImageView = cell.findViewById(R.id.bookmarkImageView);

        if(isBookmarked) bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
        bookmarkImageView.setEnabled(true);
        bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable uncheckedBookmark = getResources().getDrawable(R.drawable.unchecked_bookmark, getContext().getTheme());
                if(bookmarkImageView.getDrawable().getConstantState().equals(uncheckedBookmark.getConstantState())) {
                    bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
                    bookmarkDB.addBookmark(songInfo);
                } else {
                    bookmarkImageView.setImageResource(R.drawable.unchecked_bookmark);
                    bookmarkDB.removeBookmark(songInfo);
                }
            }
        });


    }
}

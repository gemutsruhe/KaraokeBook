package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellSearchedSongBinding;

public class SearchedSongCellFactory extends CellFactory {
    //private Resources resources = Resources.getSystem();
    public SearchedSongCellFactory(Context context, ViewGroup parent) {
        super(context, parent);
    }
    public ViewBinding create(CellData data) {
        SongCellData songCellData = (SongCellData) data;

        CellSearchedSongBinding binding = CellSearchedSongBinding.inflate(inflater, parent, false);

        setTextView(binding.songNumTextView, songCellData.getNumber());
        setScrollTextView(binding.songTitleTextView, songCellData.getTitle());
        setScrollTextView(binding.songSingerTextView, songCellData.getSinger());
        setBookmark(binding.bookmarkImageView, songCellData, songCellData.isBookmarked());
        //Log.e("TEST", String.valueOf(binding.songNumTextView.getText()));
        return binding;
    }

    private void setBookmark(ImageView bookmarkImageView, SongCellData songCellData, boolean isBookmarked) {
        if(isBookmarked) bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
        bookmarkImageView.setEnabled(true);
        bookmarkImageView.setOnClickListener(view -> {
            Drawable uncheckedBookmark = resources.getDrawable(R.drawable.unchecked_bookmark, context.getTheme());
            if(bookmarkImageView.getDrawable().getConstantState().equals(uncheckedBookmark.getConstantState())) {
                bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
                //bookmarkDB.addBookmark(songInfo);
            } else {
                bookmarkImageView.setImageResource(R.drawable.unchecked_bookmark);
                //bookmarkDB.removeBookmark(songInfo);
            }
        });
    }
}

package com.karaoke.karaokebook.CellFactory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.CellData.CellData;
import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.CellData.SongCellData;
import com.karaoke.karaokebook.databinding.CellSearchedSongBinding;

public class SearchedSongCellFactory extends CellFactory {
    //private Resources resources = Resources.getSystem();
    SearchedSongCellFactory(Context context, ViewGroup parent) {
        super(context, parent);
    }
    public ViewBinding create(CellData data) {
        SongCellData songCellData = (SongCellData) data;

        CellSearchedSongBinding binding = CellSearchedSongBinding.inflate(inflater, parent, false);

        setTextView(binding.songNumTextView, songCellData.getNumber());
        setTextView(binding.songTitleTextView, songCellData.getTitle());
        setTextView(binding.songSingerTextView, songCellData.getSinger());
        setBookmark(binding.bookmarkImageView, songCellData, songCellData.isBookmarked());

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

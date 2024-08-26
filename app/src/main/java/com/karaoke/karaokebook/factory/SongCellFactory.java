package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.model.Bookmark;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.databinding.CellSongBinding;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class SongCellFactory extends CellFactory {
    LibraryViewModel libraryViewModel;
    public SongCellFactory(Context context, LifecycleOwner lifecycleOwner, ViewGroup parent) {
        super(context, lifecycleOwner, parent);
        libraryViewModel = new LibraryViewModel(context);
    }

    public ViewBinding create(CellData data) {

        SongCellData songCellData = (SongCellData) data;

        CellSongBinding binding = CellSongBinding.inflate(inflater, parent, false);
        binding.setData(songCellData);
        binding.setViewModel(libraryViewModel);
        binding.setLifecycleOwner(lifecycleOwner);
        //setTextView(binding.songNumTextView, String.valueOf(songCellData.getNumber()));
        //setScrollTextView(binding.songTitleTextView, songCellData.getTitle());
        //setScrollTextView(binding.songSingerTextView, songCellData.getSinger());
        //setBookmark(binding.bookmarkImageView, songCellData, songCellData.isBookmarked());

        return binding;
    }

    /*private void setBookmark(ImageView bookmarkImageView, SongCellData songCellData, boolean isBookmarked) {
        bookmarkImageView.setEnabled(true);

        bookmarkImageView.setOnClickListener(view -> {
            Bookmark song = new Bookmark(songCellData);
            if(songCellData.isBookmarked()) {
                libraryViewModel.deleteBookmark(song);
            } else {
                libraryViewModel.addBookmark(song);
            }
        });

        songCellData.getBookmarked().observe(lifecycleOwner, bookmarked -> {
            if(bookmarked) {
                bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
            } else {
                bookmarkImageView.setImageResource(R.drawable.unchecked_bookmark);
            }
        });
    }*/
}

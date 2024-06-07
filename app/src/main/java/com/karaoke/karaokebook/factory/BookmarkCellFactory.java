package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.databinding.CellBookmarkedSongBinding;

public class BookmarkCellFactory extends CellFactory {
    BookmarkCellFactory(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public ViewBinding create(CellData info) {
        BookmarkCellData data = (BookmarkCellData) info;

        CellBookmarkedSongBinding binding = CellBookmarkedSongBinding.inflate(inflater, parent, false);

        setTextView(binding.songNumTextView, String.valueOf(data.getSongNumber()));
        setTextView(binding.songTitleTextView, data.getSongName());
        setTextView(binding.songSingerTextView, data.getSinger());
        setTextView(binding.keyLevelTextView, String.valueOf(data.getPitch()));

        return null;
    }
}

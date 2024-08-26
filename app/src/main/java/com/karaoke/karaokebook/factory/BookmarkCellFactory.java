package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.databinding.CellBookmarkedSongBinding;

public class BookmarkCellFactory extends CellFactory {

    public BookmarkCellFactory(Context context, LifecycleOwner lifecycleOwner, ViewGroup parent) {
        super(context, lifecycleOwner, parent);
    }

    @Override
    public ViewBinding create(CellData info) {
        //BookmarkCellData data = (BookmarkCellData) info;
        SongCellData data = (SongCellData) info;

        CellBookmarkedSongBinding binding = CellBookmarkedSongBinding.inflate(inflater, parent, false);
        binding.setData(data);
        /*setTextView(binding.songNumTextView, String.valueOf(data.getNumber()));
        setTextView(binding.songTitleTextView, data.getTitle());
        setTextView(binding.songSingerTextView, data.getSinger());
        setTextView(binding.keyLevelTextView, String.valueOf(data.getPitch()));*/

        return binding;
    }
}

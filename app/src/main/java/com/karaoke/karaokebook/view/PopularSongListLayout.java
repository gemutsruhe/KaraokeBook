package com.karaoke.karaokebook.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.lifecycle.LifecycleOwner;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.factory.CellFactoryProvider;
import com.karaoke.karaokebook.factory.SongCellFactory;

import java.util.List;

public class PopularSongListLayout extends LinearLayout {

    Context context;
    CellFactoryProvider cellFactory;

    public PopularSongListLayout(Context context, LifecycleOwner lifecycleOwner) {
        super(context);
        this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);

        cellFactory = new CellFactoryProvider(new SongCellFactory(context, lifecycleOwner,this));
    }

    public void addPopularList(List<Integer> songNumberList) {
        for (Integer songNumber : songNumberList) {
            View searchedSongCell = cellFactory.createCell(SongRepository.getInstance().getSongData(songNumber));
            this.addView(searchedSongCell);
        }
    }
}

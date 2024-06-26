package com.karaoke.karaokebook.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.remote.GetPopularSong;
import com.karaoke.karaokebook.factory.CellFactoryProvider;
import com.karaoke.karaokebook.factory.SearchedSongCellFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PopularSongListLayout extends LinearLayout {

    Context context;
    CellFactoryProvider cellFactory;

    public PopularSongListLayout(Context context) {
        super(context);
        this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);

        cellFactory = new CellFactoryProvider(new SearchedSongCellFactory(context, this));
    }
    public void addPopularList(List<SongCellData> cellDataList) {
        for (SongCellData cellData : cellDataList) {
            View searchedSongCell = cellFactory.createCell(cellData);
            this.addView(searchedSongCell);
        }
    }
}

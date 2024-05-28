package com.karaoke.karaokebook.CellFactory;

import android.view.View;

import com.karaoke.karaokebook.CellData.CellData;

public class CellFactoryProvider {

    CellFactory cellFactory;
    CellFactoryProvider(CellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    public View createCell(CellData cellData) {
        return cellFactory.create(cellData).getRoot();
    }
}

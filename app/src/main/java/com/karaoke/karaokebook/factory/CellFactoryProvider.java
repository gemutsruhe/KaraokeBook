package com.karaoke.karaokebook.factory;

import android.view.View;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.CellData;

public class CellFactoryProvider {

    CellFactory cellFactory;
    public CellFactoryProvider(CellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    public View createCell(CellData cellData) {
        return cellFactory.create(cellData).getRoot();
    }
}
package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.CellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.databinding.CellFolderBinding;

public class FolderCellFactory extends CellFactory {
    FolderCellFactory(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public ViewBinding create(CellData cellData) {
        CellFolderBinding binding = CellFolderBinding.inflate(inflater, parent, false);

        FolderCellData folderCellData = (FolderCellData) cellData;

        setTextView(binding.folderNameTextView, folderCellData.getName());
        setTextView(binding.folderItemNumTextView, String.valueOf(folderCellData.getParent()));

        return binding;
    }
}

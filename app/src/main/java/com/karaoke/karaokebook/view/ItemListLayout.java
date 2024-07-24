package com.karaoke.karaokebook.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.karaoke.karaokebook.data.local.BookmarkDB;
import com.karaoke.karaokebook.data.repository.LibraryRepository;

public class ItemListLayout extends LinearLayout {
    LayoutInflater inflater;
    BookmarkDB bookmarkDB;
    LibraryRepository libraryRepository;

    public ItemListLayout(Context context) {
        super(context);

        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void update(int folder) {

    }
}

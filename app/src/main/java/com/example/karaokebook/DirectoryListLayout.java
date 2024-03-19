package com.example.karaokebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class DirectoryListLayout extends LinearLayout {
    LayoutInflater inflater;
    BookmarkDB bookmarkDB;
    public DirectoryListLayout(Context context) {
        super(context);
        //this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bookmarkDB = BookmarkDB.getInstance();
    }

    public void addDirectories() {
        bookmarkDB.getDirectories();
    }
}

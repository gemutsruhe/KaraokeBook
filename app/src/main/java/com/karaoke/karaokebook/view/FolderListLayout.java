package com.karaoke.karaokebook.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.karaoke.karaokebook.data.local.BookmarkDB;

public class FolderListLayout extends LinearLayout {
    LayoutInflater inflater;
    BookmarkDB bookmarkDB;
    public FolderListLayout(Context context) {
        super(context);
        //this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void addDirectories() {

    }
}

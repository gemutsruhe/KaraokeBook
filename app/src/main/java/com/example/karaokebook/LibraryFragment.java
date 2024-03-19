package com.example.karaokebook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    View v;

    LinearLayout directoryListLayout;

    boolean rootDirectory;

    ArrayList<Boolean> isFolderList;
    BookmarkDB bookmarkDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_library, container, false);
        bookmarkDB = BookmarkDB.getInstance();

        directoryListLayout = v.findViewById(R.id.directoryListLayout);

        //directoryListLayout

        View folder = inflater.inflate(R.layout.cell_folder, directoryListLayout, false);
        if(folder != null) {
            directoryListLayout.addView(folder);
        }

        return v;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden) {
            Log.e("TEST", "not hidden");

            bookmarkDB.getBookmarkList();
        } else {
            Log.e("TEST", "hidden");
        }
    }
}

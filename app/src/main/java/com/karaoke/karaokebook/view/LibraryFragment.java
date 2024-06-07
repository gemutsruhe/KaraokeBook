package com.karaoke.karaokebook.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karaoke.karaokebook.data.local.BookmarkDB;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;

public class LibraryFragment extends Fragment {

    LinearLayout folderListLayout;

    BookmarkDB bookmarkDB;
    FragmentLibraryBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(getLayoutInflater(), container, false);
        bookmarkDB = BookmarkDB.getInstance();

        //folderListLayout = binding.folderListLayout;

        CellFolderBinding folderBinding = CellFolderBinding.inflate(getLayoutInflater(), folderListLayout, false);
        //folderListLayout.addView(folderBinding.getRoot());

        return binding.getRoot();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden) {

        } else {

        }
    }
}

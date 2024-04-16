package com.karaoke.karaokebook;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

public class LibraryFragment extends Fragment {

    LinearLayout directoryListLayout;

    BookmarkDB bookmarkDB;
    FragmentLibraryBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentLibraryBinding.inflate(getLayoutInflater(), container, false);
        bookmarkDB = BookmarkDB.getInstance();

        directoryListLayout = binding.directoryListLayout;

        CellFolderBinding folderBinding = CellFolderBinding.inflate(getLayoutInflater(), directoryListLayout, false);
        directoryListLayout.addView(folderBinding.getRoot());

        return binding.getRoot();
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

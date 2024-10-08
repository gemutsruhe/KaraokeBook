package com.karaoke.karaokebook.view.library;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.databinding.FragmentBookmarkBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class BookmarkFragment extends Fragment {

    FragmentBookmarkBinding binding;
    DatabaseViewModel databaseViewModel;
    LibraryViewModel libraryViewModel;

    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(getLayoutInflater(), container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        libraryViewModel = new ViewModelProvider(requireParentFragment()).get(LibraryViewModel.class);
        databaseViewModel.getDBBookmarkList();

        RecyclerView recyclerView = binding.bookmarkListView;
        BookmarkAdapter adapter = new BookmarkAdapter(libraryViewModel.getSongDataMap(), databaseViewModel, getViewLifecycleOwner());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        libraryViewModel.getBookmarkList().observe(getViewLifecycleOwner(), adapter::setBookmarkList);

        return binding.getRoot();
    }
}

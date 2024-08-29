package com.karaoke.karaokebook.view.library;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibraryFragment extends Fragment {
    FragmentLibraryBinding binding;
    DatabaseViewModel databaseViewModel;
    LibraryViewModel libraryViewModel;

    FragmentManager fragmentManager;
    FragmentTransaction ft;

    FolderFragment folderFragment;
    BookmarkFragment bookmarkFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLibraryBinding.inflate(getLayoutInflater(), container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        libraryViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);

        folderFragment = new FolderFragment();
        bookmarkFragment = new BookmarkFragment();

        fragmentManager = getChildFragmentManager();

        addFragment();

        binding.showFolderButton.setOnClickListener((view) -> {
            showFragment(folderFragment);
        });

        binding.showBookmarkButton.setOnClickListener((view) -> {
            showFragment(bookmarkFragment);
        });

        libraryViewModel.getBookmarkList().observe(getViewLifecycleOwner(), bookmarkList -> libraryViewModel.createBookmarkTree(bookmarkList));

        libraryViewModel.getFolderList().observe(getViewLifecycleOwner(), folderList -> libraryViewModel.createFolderTree(folderList));

        return binding.getRoot();
    }

    private void addFragment() {
        ft = fragmentManager.beginTransaction();

        ft.add(binding.libraryFragment.getId(), folderFragment, "FolderFragment").show(folderFragment);
        ft.add(binding.libraryFragment.getId(), bookmarkFragment, "BookmarkFragment").hide(bookmarkFragment);

        ft.commit();
    }

    private void showFragment(Fragment target) {
        ft = fragmentManager.beginTransaction();

        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment.equals(target)) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }

        ft.commit();
    }
}

package com.karaoke.karaokebook.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.databinding.FragmentBookmarkBinding;
import com.karaoke.karaokebook.factory.BookmarkCellFactory;
import com.karaoke.karaokebook.factory.CellFactoryProvider;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class BookmarkFragment extends Fragment {

    FragmentBookmarkBinding binding;
    LibraryRepository libraryRepository;
    LibraryViewModel libraryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(getLayoutInflater(), container, false);

        libraryRepository = LibraryRepository.getInstance();
        libraryViewModel = new LibraryViewModel(getContext());
        libraryViewModel.getDBBookmarkList();

        LinearLayout bookmarkListLayout = new ItemListLayout(getContext());
        binding.bookmarkScrollView.addView(bookmarkListLayout);

        CellFactoryProvider cellFactory = new CellFactoryProvider(new BookmarkCellFactory(getContext(), getViewLifecycleOwner(), bookmarkListLayout));

        libraryRepository.getBookmarkList().observe(getViewLifecycleOwner(), bookmarkList -> {
            bookmarkListLayout.removeAllViews();
            for(Integer songNumber : bookmarkList) {
                View bookmarkView = cellFactory.createCell(SongRepository.getInstance().getSongData(songNumber));
                bookmarkListLayout.addView(bookmarkView);
            }
        });



        return binding.getRoot();
    }
}

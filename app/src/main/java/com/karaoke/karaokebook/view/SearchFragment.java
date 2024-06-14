package com.karaoke.karaokebook.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.karaoke.karaokebook.data.local.BookmarkDB;
import com.karaoke.karaokebook.databinding.FragmentSearchBinding;
import com.karaoke.karaokebook.data.repository.SearchedSongRepository;
import com.karaoke.karaokebook.viewModel.SearchedSongViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {

    //LayoutInflater inflater = null;
    FragmentSearchBinding binding;

    SearchedSongListLayout searchedSongListLayout;

    SearchedSongRepository searchedSongRepository;
    SearchedSongViewModel searchedSongViewModel;
    String[] natList = {"한국", "팝송", "일본"};
    String[] typeList = {"제목", "가수", "가사"};
    BookmarkDB bookmarkDB;
    ExecutorService executorService;
    Boolean searchState;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        executorService = Executors.newSingleThreadExecutor();
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        searchedSongListLayout = new SearchedSongListLayout(getContext());
        binding.searchedSongScrollView.addView(searchedSongListLayout);

        setSpinnerAdapter(binding.typeSpinner, typeList);
        setSpinnerAdapter(binding.natSpinner, natList);

        searchedSongRepository = SearchedSongRepository.getInstance();
        searchedSongViewModel = new SearchedSongViewModel();

        searchedSongViewModel.getSongCellDataList().observe(getViewLifecycleOwner(), songCellDataList -> {
            searchedSongListLayout.addSearchedSongs(songCellDataList);
        });

        searchedSongViewModel.getSongCellDataList().observe(getViewLifecycleOwner(), songCellData -> {
            searchedSongListLayout.addSearchedSongs(songCellData);
        });

        searchedSongViewModel.getSearchState().observe(getViewLifecycleOwner(), searchState -> {
            this.searchState = searchState;

            if(searchState) {
                searchedSongRepository.getSongCellDataList().clear(false);
                searchedSongListLayout.removeAllViews();
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.searchImageView.setVisibility(View.GONE);
            } else {
                binding.progressBar.setVisibility(View.GONE);
                binding.searchImageView.setVisibility(View.VISIBLE);
            }
        });

        binding.searchImageView.setOnClickListener(view -> {
            if (!searchState) {
                String searchType = binding.typeSpinner.getSelectedItem().toString();
                String natName = binding.natSpinner.getSelectedItem().toString();
                String searchText = binding.searchStrEditText.getText().toString();

                searchedSongViewModel.search(searchType, natName, searchText);
            }
        });

        binding.progressBar.setOnClickListener(view -> {
            searchedSongViewModel.stopSearch();
        });

        return binding.getRoot();
    }

    private void setSpinnerAdapter(Spinner spinner, String[] list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }



    @Override
    public void onPause() {
        super.onPause();
        Log.e("TEST", "onPause");
    }
}

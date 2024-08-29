package com.karaoke.karaokebook.view.song;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.databinding.FragmentSearchBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.SearchViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SearchFragment extends Fragment {

    //LayoutInflater inflater = null;
    FragmentSearchBinding binding;

    DatabaseViewModel databaseViewModel;
    SearchViewModel searchViewModel;
    String[] natList = {"한국", "팝송", "일본"};
    String[] typeList = {"제목", "가수", "가사"};
    ExecutorService executorService;
    Boolean searchState;
    View v;
    @SuppressLint("NotifyDataSetChanged")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        executorService = Executors.newSingleThreadExecutor();
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        RecyclerView recyclerView = binding.searchSongView;
        SongAdapter adapter = new SongAdapter(getViewLifecycleOwner(), searchViewModel.getSongDataMap(), data -> {
            if (!data.isBookmark()) {
                databaseViewModel.addBookmark(data);
            } else {
                databaseViewModel.deleteBookmark(data);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        setSpinnerAdapter(binding.typeSpinner, typeList);
        setSpinnerAdapter(binding.natSpinner, natList);

        searchViewModel.getSearchList().observe(getViewLifecycleOwner(), songCellDataList -> {
            //searchedSongListLayout.addSearchedSongs(songCellDataList);
        });

        //searchedSongListLayout.addSearchedSongs(songCellData);
        searchViewModel.getSearchList().observe(getViewLifecycleOwner(), adapter::setSongList);

        searchViewModel.getSearchState().observe(getViewLifecycleOwner(), searchState -> {
            this.searchState = searchState;

            if(searchState) {
                //searchedSongRepository.getSongNumberList().clear(false);
                searchViewModel.clearSearchSongList();
                //searchedSongListLayout.removeAllViews();
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

                searchViewModel.search(searchType, natName, searchText);
            }
        });

        binding.progressBar.setOnClickListener(view -> {
            searchViewModel.stopSearch();
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
    }
}

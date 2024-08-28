package com.karaoke.karaokebook.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.FragmentPopularListBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.PopularViewModel;

public class PopularListFragment extends Fragment {
    private int type;
    SongAdapter adapter;
    DatabaseViewModel databaseViewModel;
    PopularViewModel popularViewModel;
    public PopularListFragment(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPopularListBinding binding = FragmentPopularListBinding.inflate(inflater, container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        popularViewModel = new ViewModelProvider(requireParentFragment()).get(PopularViewModel.class);

        RecyclerView recyclerView = binding.popularList;

        adapter = new SongAdapter(getViewLifecycleOwner(), popularViewModel.getSongDataMap(), data -> {
            if (!data.isBookmark()) {
                databaseViewModel.addBookmark(data);
            } else {
                databaseViewModel.deleteBookmark(data);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        popularViewModel.getPopularList(type).observe(getViewLifecycleOwner(), (popularList) -> {
            adapter.setSongList(popularList);
        });

        return binding.getRoot();
    }
}

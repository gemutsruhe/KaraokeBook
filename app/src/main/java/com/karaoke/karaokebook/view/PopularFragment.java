package com.karaoke.karaokebook.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.karaoke.karaokebook.data.repository.PopularChartRepository;
import com.karaoke.karaokebook.databinding.FragmentPopularBinding;
import com.karaoke.karaokebook.viewModel.PopularChartViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment {
    PopularChartRepository popularChartRepository;
    PopularChartViewModel popularChartViewModel;
    FragmentPopularBinding binding;
    ArrayList<Button> typeButtonList = new ArrayList<>();
    List<PopularSongListLayout> popularLayoutList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(getLayoutInflater(), container, false);
        popularChartRepository = PopularChartRepository.getInstance();
        popularChartViewModel = new PopularChartViewModel();

        initPopularList();

        typeButtonList.add(binding.koreanPopularBtn);
        typeButtonList.add(binding.popPopularBtn);
        typeButtonList.add(binding.japanPopularBtn);

        for (int i = 0; i < typeButtonList.size(); i++) {
            int type = i;
            typeButtonList.get(type).setOnClickListener(view -> changeType(type));
        }

        popularChartViewModel.getPopularList();

        for (int i = 0; i <= 2; i++) {
            int type = i;

            popularChartRepository.getPopularList(type).observe(getViewLifecycleOwner(), cellDataList -> {
                popularLayoutList.get(type).addPopularList(cellDataList);
                if (type == 0) {
                    changeType(0);
                }
            });
        }

        return binding.getRoot();
    }

    private void initPopularList() {
        for (int type = 0; type <= 2; type++) {
            popularLayoutList.add(new PopularSongListLayout(getContext()));
        }
    }

    private void addButtonList() {

    }

    private void changeType(int type) {
        binding.popularScrollView.removeAllViews();
        binding.popularScrollView.addView(popularLayoutList.get(type));
    }
}

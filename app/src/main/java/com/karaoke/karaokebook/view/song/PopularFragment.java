package com.karaoke.karaokebook.view.song;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.karaoke.karaokebook.databinding.FragmentPopularBinding;
import com.karaoke.karaokebook.viewModel.PopularViewModel;

import java.util.ArrayList;
import java.util.List;

public class PopularFragment extends Fragment {
    PopularViewModel popularViewModel;
    FragmentPopularBinding binding;
    ArrayList<Button> typeButtonList = new ArrayList<>();
    List<Fragment> popularFragmentList = new ArrayList<>();
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPopularBinding.inflate(getLayoutInflater(), container, false);

        popularViewModel = new ViewModelProvider(this).get(PopularViewModel.class);

        fragmentManager = getChildFragmentManager();

        addPopularListFragment();

        typeButtonList.add(binding.koreanPopularBtn);
        typeButtonList.add(binding.popPopularBtn);
        typeButtonList.add(binding.japanPopularBtn);

        for (int i = 0; i < typeButtonList.size(); i++) {
            int type = i;
            typeButtonList.get(type).setOnClickListener(view -> showFragment(popularFragmentList.get(type)));
        }

        popularViewModel.getPopularList();

        return binding.getRoot();
    }

    private void addPopularListFragment() {
        ft = fragmentManager.beginTransaction();

        for (int type = 0; type <= 2; type++) {
            popularFragmentList.add(new PopularListFragment(type));
            ft.add(binding.popularListFragment.getId(), popularFragmentList.get(type), "type" + type);
            ft.hide(popularFragmentList.get(type));
        }
        ft.show(popularFragmentList.get(0));

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

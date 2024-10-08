package com.karaoke.karaokebook.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.databinding.ActivityMainBinding;
import com.karaoke.karaokebook.view.library.LibraryFragment;
import com.karaoke.karaokebook.view.song.PopularFragment;
import com.karaoke.karaokebook.view.song.SearchFragment;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchFragment searchFragment;
    PopularFragment popularFragment;
    LibraryFragment libraryFragment;

    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    LibraryViewModel libraryViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();



        searchFragment = new SearchFragment();
        popularFragment = new PopularFragment();
        libraryFragment = new LibraryFragment();

        addFragments();

        ft.show(searchFragment);
        ft.hide(popularFragment);
        ft.hide(libraryFragment);

        binding.searchFragmentBtn.setOnClickListener(view -> {
            showFragment(searchFragment);
        });

        binding.popularFragmentBtn.setOnClickListener(view -> {
            showFragment(popularFragment);
        });

        binding.libraryFragmentBtn.setOnClickListener(view -> {
            showFragment(libraryFragment);
        });
    }

    private void addFragments() {
        ft = fragmentManager.beginTransaction();

        ft.add(R.id.fragmentFrame, searchFragment);
        ft.add(R.id.fragmentFrame, popularFragment);
        ft.add(R.id.fragmentFrame, libraryFragment);

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

package com.karaoke.karaokebook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.karaoke.karaokebook.bookmark.LibraryFragment;
import com.karaoke.karaokebook.databinding.ActivityMainBinding;
import com.karaoke.karaokebook.search.SearchFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchFragment searchFragment;
    LibraryFragment libraryFragment;
    Button searchFragmentButton;
    Button libraryFragmentButton;
    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    Fragment crtFragment = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchFragmentButton = binding.btnSearchFragment;
        libraryFragmentButton = binding.btnLibraryFragment;

        fragmentManager = getSupportFragmentManager();

        searchFragment = new SearchFragment();
        libraryFragment = new LibraryFragment();

        addFragments();
        ft.show(searchFragment);
        ft.hide(libraryFragment);
        //showFragment(searchFragment);

        searchFragmentButton.setOnClickListener(view -> {
            showFragment(searchFragment);
        });

        libraryFragmentButton.setOnClickListener(view -> {
            showFragment(libraryFragment);
        });


    }

    private void addFragments() {
        ft = fragmentManager.beginTransaction();

        ft.add(R.id.fragmentFrame, searchFragment);
        ft.add(R.id.fragmentFrame, libraryFragment);

        ft.commit();
    }
    private void showFragment(Fragment target) {
        ft = fragmentManager.beginTransaction();

        List<Fragment> fragmentList = fragmentManager.getFragments();
        for(Fragment fragment : fragmentList) {
            if(fragment.equals(target)) {
                Log.e("TEST", "show");
                ft.show(fragment);
            } else {
                Log.e("TEST", "hide");
                ft.hide(fragment);
            }
        }

        ft.commit();
    }

}

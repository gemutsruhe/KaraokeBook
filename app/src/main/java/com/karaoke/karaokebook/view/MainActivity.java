package com.karaoke.karaokebook.view;

import static com.karaoke.karaokebook.data.remote.GetPopularSong.get;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.remote.GetPopularSong;
import com.karaoke.karaokebook.view.LibraryFragment;
import com.karaoke.karaokebook.databinding.ActivityMainBinding;
import com.karaoke.karaokebook.view.SearchFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchFragment searchFragment;
    PopularFragment popularFragment;
    LibraryFragment libraryFragment;

    ActivityMainBinding binding;
    FragmentManager fragmentManager;
    FragmentTransaction ft;

    Fragment crtFragment = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragmentManager = getSupportFragmentManager();

        searchFragment = new SearchFragment();
        popularFragment =  new PopularFragment();
        libraryFragment = new LibraryFragment();

        addFragments();
        ft.show(searchFragment);
        ft.hide(libraryFragment);
        //showFragment(searchFragment);

        binding.searchFragmentBtn.setOnClickListener(view -> {
            showFragment(searchFragment);
        });

        binding.popularFragmentBtn.setOnClickListener(view -> {
            showFragment(popularFragment);
        });

        binding.libraryFragmentBtn.setOnClickListener(view -> {
            showFragment(libraryFragment);
        });

        new Thread(() -> {
            List<SongCellData> dataList = GetPopularSong.get(1);
        }).start();


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

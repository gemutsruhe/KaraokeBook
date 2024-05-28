package com.karaoke.karaokebook.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.karaoke.karaokebook.BookmarkDB;
import com.karaoke.karaokebook.CellData.SongCellData;
import com.karaoke.karaokebook.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchFragment extends Fragment {

    //LayoutInflater inflater = null;
    FragmentSearchBinding binding;

    ImageView searchImage;
    EditText searchStrEditText;
    SearchedSongListLayout searchedSongListLayout;

    String[] natList = {"한국", "팝송", "일본"};
    String[] typeList = {"제목", "가수", "가사"};

    LiveData<ArrayList<SongCellData>> searchedSongs;
    BookmarkDB bookmarkDB;
    ExecutorService executorService;
    boolean searching = false;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        executorService = Executors.newSingleThreadExecutor();
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        //.addView(binding.getRoot());
        searchedSongListLayout = new SearchedSongListLayout(getContext());
        binding.searchedSongScrollView.addView(searchedSongListLayout);

        setSpinnerAdapter(binding.typeSpinner, typeList);
        setSpinnerAdapter(binding.natSpinner, natList);
        searchedSongs = new MutableLiveData<>();
        searchedSongs.observe(getViewLifecycleOwner(), new Observer<ArrayList<SongCellData>>() {
            @Override
            public void onChanged(ArrayList<SongCellData> songCellDataList) {
                searchedSongListLayout.addSearchedSongs(songCellDataList);
                searching = false;
            }
        });

        binding.searchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searching) {
                    searching = true;

                    String searchType = binding.typeSpinner.getSelectedItem().toString();
                    String natName = binding.natSpinner.getSelectedItem().toString();
                    String searchText = binding.searchStrEditText.getText().toString();

                    search(searchType, natName, searchText);
                }


            }
        });

        return binding.getRoot();
    }

    private void setSpinnerAdapter(Spinner spinner, String[] list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void search(String searchType, String natName, String searchText){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Future<ArrayList<SongCellData>> searchResult = executorService.submit(new SearchSong(searchType, natName, searchText));

                    ((MutableLiveData<ArrayList<SongCellData>>)searchedSongs).postValue(searchResult.get());
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("TEST", "onPause");
    }
}

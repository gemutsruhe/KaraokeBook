package com.karaoke.karaokebook;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchFragment extends Fragment {

    Boolean firstCall = true;

    LayoutInflater inflater = null;

    ImageView searchImage;
    EditText searchStrEditText;
    SearchedSongListLayout searchedSongListLayout;

    Spinner typeSpinner;
    Spinner natSpinner;
    String[] natList = {"한국", "팝송", "일본"};
    String[] typeList = {"제목", "가수", "가사"};

    //ArrayList<SongInfo> searchedSongInfoList;
    //private ArrayList<SongInfo> searchedSongInfoList;
    MutableLiveData<ArrayList<SongInfo>> searchedSongsLiveData;
    BookmarkDB bookmarkDB;
    ExecutorService executorService;
    boolean searching = false;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        executorService = Executors.newSingleThreadExecutor();

        this.inflater = inflater;
        v = inflater.inflate(R.layout.fragment_search, container, false);
        typeSpinner = v.findViewById(R.id.typeSpinner);
        natSpinner = v.findViewById(R.id.natSpinner);
        searchImage = v.findViewById(R.id.searchImageView);
        searchStrEditText = v.findViewById(R.id.searchStrEditText);
        ScrollView searchedSongScrollView = v.findViewById(R.id.searchedSongScrollView);
        searchedSongListLayout = new SearchedSongListLayout(getContext());
        searchedSongScrollView.addView(searchedSongListLayout);
        //searchedSongListLayout = v.findViewById(R.id.searchedSongListLayout);

        SearchedSongListLayout listLayout = new SearchedSongListLayout(getContext());

        bookmarkDB = BookmarkDB.getInstance();

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        ArrayAdapter<String> natAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, natList);
        natAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        natSpinner.setAdapter(natAdapter);

        searchedSongsLiveData = new MutableLiveData<>();
        searchedSongsLiveData.setValue(null);
        searchedSongsLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<SongInfo>>() {
            @Override
            public void onChanged(ArrayList<SongInfo> songInfoList) {
                searchedSongListLayout.addSearchedSongs(songInfoList);
                searching = false;
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searching) {
                    searching = true;

                    String searchType = typeSpinner.getSelectedItem().toString();
                    String natName = natSpinner.getSelectedItem().toString();
                    String searchText = searchStrEditText.getText().toString();

                    search(searchType, natName, searchText);
                }


            }
        });
        return v;
    }

    private void search(String searchType, String natName, String searchText){

        Future<ArrayList<SongInfo>> searchResult = executorService.submit(new SearchSong(searchType, natName, searchText));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<SongInfo> newSearched = searchResult.get();
                    searchedSongsLiveData.postValue(newSearched);
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
    /*public void addSearchedSong() {
        searchedSongListLayout.removeAllViews();

        for(int i = 0; i < searchedSongInfoList.size(); i++) {
            View cell = createSongInfoView(searchedSongInfoList.get(i));
            searchedSongListLayout.addView(cell, i);
        }
    }*/
    /*public void addSearchedSong(ArrayList<SongInfo> searchedSongInfoList) {
        searchedSongListLayout.removeAllViews();
        if(searchedSongInfoList != null) {
            for(int i = 0; i < searchedSongInfoList.size(); i++) {
                View cell = createSongInfoView(searchedSongInfoList.get(i));
                searchedSongListLayout.addView(cell, i);
            }
        }
        searching = false;
    }*/

    private void setTitleTextView(View cell, String title) {
        TextView songTitleTextView = cell.findViewById(R.id.songTitleTextView);
        songTitleTextView.setText(title);
        songTitleTextView.setHorizontallyScrolling(true);
        songTitleTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setSingerTextView(View cell, String singer) {
        TextView songSingerTextView = cell.findViewById(R.id.songSingerTextView);
        songSingerTextView.setText(singer);
        songSingerTextView.setHorizontallyScrolling(true);
        songSingerTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    /*public View createSongInfoView(SongInfo songInfo){

        View cell = inflater.inflate(R.layout.cell_seaarched_song, searchedSongListLayout, false);

        TextView songNumTextView = cell.findViewById(R.id.songNumTextView);
        songNumTextView.setText(songInfo.getNumber());

        setTitleTextView(cell, songInfo.getTitle());
        setSingerTextView(cell, songInfo.getSinger());

        ImageView bookmarkImageView = cell.findViewById(R.id.bookmarkImageView);
        if(songInfo.isBookmark()) bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
        bookmarkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable uncheckedBookmark = getResources().getDrawable(R.drawable.unchecked_bookmark, getContext().getTheme());
                if(bookmarkImageView.getDrawable().getConstantState().equals(uncheckedBookmark.getConstantState())) {
                    bookmarkImageView.setImageResource(R.drawable.checked_bookmark);
                    bookmarkDB.addBookmark(songInfo);
                } else {
                    bookmarkImageView.setImageResource(R.drawable.unchecked_bookmark);
                    bookmarkDB.removeBookmark(songInfo);
                }
                bookmarkImageView.setEnabled(true);
            }
        });

        return cell;
    }*/


}

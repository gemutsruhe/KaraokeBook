package com.karaoke.karaokebook.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.data.repository.SongRepository;
import com.karaoke.karaokebook.databinding.CellBookmarkBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{

    private final Map<Integer, SongCellData> songDataMap;
    private List<Integer> bookmarkList;

    public BookmarkAdapter(Map<Integer, SongCellData> songDataMap) {
        this.songDataMap = songDataMap;
        bookmarkList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellBookmarkBinding binding = CellBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int songNumber = bookmarkList.get(position);
        SongCellData data = songDataMap.get(songNumber);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        Log.e("TEST", String.valueOf(bookmarkList.size()));
        return bookmarkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CellBookmarkBinding binding;
        public ViewHolder(CellBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SongCellData data) {
            binding.setData(data);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBookmarkList(List<Integer> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }
}

package com.karaoke.karaokebook.view;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellSongBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private final Map<Integer, SongCellData> songDataMap;
    private List<Integer> songList;
    private final LifecycleOwner lifecycleOwner;

    public interface OnBookmarkClickListener {
        void onBookmarkClick(SongCellData data);
    }

    private final SongAdapter.OnBookmarkClickListener listener;

    public SongAdapter(LifecycleOwner lifecycleOwner, Map<Integer, SongCellData> songDataMap, SongAdapter.OnBookmarkClickListener listener) {
        this.lifecycleOwner = lifecycleOwner;
        this.songDataMap = songDataMap;
        this.songList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellSongBinding binding = CellSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SongAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.ViewHolder holder, int position) {
        int songNumber = songList.get(position);
        SongCellData data = songDataMap.get(songNumber);
        holder.bind(lifecycleOwner, data, listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CellSongBinding binding;

        public ViewHolder(CellSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(LifecycleOwner lifecycleOwner, SongCellData data, SongAdapter.OnBookmarkClickListener listener) {
            binding.setData(data);
            binding.bookmarkImageView.setOnClickListener(view -> {
                listener.onBookmarkClick(data);
            });
            binding.setLifecycleOwner(lifecycleOwner);
        }
    }

    public void setSongList(List<Integer> songList) {
        this.songList = songList;
    }
}

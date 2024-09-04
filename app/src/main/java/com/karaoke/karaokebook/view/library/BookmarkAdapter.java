package com.karaoke.karaokebook.view.library;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellBookmarkBinding;
import com.karaoke.karaokebook.view.PitchDialog;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private final Map<Integer, SongCellData> songDataMap;
    private List<Integer> bookmarkList;
    private final DatabaseViewModel databaseViewModel;
    private final LifecycleOwner lifecycleOwner;


    public BookmarkAdapter(Map<Integer, SongCellData> songDataMap, DatabaseViewModel databaseViewModel, LifecycleOwner lifecycleOwner) {
        this.songDataMap = songDataMap;
        this.databaseViewModel = databaseViewModel;
        this.lifecycleOwner = lifecycleOwner;
        bookmarkList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CellBookmarkBinding binding = CellBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, databaseViewModel, lifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int songNumber = bookmarkList.get(position);
        SongCellData data = songDataMap.get(songNumber);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CellBookmarkBinding binding;
        private final DatabaseViewModel databaseViewModel;
        private final LifecycleOwner lifecycleOwner;

        public ViewHolder(CellBookmarkBinding binding, DatabaseViewModel databaseViewModel, LifecycleOwner lifecycleOwner) {
            super(binding.getRoot());
            this.binding = binding;
            this.databaseViewModel = databaseViewModel;
            this.lifecycleOwner = lifecycleOwner;
        }

        @SuppressLint("ClickableViewAccessibility")
        public void bind(SongCellData data) {
            binding.setData(data);
            binding.keyLevelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new PitchDialog(binding.getRoot().getContext(), data, databaseViewModel, lifecycleOwner).show();
                }
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBookmarkList(List<Integer> bookmarkList) {
        this.bookmarkList = bookmarkList;
        notifyDataSetChanged();
    }
}

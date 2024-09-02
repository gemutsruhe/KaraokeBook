package com.karaoke.karaokebook.view.library;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellBookmarkBinding;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static FolderAdapter.OnFolderClickListener listener;
    private final int VIEW_TYPE_FOLDER = 1;
    private final int VIEW_TYPE_BOOKMARK = 2;
    private final Map<Integer, FolderCellData> folderDataMap;
    private final Map<Integer, SongCellData> songDataMap;
    private final Map<Integer, Set<Integer>> folderTree;
    private final Map<Integer, Set<Integer>> bookmarkTree;
    //private final OnDeleteBookmarkClickListener deleteBookmarkListener;
    private List<Integer> crtFolderList;
    private List<Integer> crtBookmarkList;
    private final LibraryViewModel libraryViewModel;
    private final DatabaseViewModel databaseViewModel;
    FolderTouchHelperCallback touchHelperCallback;

    public FolderAdapter(DatabaseViewModel databaseViewModel, LibraryViewModel libraryViewModel) {
        this.libraryViewModel = libraryViewModel;
        this.databaseViewModel = databaseViewModel;
        this.folderDataMap = libraryViewModel.getFolderDataMap();
        this.songDataMap = libraryViewModel.getSongDataMap();
        this.folderTree = libraryViewModel.getFolderTree();
        this.bookmarkTree = libraryViewModel.getBookmarkTree();
        this.crtFolderList = new ArrayList<>();
        this.crtBookmarkList = new ArrayList<>();
    }

    public static void setOnFolderClickListener(FolderAdapter.OnFolderClickListener listener) {
        FolderAdapter.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_FOLDER) {
            CellFolderBinding binding = CellFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new FolderViewHolder(binding);
        } else {
            CellBookmarkBinding binding = CellBookmarkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new BookmarkViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FolderViewHolder) {
            int folderId = crtFolderList.get(position);

            ((FolderViewHolder) holder).bind(folderDataMap.get(folderId), folderTree.get(folderId), bookmarkTree.get(folderId));
        } else if (holder instanceof BookmarkViewHolder) {
            int bookmarkId = crtBookmarkList.get(position - crtFolderList.size());

            ((BookmarkViewHolder) holder).bind(songDataMap.get(bookmarkId), databaseViewModel, touchHelperCallback);
        }
    }

    @Override
    public int getItemCount() {
        return crtFolderList.size() + crtBookmarkList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < crtFolderList.size()) {
            return VIEW_TYPE_FOLDER;
        } else {
            return VIEW_TYPE_BOOKMARK;
        }
    }

    public void setCrtFolderList(List<Integer> crtFolderList) {
        this.crtFolderList = crtFolderList;
        notifyDataSetChanged();
    }

    public void setCrtBookmarkList(List<Integer> crtBookmarkList) {
        this.crtBookmarkList = crtBookmarkList;
        notifyDataSetChanged();
    }

    public int getCrtFolderCount() {
        return crtFolderList.size();
    }

    public interface OnFolderClickListener {
        void onFolderClick(FolderCellData data);
    }

    public static class FolderViewHolder extends RecyclerView.ViewHolder {
        CellFolderBinding binding;

        public FolderViewHolder(CellFolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FolderCellData data, Set<Integer> childFolder, Set<Integer> childBookmark) {
            binding.setData(data);
            binding.setChildFolder(childFolder);
            binding.setChildBookmark(childBookmark);
            binding.getRoot().setOnClickListener(view -> listener.onFolderClick(data));
        }
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        CellBookmarkBinding binding;

        public BookmarkViewHolder(CellBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SongCellData data, DatabaseViewModel databaseViewModel, FolderTouchHelperCallback touchHelperCallback) {
            binding.setData(data);
            binding.deleteBookmarkButton.setOnClickListener(view -> {
                databaseViewModel.deleteBookmark(data);
                touchHelperCallback.resetSelect();
            });
        }
    }

    public void setTouchHelperCallback(FolderTouchHelperCallback callback) {
        this.touchHelperCallback = callback;
    }

    public void moveFolder(int fromPosition, int toPosition) {
        if(fromPosition == toPosition) return;

        if(toPosition < crtFolderList.size()) {
            if(fromPosition < crtFolderList.size()) {
                Log.e("TEST", "moveFolder");
                int childFolder = crtFolderList.get(fromPosition);
                int parentFolder = crtFolderList.get(toPosition);
                databaseViewModel.moveFolder(childFolder, parentFolder);

            }
        }
    }

    public void changeItemPosition(int fromPosition, int toPosition) {
        Collections.swap(crtBookmarkList, fromPosition, toPosition);
    }
}

package com.karaoke.karaokebook.view.library;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellBookmarkBinding;
import com.karaoke.karaokebook.databinding.CellFolderBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_FOLDER = 1;
    private final int VIEW_TYPE_BOOKMARK = 2;

    private final Map<Integer, FolderCellData> folderDataMap;
    private final Map<Integer, SongCellData> songDataMap;

    private final Map<Integer, Set<Integer>> folderTree;
    private final Map<Integer, Set<Integer>> bookmarkTree;

    private List<Integer> crtFolderList;
    private List<Integer> crtBookmarkList;

    public interface OnFolderClickListener {
        void onFolderClick(FolderCellData data);
    }

    private static FolderAdapter.OnFolderClickListener listener;

    public FolderAdapter(Map<Integer, FolderCellData> folderDataMap, Map<Integer, SongCellData> songDataMap, Map<Integer, Set<Integer>> folderTree, Map<Integer, Set<Integer>> bookmarkTree) {
        this.folderDataMap = folderDataMap;
        this.songDataMap = songDataMap;
        this.folderTree = folderTree;
        this.bookmarkTree = bookmarkTree;
        this.crtFolderList = new ArrayList<>();
        this.crtBookmarkList = new ArrayList<>();
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

            ((BookmarkViewHolder) holder).bind(songDataMap.get(bookmarkId));
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

        public void bind(SongCellData data) {
            binding.setData(data);
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

    public static void setOnFolderClickListener(FolderAdapter.OnFolderClickListener listener) {
        FolderAdapter.listener = listener;
    }

    public int getCrtFolderCount() {
        return crtFolderList.size();
    }
}

package com.karaoke.karaokebook.view.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.databinding.CellBookmarkBinding;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.view.PitchDialog;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static OnFolderClickListener listener;
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
    private FolderTouchHelperCallback touchHelperCallback;
    private final LifecycleOwner lifecycleOwner;

    public FolderAdapter(DatabaseViewModel databaseViewModel, LibraryViewModel libraryViewModel, LifecycleOwner lifecycleOwner) {
        this.libraryViewModel = libraryViewModel;
        this.databaseViewModel = databaseViewModel;
        this.folderDataMap = libraryViewModel.getFolderDataMap();
        this.songDataMap = libraryViewModel.getSongDataMap();
        this.folderTree = libraryViewModel.getFolderTree();
        this.bookmarkTree = libraryViewModel.getBookmarkTree();
        this.crtFolderList = new ArrayList<>();
        this.crtBookmarkList = new ArrayList<>();
        this.lifecycleOwner = lifecycleOwner;
    }

    public static void setOnFolderClickListener(OnFolderClickListener listener) {
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

            ((BookmarkViewHolder) holder).bind(songDataMap.get(bookmarkId), databaseViewModel, touchHelperCallback, lifecycleOwner);
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
        Log.e("TEST", String.valueOf(crtBookmarkList.size()));
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
            binding.folderDataLayout.setOnClickListener(view -> listener.onFolderClick(data));
        }
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        CellBookmarkBinding binding;
        private GestureDetector gestureDetector;
        //private DatabaseViewModel databaseViewModel;
        //private

        public BookmarkViewHolder(CellBookmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ClickableViewAccessibility")
        public void bind(SongCellData data, DatabaseViewModel databaseViewModel, FolderTouchHelperCallback touchHelperCallback, LifecycleOwner lifecycleOwner) {
            binding.setData(data);

            binding.keyLevelTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new PitchDialog(binding.getRoot().getContext(), data, databaseViewModel, lifecycleOwner).show();
                }
            });

            binding.deleteBookmarkButton.setOnClickListener(view -> {
                databaseViewModel.deleteBookmark(data);
                touchHelperCallback.resetSelect();
            });
        }

        /*private class FolderNameDialog extends AlertDialog.Builder {
            int pitch = 0;
            public FolderNameDialog(@NonNull Context context) {
                super(context);
                LinearLayout linearLayout = new LinearLayout(context);
                pitch = 0;
                final TextView input = new TextView(context);
                input.setText(String.valueOf(pitch));
                final Button minusButton = new Button(context);
                minusButton.setText("-1");
                minusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pitch--;
                        input.setText(String.valueOf(pitch));
                    }
                });
                final Button plusButton = new Button(context);
                plusButton.setText("+1");
                plusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pitch++;
                        input.setText(String.valueOf(pitch));
                    }
                });

                linearLayout.addView(minusButton);
                linearLayout.addView(input);
                linearLayout.addView(plusButton);
                //input.setInputType(InputType.TYPE_CLASS_TEXT);

                this.setView(linearLayout);

                this.setPositiveButton("OK", (dialogInterface, i) -> {
                    String folderName = input.getText().toString();
                    *//*int parentFolderId = libraryViewModel.getCrtFolder().getValue();
                    databaseViewModel.addFolder(folderName, parentFolderId);
                    databaseViewModel.getDBFolderList();*//*
                });
                this.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

                this.show();
            }
        }*/
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
            } else {
                Log.e("TEST", "moveBookmark");
                int childBookmark = crtBookmarkList.get(fromPosition - crtFolderList.size());
                int parentFolder = crtFolderList.get(toPosition);
                databaseViewModel.moveBookmark(childBookmark, parentFolder);
            }
        }
    }

    public void changeItemPosition(int fromPosition, int toPosition) {
        if(crtBookmarkList.size() <= toPosition || crtBookmarkList.size() <= fromPosition) return ;
        Collections.swap(crtBookmarkList, fromPosition, toPosition);
    }
}

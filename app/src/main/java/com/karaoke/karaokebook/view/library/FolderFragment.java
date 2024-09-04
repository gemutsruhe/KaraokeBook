package com.karaoke.karaokebook.view.library;

import android.content.Context;
import android.gesture.Gesture;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.databinding.FragmentFolderBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class FolderFragment extends Fragment {
    FragmentFolderBinding binding;
    DatabaseViewModel databaseViewModel;
    LibraryViewModel libraryViewModel;
    FolderAdapter adapter;
    FolderTouchHelperCallback callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFolderBinding.inflate(inflater, container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        libraryViewModel = new ViewModelProvider(requireParentFragment()).get(LibraryViewModel.class);

        binding.addFolderButton.setOnClickListener((view) -> {
            new FolderNameDialog(getContext());
        });

        binding.setParent(libraryViewModel.getParentFolder());

        binding.setLifecycleOwner(getViewLifecycleOwner());

        binding.goParentFolderView.setOnClickListener((view) -> {
            callback.resetSelect();
            libraryViewModel.moveFolder(libraryViewModel.getParentFolder().getValue());
        });

        RecyclerView recyclerView = binding.folderChildView;
        FolderAdapter.setOnFolderClickListener(data -> libraryViewModel.moveFolder(data.getId()));

        GestureDetector gestureDetector = new GestureDetector(this.getContext(), new GestureListener());

        adapter = new FolderAdapter(databaseViewModel, libraryViewModel, getViewLifecycleOwner());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        callback = new FolderTouchHelperCallback(adapter, databaseViewModel, libraryViewModel);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.setTouchHelperCallback(callback);

        libraryViewModel.getCrtFolder().observe(getViewLifecycleOwner(), folder -> libraryViewModel.updateItemList(folder));

        libraryViewModel.getCrtFolderList().observe(getViewLifecycleOwner(), crtFolderList -> adapter.setCrtFolderList(crtFolderList));

        libraryViewModel.getCrtBookmarkList().observe(getViewLifecycleOwner(), crtBookmarkList -> adapter.setCrtBookmarkList(crtBookmarkList));

        databaseViewModel.getDBFolderList();

        return binding.getRoot();
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // 스크롤 동작 감지
            if (distanceY > 0) {

            } else if (distanceY < 0) {

            }
            return true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    private class FolderNameDialog extends AlertDialog.Builder {

        public FolderNameDialog(@NonNull Context context) {
            super(context);

            final EditText input = new EditText(context);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            this.setTitle("folder name");
            this.setView(input);

            this.setPositiveButton("OK", (dialogInterface, i) -> {
                String folderName = input.getText().toString();
                int parentFolderId = libraryViewModel.getCrtFolder().getValue();
                databaseViewModel.addFolder(folderName, parentFolderId);
                databaseViewModel.getDBFolderList();
            });
            this.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            this.show();
        }
    }
}

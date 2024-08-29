package com.karaoke.karaokebook.view.library;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.databinding.FragmentFolderBinding;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class FolderFragment extends Fragment {
    FragmentFolderBinding binding;
    DatabaseViewModel databaseViewModel;
    LibraryViewModel libraryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentFolderBinding.inflate(inflater, container, false);

        databaseViewModel = new ViewModelProvider(requireActivity()).get(DatabaseViewModel.class);
        libraryViewModel = new ViewModelProvider(requireParentFragment()).get(LibraryViewModel.class);

        binding.addFolderButton.setOnClickListener((view) -> {
            new FolderNameDialog(getContext());
        });

        RecyclerView recyclerView = binding.folderChildView;
        FolderAdapter adapter = new FolderAdapter(libraryViewModel.getFolderDataMap(), libraryViewModel.getSongDataMap(), libraryViewModel.getFolderTree(), libraryViewModel.getBookmarkTree());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        libraryViewModel.getCrtFolder().observe(getViewLifecycleOwner(), folder -> {
            libraryViewModel.updateItemList(folder);
        });

        libraryViewModel.getCrtFolderList().observe(getViewLifecycleOwner(), adapter::setCrtFolderList);

        libraryViewModel.getCrtBookmarkList().observe(getViewLifecycleOwner(), adapter::setCrtBookmarkList);

        databaseViewModel.getDBFolderList();

        return binding.getRoot();
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}

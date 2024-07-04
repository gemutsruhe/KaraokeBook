package com.karaoke.karaokebook.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.karaoke.karaokebook.data.local.BookmarkDB;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class LibraryFragment extends Fragment {

    LinearLayout folderListLayout;
    LibraryRepository libraryRepository;
    BookmarkDB bookmarkDB;
    FragmentLibraryBinding binding;
    LibraryViewModel libraryViewModel;
    Context context;
    String newFolderName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = FragmentLibraryBinding.inflate(getLayoutInflater(), container, false);
        bookmarkDB = BookmarkDB.getInstance();
        libraryViewModel = new LibraryViewModel(getContext());
        libraryRepository = LibraryRepository.getInstance();

        libraryRepository.getCrtFolder().observe(getViewLifecycleOwner(), crtFolder -> {

        });
        binding.addFolderButton.setOnClickListener(view -> {
            new FolderNameDialog(context);
        });
        //folderListLayout = binding.folderListLayout;

        CellFolderBinding folderBinding = CellFolderBinding.inflate(getLayoutInflater(), folderListLayout, false);
        //folderListLayout.addView(folderBinding.getRoot());

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
                int parentFolderId = libraryRepository.getCrtFolder().getValue();
                libraryViewModel.addFolder(folderName, parentFolderId);
            });
            this.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            this.show();
        }
    }
}

package com.karaoke.karaokebook.view;

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

import com.karaoke.karaokebook.data.local.BookmarkDB;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

public class LibraryFragment extends Fragment {

    ItemListLayout itemListLayout;
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
        libraryRepository = LibraryRepository.getInstance();

        libraryViewModel = new LibraryViewModel(context);
        itemListLayout = new ItemListLayout(context);

        libraryRepository.getFolder().observe(getViewLifecycleOwner(), crtFolder -> {
            libraryViewModel.updateItemList(crtFolder);
        });

        binding.addFolderButton.setOnClickListener(view -> {
            new FolderNameDialog(context);
        });

        binding.itemScrollView.addView(itemListLayout);

        libraryRepository.getItemList().observe(getViewLifecycleOwner(), itemList -> {

        });
        //folderListLayout = binding.folderListLayout;

        //CellFolderBinding folderBinding = CellFolderBinding.inflate(getLayoutInflater(), folderListLayout, false);
        //folderListLayout.addView(folderBinding.getRoot());

        return binding.getRoot();
    }

    private void updateItemList() {

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
                int parentFolderId = libraryRepository.getFolder().getValue();
                libraryViewModel.addFolder(folderName, parentFolderId);

                CellFolderBinding newFolder = CellFolderBinding.inflate(getLayoutInflater());
                newFolder.folderNameTextView.setText(folderName);

                itemListLayout.addView(newFolder.getRoot());
            });
            this.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());

            this.show();
        }
    }
}

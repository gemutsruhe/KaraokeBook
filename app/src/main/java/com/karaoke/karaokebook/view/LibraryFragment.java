package com.karaoke.karaokebook.view;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.FolderCellData;
import com.karaoke.karaokebook.data.repository.LibraryRepository;
import com.karaoke.karaokebook.databinding.CellFolderBinding;
import com.karaoke.karaokebook.databinding.FragmentLibraryBinding;
import com.karaoke.karaokebook.factory.CellFactoryProvider;
import com.karaoke.karaokebook.factory.FolderCellFactory;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    ItemListLayout itemListLayout;
    LibraryRepository libraryRepository;
    //BookmarkDB bookmarkDB;
    FragmentLibraryBinding binding;
    LibraryViewModel libraryViewModel;
    Context context;
    String newFolderName;
    FragmentManager fragmentManager;
    FragmentTransaction ft;
    FolderFragment folderFragment;
    BookmarkFragment bookmarkFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();

        binding = FragmentLibraryBinding.inflate(getLayoutInflater(), container, false);

        //bookmarkDB = BookmarkDB.getInstance();
        libraryRepository = LibraryRepository.getInstance();

        libraryViewModel = new LibraryViewModel(context);
        itemListLayout = new ItemListLayout(context);

        folderFragment = new FolderFragment();
        bookmarkFragment = new BookmarkFragment();

        fragmentManager = getChildFragmentManager();

        addFragment();

        binding.showFolderButton.setOnClickListener((view) -> {
            showFragment(folderFragment);
        });

        binding.showBookmarkButton.setOnClickListener((view) -> {
            showFragment(bookmarkFragment);
        });

        /*libraryRepository.getFolder().observe(getViewLifecycleOwner(), crtFolder -> {
            itemListLayout.removeAllViews();
            libraryViewModel.updateItemList(crtFolder);
        });

        binding.addFolderButton.setOnClickListener(view -> {
            new FolderNameDialog(context);
        });

        binding.itemScrollView.addView(itemListLayout);

        libraryRepository.getFolderList().observe(getViewLifecycleOwner(), folderList -> {
            libraryViewModel.createFolderTree(folderList);
            libraryViewModel.updateCrtFolderList(libraryRepository.getFolder().getValue());
        });

        libraryRepository.getCrtFolderList().observe(getViewLifecycleOwner(), crtFolderList -> {
            CellFactoryProvider cellFactory = new CellFactoryProvider(new FolderCellFactory(this.getContext(), itemListLayout));

            itemListLayout.removeAllViews();
            for(FolderCellData cellData : crtFolderList) {
                View folder = cellFactory.createCell(cellData);
                folder.setOnLongClickListener((view) -> {
                    libraryViewModel.deleteFolder(cellData.getId());
                    return true;
                });

                folder.setOnClickListener((view) -> {
                    libraryViewModel.moveFolder(cellData.getId());
                });
                itemListLayout.addView(folder);
            }
        });

        libraryRepository.getBookmarkList().observe(getViewLifecycleOwner(), bookmarkList -> {
            libraryViewModel.createBookmarkTree(bookmarkList);
            //libraryViewModel.updateCrtBookmarkList(libraryRepository.getFolder().getValue());
        });*/
        //folderListLayout = binding.folderListLayout;



        return binding.getRoot();
    }

    /*@Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            libraryRepository.setFolder(libraryRepository.getFolder().getValue());
        }
    }*/

    private void addFragment() {
        ft = fragmentManager.beginTransaction();

        ft.add(binding.libraryFragment.getId(), folderFragment, "FolderFragment").show(folderFragment);
        ft.add(binding.libraryFragment.getId(), bookmarkFragment, "BookmarkFragment").hide(bookmarkFragment);

        ft.commit();
    }

    private void showFragment(Fragment target) {
        ft = fragmentManager.beginTransaction();

        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment.equals(target)) {
                Log.e("TEST", "show");
                ft.show(fragment);
            } else {
                Log.e("TEST", "hide");
                ft.hide(fragment);
            }
        }

        ft.commit();
    }
}

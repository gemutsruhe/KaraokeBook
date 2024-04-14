package com.karaoke.karaokebook;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchFragment searchFragment;
    LibraryFragment libraryFragment;
    Button searchButton;
    Button libraryButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        searchFragment = new SearchFragment();
        libraryFragment = new LibraryFragment();

        searchButton = findViewById(R.id.btn_fragmentA);
        libraryButton = findViewById(R.id.btn_fragmentB);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment searchFragment = new SearchFragment();
        Fragment libraryFragment = new LibraryFragment();

        fragmentManager.beginTransaction().add(R.id.fragmentFrame, searchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentFrame, libraryFragment).commit();

        fragmentManager.beginTransaction().hide(libraryFragment).commit();

        while(BookmarkDB.createInstance(getApplicationContext()) == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().hide(libraryFragment).commit();
                fragmentManager.beginTransaction().show(searchFragment).commit();
            }
        });

        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction().hide(searchFragment).commit();
                fragmentManager.beginTransaction().show(libraryFragment).commit();
            }
        });
    }


}

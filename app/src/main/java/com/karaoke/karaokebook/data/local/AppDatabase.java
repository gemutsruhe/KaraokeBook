package com.karaoke.karaokebook.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.karaoke.karaokebook.data.model.Bookmark;
import com.karaoke.karaokebook.data.model.Folder;

@Database(entities = {Bookmark.class, Folder.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookmarkDao bookmarkDao();

    public abstract FolderDao folderDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase.db")
                    //.createFromAsset("database/app_database.db")
                    .build();
        }

        return instance;
    }

    public static AppDatabase getInstance() {
        return instance;
    }
}

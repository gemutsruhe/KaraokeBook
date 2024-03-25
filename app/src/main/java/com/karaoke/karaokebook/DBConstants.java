package com.karaoke.karaokebook;

public class DBConstants {
    static final String DATABASE_NAME = "BookmarkList.db";
    static final String COL_ID = "id";
    static final String COL_BOOKMARK_ID = "bookmark_id";
    static final String COL_SONG_NUMBER = "song_number";
    static final String COL_SONG_NAME = "song_name";
    static final String COL_SINGER = "singer";
    static final String COL_PITCH = "pitch";
    static final String BOOKMARK_TABLE_NAME = "Bookmark";
    static final String FOLDER_TABLE_NAME = "Folder";
    static final String COL_FOLDER_ID = "folder_id";
    static final String COL_FOLDER_NAME = "folder_name";
    static final String COL_BOOKMARKS_COUNT = "count";
    static final String FOLDER_BOOKMARK_TABLE_NAME = "FolderBookmarkList";
    static final String TABLE_NAME = "Bookmark";
    static final String COL_NUMBER = "number";
    static final String COL_NAME = "name";

    static final String CREATE_BOOKMARK_TABLE_SQL = "CREATE TABLE"
            + BOOKMARK_TABLE_NAME + "(" + COL_BOOKMARK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_SONG_NUMBER + "INTEGER,"
            + COL_SONG_NAME + " TEXT,"
            + COL_SINGER + " TEXT"
            + COL_PITCH + " INTEGER)";

    static final String CREATE_FOLDER_TABLE_SQL = "CREATE TABLE "
            + FOLDER_TABLE_NAME + "("
            + COL_FOLDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FOLDER_NAME + " TEXT DEFAULT (Undefined), "
            + "bookmarks_num INTEGER DEFAULT 0)";



}

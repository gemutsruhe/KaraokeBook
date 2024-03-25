package com.karaoke.karaokebook;


import static com.karaoke.karaokebook.DBConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BookmarkDB extends SQLiteOpenHelper {

    /*static final String DATABASE_NAME = "bookmarkList.db";

    static final String BOOKMARK_TABLE_NAME = "Bookmark";
    static final String FOLDER_TABLE_NAME = "Folder";
    static final String FOLDER_BOOKMARK_TABLE_NAME = "FolderBookmarkList";
    static final String TABLE_NAME = "Bookmark";
    static final String COL_NUMBER = "number";
    static final String COL_NAME = "name";
    static final String COL_SINGER = "singer";
    static final String COL_PITCH = "pitch";*/

    private static BookmarkDB dbInstance = null;
    private BookmarkDB(Context context, int version) {
        super(context, DBConstants.DATABASE_NAME, null, version);
    }

    public static BookmarkDB createInstance(Context context) {
        dbInstance = new BookmarkDB(context, 1);
        return dbInstance;
    }

    public static BookmarkDB getInstance() {
        return dbInstance;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        SongInfo songInfo;
        //songInfo.ge
        db.execSQL("CREATE TABLE Bookmark(bookmark_id INTEGER PRIMARY KEY AUTOINCREMENT, song_number INTEGER, song_name TEXT, singer TEXT, pitch INTEGER)");
        db.execSQL("CREATE TABLE Folder(folder_id INTEGER PRIMARY KEY AUTOINCREMENT, folder_name TEXT DEFAULT (Undefined), bookmarks_num INTEGER DEFAULT 0)");
        db.execSQL("CREATE TABLE FolderBookmarkList(list_id INTEGER PRIMARY KEY AUTOINCREMENT, folder_id INTEGER, bookmark_id INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean getBookmark(String number) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Bookmark WHERE number = " + number, null);
        Boolean isBookmarked = cursor.getCount() != 0;

        cursor.close();
        db.close();
        return isBookmarked;
    }

    public ArrayList<SongInfo> getBookmarkList() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Bookmark", null);
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int columnNum = cursor.getColumnCount();
                for(int i = 0; i < columnNum; i++) {

                    Log.e("TEST", cursor.getColumnName(i) + " " + cursor.getString(i) + " ");
                }
            } while (cursor.moveToNext());
        }
        db.close();
        return null;
    }

    public ArrayList<Boolean> getBookmarkList(ArrayList<SongInfo> songInfoList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Boolean> is_bookmarked = new ArrayList<>();

        for(SongInfo songInfo : songInfoList) {
            String number = songInfo.getNumber();
            Cursor cursor = db.rawQuery("SELECT * FROM Bookmark WHERE number = " + number, null);
            if(cursor.getCount() != 0) {
                is_bookmarked.add(true);
            } else {
                is_bookmarked.add(false);
            }
        }
        db.close();
        return is_bookmarked;
    }

    public void addBookmark(SongInfo songInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NUMBER, songInfo.getNumber());
        values.put(COL_NAME, songInfo.getTitle());
        values.put(COL_SINGER, songInfo.getSinger());
        values.put(COL_PITCH, songInfo.getPitch());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void removeBookmark(SongInfo songInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_NUMBER + "=?";
        String[] whereArgs = {String.valueOf(songInfo.getNumber())};

        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    
    public void createFolder(String folderName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //db.execSQL("CREATE TABLE Folder(name TEXT, number INTEGER)");
        values.put("folder_name", folderName);
        db.insert("Folder", null, values);
        db.close();
    }

    public void addBookmarkToFolder(Bookmark bookmark, BookmarkFolder bookmarkFolder) {
        int songNumber = bookmark.getSongNumber();
        String songName = bookmark.getSongName();
        int pitch = bookmark.getPitch();

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
    }

    public void getDirectories() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT folder_nam, bookmarks_num FROM Folder", null);
        if(cursor != null) {
            do{
                cursor.getString(0);
                cursor.getInt(1);
            } while (cursor.moveToNext());
        }
    }
}

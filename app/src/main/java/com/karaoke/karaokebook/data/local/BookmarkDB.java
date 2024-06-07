package com.karaoke.karaokebook.data.local;


import static com.karaoke.karaokebook.data.local.DBConstants.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.karaoke.karaokebook.data.cell.BookmarkCellData;
import com.karaoke.karaokebook.data.cell.SongCellData;
import com.karaoke.karaokebook.bookmark.BookmarkFolder;

import java.util.ArrayList;

public class BookmarkDB extends SQLiteOpenHelper {

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
        SongCellData songCellData;
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

    public ArrayList<SongCellData> getBookmarkList() {
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

    public ArrayList<Boolean> getBookmarkList(ArrayList<SongCellData> songCellDataList) {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Boolean> is_bookmarked = new ArrayList<>();

        for(SongCellData songCellData : songCellDataList) {
            String number = songCellData.getNumber();
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

    public void addBookmark(SongCellData songCellData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NUMBER, songCellData.getNumber());
        values.put(COL_NAME, songCellData.getTitle());
        values.put(COL_SINGER, songCellData.getSinger());
        values.put(COL_PITCH, songCellData.getPitch());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void removeBookmark(SongCellData songCellData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_NUMBER + "=?";
        String[] whereArgs = {String.valueOf(songCellData.getNumber())};

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

    public void addBookmarkToFolder(BookmarkCellData bookmarkCellData, BookmarkFolder bookmarkFolder) {
        int songNumber = bookmarkCellData.getSongNumber();
        String songName = bookmarkCellData.getSongName();
        int pitch = bookmarkCellData.getPitch();

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

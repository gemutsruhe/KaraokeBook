package com.karaoke.karaokebook.data;

import android.util.Log;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;

import com.karaoke.karaokebook.R;

import java.util.Set;

public class BindingAdapters {
    @BindingAdapter("app:songNumber")
    public static void setSongNumber(TextView textView, int songNumber) {
        textView.setText(String.valueOf(songNumber));
    }
    @BindingAdapter("app:bookmarkIcon")
    public static void setBookmarkIcon(ImageView imageview, LiveData<Boolean> bookmarked) {
        int resourceId = bookmarked.getValue() ? R.drawable.checked_bookmark : R.drawable.unchecked_bookmark;
        imageview.setImageResource(resourceId);
    }
    @BindingAdapter("app:songPitch")
    public static void setSongPitch(TextView textView, int songPitch) {
        if(songPitch > 0) textView.setText("+" + songPitch);
        else textView.setText(String.valueOf(songPitch));
    }

    @BindingAdapter("app:childFolderNum")
    public static void setChildFolderNum(TextView textView, Set<Integer> childFolder) {
        if(!(childFolder == null)) textView.setText(childFolder.size() + " 폴더");
        else textView.setText("0 폴더");
    }

    @BindingAdapter("app:childBookmarkNum")
    public static void setChildBookmarkNum(TextView textView, Set<Integer> childBookmark) {
        if(!(childBookmark == null)) textView.setText(childBookmark.size() + " 곡");
        else textView.setText("0 곡");
    }

    @BindingAdapter("app:goParentFolderVisible")
    public static void setGoParentFolderVisible(TextView textView, LiveData<Integer> parent) {
        if(parent.getValue() == -1) {
            textView.setVisibility(TextView.GONE);
        } else {
            textView.setVisibility(TextView.VISIBLE);
        }
    }
}

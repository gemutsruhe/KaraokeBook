package com.karaoke.karaokebook.data;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;

import com.karaoke.karaokebook.R;

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
        if(songPitch > 0) {
            textView.setText("+" + songPitch);
        } else {
            textView.setText(String.valueOf(songPitch));
        }

    }
}

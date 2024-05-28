package com.karaoke.karaokebook.CellFactory;

import android.content.Context;
import android.content.res.Resources;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.CellData.CellData;

public abstract class CellFactory {
    protected final Context context;
    protected final LayoutInflater inflater;
    protected final ViewGroup parent;
    protected final Resources resources;
    CellFactory(Context context, ViewGroup parent) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.parent = parent;
        this.resources = context.getResources();
    }
    public abstract ViewBinding create(CellData info);

    protected void setTextView(TextView textView, String text) {
        textView.setText(text);
        textView.setHorizontallyScrolling(true);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
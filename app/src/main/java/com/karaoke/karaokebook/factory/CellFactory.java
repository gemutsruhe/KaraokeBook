package com.karaoke.karaokebook.factory;

import android.content.Context;
import android.content.res.Resources;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.viewbinding.ViewBinding;

import com.karaoke.karaokebook.data.cell.CellData;

public abstract class CellFactory {
    protected final Context context;
    protected final LayoutInflater inflater;
    protected final ViewGroup parent;
    protected final Resources resources;
    protected final LifecycleOwner lifecycleOwner;

    CellFactory(Context context, LifecycleOwner lifecycleOwner, ViewGroup parent) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.parent = parent;
        this.resources = context.getResources();
    }

    public abstract ViewBinding create(CellData info);

    protected void setTextView(TextView textView, String text) {
        textView.setText(text);
    }

    protected void setScrollTextView(TextView textView, String text) {
        textView.setText(text);
        textView.setHorizontallyScrolling(true);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }
}
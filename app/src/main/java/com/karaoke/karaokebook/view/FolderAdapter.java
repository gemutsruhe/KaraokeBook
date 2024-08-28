package com.karaoke.karaokebook.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {

    private final int VIEW_TYPE_FOLDER = 1;
    private final int VIEW_TYPE_BOOKMARK = 2;


    public FolderAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {


        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

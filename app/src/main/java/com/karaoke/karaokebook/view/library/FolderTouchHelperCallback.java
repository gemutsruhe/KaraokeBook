package com.karaoke.karaokebook.view.library;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.R;

public class FolderTouchHelperCallback extends ItemTouchHelper.Callback {

    private final FolderAdapter adapter;
    private static final float SWIPE_DISTANCE = 0.2f;
    private final int SWIPE_NOT = 0;
    private final int SWIPE_LEFT = -1;
    private final int SWIPE_RIGHT = 1;
    private int preSwipeDirection = SWIPE_NOT;
    private int swipeDirection = SWIPE_NOT;
    private RecyclerView.ViewHolder prevViewHolder;

    public FolderTouchHelperCallback(FolderAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
        swipeDirection = preSwipeDirection;
        prevViewHolder = viewHolder;

        return 2.0f;
    }

    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
        return defaultValue * 40;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (prevViewHolder != null && prevViewHolder.getAdapterPosition() != viewHolder.getAdapterPosition()) {
                View view;

                if (prevViewHolder.getAdapterPosition() < adapter.getCrtFolderCount()) {
                    view = prevViewHolder.itemView.findViewById(R.id.folderDataLayout);
                } else {
                    view = prevViewHolder.itemView.findViewById(R.id.bookmarkDataLayout);
                }
                if (view != null) view.setTranslationX(0);
                swipeDirection = SWIPE_NOT;
                preSwipeDirection = SWIPE_NOT;
            }

            float width = viewHolder.itemView.getWidth();
            float MAX_SWIPE_DISTANCE = 0.2f;

            float maxDistance = width * MAX_SWIPE_DISTANCE;

            View swipeView;
            if (viewHolder.getAdapterPosition() < adapter.getCrtFolderCount()) {
                swipeView = viewHolder.itemView.findViewById(R.id.folderDataLayout);
            } else {
                swipeView = viewHolder.itemView.findViewById(R.id.bookmarkDataLayout);
            }


            float translationX;

            if (isCurrentlyActive) {
                if (swipeDirection == SWIPE_NOT) {
                    translationX = Math.min(Math.abs(dX), maxDistance);
                    if (dX < 0) translationX *= -1;

                    if (dX < -maxDistance) {
                        preSwipeDirection = SWIPE_LEFT;
                    } else if (dX > maxDistance) {
                        preSwipeDirection = SWIPE_RIGHT;
                    } else {
                        preSwipeDirection = SWIPE_NOT;
                    }
                } else {
                    translationX = Math.min(Math.abs(swipeDirection * maxDistance + dX), maxDistance);
                    if (swipeDirection * maxDistance + dX < 0) translationX *= -1;

                    if (dX < -maxDistance * 2) {
                        preSwipeDirection = SWIPE_LEFT;
                    } else if (dX > maxDistance * 2) {
                        preSwipeDirection = SWIPE_RIGHT;
                    } else if (Math.abs(dX) > maxDistance / 2) {
                        preSwipeDirection = SWIPE_NOT;
                    }
                }

            } else {
                if (swipeDirection == SWIPE_LEFT) {
                    translationX = -maxDistance;
                } else if (swipeDirection == SWIPE_RIGHT) {
                    translationX = maxDistance;
                } else {
                    translationX = 0;
                }
            }

            swipeView.setTranslationX(translationX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    public void deleteItem() {
        View view;
        if (prevViewHolder.getAdapterPosition() < adapter.getCrtFolderCount()) {
            view = prevViewHolder.itemView.findViewById(R.id.folderDataLayout);
        } else {
            view = prevViewHolder.itemView.findViewById(R.id.bookmarkDataLayout);
        }
        if (view != null) view.setTranslationX(0);
        swipeDirection = SWIPE_NOT;
        preSwipeDirection = SWIPE_NOT;
        prevViewHolder = null;
    }

}

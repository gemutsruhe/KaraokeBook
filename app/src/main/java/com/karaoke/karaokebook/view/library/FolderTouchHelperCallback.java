package com.karaoke.karaokebook.view.library;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.karaoke.karaokebook.R;
import com.karaoke.karaokebook.viewModel.DatabaseViewModel;
import com.karaoke.karaokebook.viewModel.LibraryViewModel;

import java.util.List;

public class FolderTouchHelperCallback extends ItemTouchHelper.Callback {

    private final FolderAdapter adapter;
    private static final float SWIPE_DISTANCE = 0.2f;
    private final int SWIPE_NOT = 0;
    private final int SWIPE_LEFT = -1;
    private final int SWIPE_RIGHT = 1;
    private int preSwipeDirection = SWIPE_NOT;
    private int swipeDirection = SWIPE_NOT;
    private RecyclerView.ViewHolder prevViewHolder;
    private int scaled = 0;
    private int currentActionState = ItemTouchHelper.ACTION_STATE_IDLE;
    private final DatabaseViewModel databaseViewModel;
    private final LibraryViewModel libraryViewModel;

    private int topBoundary;
    private int bottomBoundary;
    private int crtPosition;

    private int fromPosition;
    private int toPosition;

    public FolderTouchHelperCallback(FolderAdapter adapter, DatabaseViewModel databaseViewModel, LibraryViewModel libraryViewModel) {
        this.adapter = adapter;
        this.databaseViewModel = databaseViewModel;
        this.libraryViewModel = libraryViewModel;
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
        /*Log.e("TEST", "onMove");
        Log.e("TEST", viewHolder.itemView.getTop() + " " + viewHolder.itemView.getBottom());
        Log.e("TEST", target.itemView.getTop() + " " + target.itemView.getBottom());*/
        //Log.e("TEST", fromPosition + " " + toPosition);
        if (fromPosition < adapter.getCrtFolderCount() || fromPosition >= adapter.getCrtFolderCount() && toPosition < adapter.getCrtFolderCount()) {
            if(scaled == 0) {

            }
        } else {

            adapter.changeItemPosition(fromPosition, toPosition);
            adapter.notifyItemMoved(fromPosition, toPosition);
        }

        this.fromPosition = fromPosition;
        this.toPosition = toPosition;

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

            float maxDistance = width * SWIPE_DISTANCE;

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
        }
        else if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            if(viewHolder.itemView.getY() + viewHolder.itemView.getHeight() / 2 < topBoundary) {
                if(crtPosition > 0) crtPosition--;
                if(crtPosition < adapter.getCrtFolderCount()) {
                    scaled = 1;
                    viewHolder.itemView.setScaleX(0.8f);
                    viewHolder.itemView.setScaleY(0.8f);
                }
                View view = recyclerView.getChildAt(crtPosition);
                topBoundary = view.getTop();
                bottomBoundary = view.getBottom();
            } else if(viewHolder.itemView.getY() + viewHolder.itemView.getHeight() / 2 > bottomBoundary) {
                if(crtPosition < recyclerView.getChildCount() - 1) crtPosition++;
                if(adapter.getCrtFolderCount() <= crtPosition) {
                    scaled = 0;
                    viewHolder.itemView.setScaleX(1.0f);
                    viewHolder.itemView.setScaleY(1.0f);
                }

                View view = recyclerView.getChildAt(crtPosition);
                if(view != null) {
                    topBoundary = view.getTop();
                    bottomBoundary = view.getBottom();
                }

            }

            float limitTop = (float)recyclerView.getChildAt(0).getHeight() * (viewHolder.getAdapterPosition() + 0.25f);
            if(dY < -limitTop) dY = -limitTop;

            float limitBottom = (float)recyclerView.getChildAt(recyclerView.getChildCount() - 1).getHeight() / 2;
            if(viewHolder.getAdapterPosition() == recyclerView.getChildCount() && dY > limitBottom) dY = limitBottom;

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    public void resetSelect() {
        View view = null;
        if(prevViewHolder == null) return;
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

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            if(currentActionState == ItemTouchHelper.ACTION_STATE_DRAG && scaled == 1) {
                adapter.moveFolder(fromPosition, crtPosition);

                prevViewHolder.itemView.setScaleX(1.0f);
                prevViewHolder.itemView.setScaleY(1.0f);
                scaled = 0;
            }
        } else {
            if(actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                prevViewHolder = viewHolder;
                topBoundary = viewHolder.itemView.getTop();
                bottomBoundary = viewHolder.itemView.getBottom();
                crtPosition = viewHolder.getAdapterPosition();
            }
        }
        //prevViewHolder = viewHolder;
        currentActionState = actionState;

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
        RecyclerView.ViewHolder viewHolder = super.chooseDropTarget(selected, dropTargets, curX, curY);

        if(viewHolder != null) Log.e("TEST", "choose : " + viewHolder.getAdapterPosition());
        //Log.e("TEST", "viewTop : " + topBoundary + " viewBottom : " + viewBottom + " curY : " + curY);
        return super.chooseDropTarget(selected, dropTargets, curX, curY);
    }
}

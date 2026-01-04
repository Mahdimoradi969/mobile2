package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class DragDropCallback extends ItemTouchHelper.Callback {

    public interface OnDragFinished {
        void onFinished();
    }

    private final TicketAdapter adapter;
    private final OnDragFinished onDragFinished;

    public DragDropCallback(TicketAdapter adapter, OnDragFinished onDragFinished) {
        this.adapter = adapter;
        this.onDragFinished = onDragFinished;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true; // با نگه داشتن طولانی آیتم جابه‌جا میشه
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false; // سوایپ خاموش (حذف فقط با دکمه)
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        adapter.moveItem(viewHolder.getBindingAdapterPosition(), target.getBindingAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) { }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        onDragFinished.onFinished(); // وقتی جابه‌جایی تموم شد، ذخیره کن
    }
}

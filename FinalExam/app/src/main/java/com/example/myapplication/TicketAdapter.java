package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.VH> {

    public interface OnDeleteClick {
        void onDelete(Ticket ticket);
    }

    private final ArrayList<Ticket> items = new ArrayList<>();
    private final OnDeleteClick onDeleteClick;

    public TicketAdapter(OnDeleteClick onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }

    public void setItems(List<Ticket> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public List<Ticket> getCurrentItems() {
        return new ArrayList<>(items);
    }

    public void moveItem(int from, int to) {
        if (from < 0 || to < 0 || from >= items.size() || to >= items.size()) return;
        Ticket moved = items.remove(from);
        items.add(to, moved);
        notifyItemMoved(from, to);

        int start = Math.min(from, to);
        int end = Math.max(from, to);
        notifyItemRangeChanged(start, end - start + 1);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ticket t = items.get(position);

        int queueNumber = holder.getBindingAdapterPosition() + 1;
        holder.txtNumber.setText(String.valueOf(queueNumber));
        holder.txtName.setText(t.clientName);

        holder.btnDelete.setOnClickListener(v -> onDeleteClick.onDelete(t));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtNumber, txtName;
        ImageButton btnDelete;

        VH(@NonNull View itemView) {
            super(itemView);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtName = itemView.findViewById(R.id.txtName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

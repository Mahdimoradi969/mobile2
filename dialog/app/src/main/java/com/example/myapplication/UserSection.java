package com.example.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserSectionAdapter extends RecyclerView.Adapter&lt;UserSectionAdapter.SectionViewHolder&gt; {

    private final Context context;
    private final List&lt;UserSection&gt; sections;
    private final OnUserClickListener listener;

    public UserSectionAdapter(Context context, List&lt;UserSection&gt; sections, OnUserClickListener listener) {
        this.context = context;
        this.sections = sections;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_section, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        UserSection section = sections.get(position);
        holder.tvSectionTitle.setText(section.getTitle());

        UserRowAdapter rowAdapter = new UserRowAdapter(section.getUsers(), listener);
        holder.rvHorizontalUsers.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        holder.rvHorizontalUsers.setAdapter(rowAdapter);
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {

        TextView tvSectionTitle;
        RecyclerView rvHorizontalUsers;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSectionTitle = itemView.findViewById(R.id.tv_section_title);
            rvHorizontalUsers = itemView.findViewById(R.id.rv_horizontal_users);
        }
    }
}
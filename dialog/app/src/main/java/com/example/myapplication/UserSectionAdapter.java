package com.example.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserGroupAdapter extends RecyclerView.Adapter<UserGroupAdapter.GroupViewHolder> {

    private final Context context;
    private final List<UserGroup> groups;
    private final OnUserClickListener listener;

    public UserGroupAdapter(Context context, List<UserGroup> groups, OnUserClickListener listener) {
        this.context = context;
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        UserGroup group = groups.get(position);
        holder.tvGroupName.setText(group.getGroupName());

        UserRowAdapter rowAdapter = new UserRowAdapter(group.getUsers(), listener);
        holder.rvHorizontalUsers.setLayoutManager(
                new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        );
        holder.rvHorizontalUsers.setAdapter(rowAdapter);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView tvGroupName;
        RecyclerView rvHorizontalUsers;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupName = itemView.findViewById(R.id.tv_group_name);
            rvHorizontalUsers = itemView.findViewById(R.id.rv_horizontal_users);
        }
    }
}
package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {

    private Context context;
    private List<List<UserData>> userListGroups;
    private ItemClickListener itemClickListener;

    public CustomRecyclerAdapter(Context context, List<List<UserData>> userListGroups, ItemClickListener itemClickListener) {
        this.context = context;
        this.userListGroups = userListGroups;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        UserGroupAdapter userGroupAdapter = new UserGroupAdapter(userListGroups.get(position), itemClickListener);
        holder.horizontalRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.horizontalRecyclerView.setAdapter(userGroupAdapter);
    }

    @Override
    public int getItemCount() {
        return userListGroups.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        RecyclerView horizontalRecyclerView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalRecyclerView = itemView.findViewById(R.id.rv_horizontal);
        }
    }
}
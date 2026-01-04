package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {

    private List<PersonData> personList;
    private ItemClickListener itemClickListener;

    public PersonAdapter(List<PersonData> personList, ItemClickListener itemClickListener) {
        this.personList = personList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        PersonData personData = personList.get(position);
        holder.userNameTextView.setText(personData.getPersonName());
        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClicked(personData));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    static class PersonViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.tv_user_name);
        }
    }
}
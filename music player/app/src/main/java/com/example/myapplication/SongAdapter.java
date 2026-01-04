package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private Context mContext;
    private List<Track> trackList;

    public TrackAdapter(Context context, List<Track> tracks) {
        this.mContext = context;
        this.trackList = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = trackList.get(position);
        holder.titleText.setText(track.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MusicPlayerActivity.class);
            intent.putExtra("trackIndex", position);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.songTitle);
        }
    }
}
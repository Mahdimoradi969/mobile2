package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v2.R;

import java.util.ArrayList;
import java.util.List;

public class PlaylistActivity extends AppCompatActivity {
    private static List<Song> trackList = new ArrayList<>();
    private static final int PICK_AUDIO_CODE = 2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        RecyclerView recycler = findViewById(R.id.recyclerView);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Adding predefined songs to the list
        trackList.add(new Song("Batel Shod", R.raw.batel_shod));
        trackList.add(new Song("Spazz", R.raw.spazz));
        trackList.add(new Song("Rock A Chock", R.raw._rock_a_chock));

        // Setting up adapter for the RecyclerView
        TrackAdapter adapter = new TrackAdapter(this, trackList);
        recycler.setAdapter(adapter);

        // Button to open music picker
        Button addMusicButton = findViewById(R.id.selectMusicButton);
        addMusicButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("audio/*");
            startActivityForResult(intent, PICK_AUDIO_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_AUDIO_CODE && resultCode == RESULT_OK && data != null) {
            Uri audioUri = data.getData();
            if (audioUri != null) {
                // Persisting URI permission
                getContentResolver().takePersistableUriPermission(audioUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Create a song entry with the selected URI
                Song newTrack = new Song("Custom Track", audioUri.hashCode());
                trackList.add(newTrack);

                // Notify the adapter about data changes
                TrackAdapter adapter = (TrackAdapter) recycler.getAdapter();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                // Start PlayerActivity with the new track
                Intent playerIntent = new Intent(this, PlayerActivity.class);
                playerIntent.putExtra("trackIndex", trackList.size() - 1);
                startActivity(playerIntent);
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
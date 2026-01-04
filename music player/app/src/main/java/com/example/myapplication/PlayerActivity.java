package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.v2.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    private TextView songNameText, currentProgress, totalDuration;
    private ImageButton playPauseBtn, nextTrackBtn, prevTrackBtn, forward10SecBtn, rewind10SecBtn;
    private ImageView albumImage;
    private SeekBar progressBar;
    private MediaPlayer player;
    private Handler progressHandler = new Handler();
    private int currentTrackIndex = 0;
    private List<Song> trackList = PlayListActivity.songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        songNameText = findViewById(R.id.songTitle);
        playPauseBtn = findViewById(R.id.playPauseButton);
        nextTrackBtn = findViewById(R.id.nextButton);
        prevTrackBtn = findViewById(R.id.prevButton);
        forward10SecBtn = findViewById(R.id.forward10Button);
        rewind10SecBtn = findViewById(R.id.back10Button);
        albumImage = findViewById(R.id.albumArt);
        progressBar = findViewById(R.id.seekBar);
        currentProgress = findViewById(R.id.currentTime);
        totalDuration = findViewById(R.id.totalTime);

        currentTrackIndex = getIntent().getIntExtra("index", 0);
        loadAndPlayTrack(currentTrackIndex);

        playPauseBtn.setOnClickListener(v -> togglePlayPause());
        nextTrackBtn.setOnClickListener(v -> switchTrack(1));
        prevTrackBtn.setOnClickListener(v -> switchTrack(-1));

        forward10SecBtn.setOnClickListener(v -> seekTrackBy(10000));
        rewind10SecBtn.setOnClickListener(v -> seekTrackBy(-10000));

        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null) player.seekTo(progress);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void loadAndPlayTrack(int trackIndex) {
        if (player != null) player.release();

        Song song = trackList.get(trackIndex);
        songNameText.setText(song.getTitle());

        if (song.getFileResId() < 0) {
            player = new MediaPlayer();
            try {
                player.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.batel_shod));
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
                player = MediaPlayer.create(this, R.raw.batel_shod);
                player.start();
            }
        } else {
            player = MediaPlayer.create(this, song.getFileResId());
            player.start();
        }

        playPauseBtn.setImageResource(android.R.drawable.ic_media_pause);
        progressBar.setMax(player.getDuration());

        setAlbumImage(song.getFileResId());
        totalDuration.setText(formatTime(player.getDuration()));

        progressHandler.postDelayed(updateProgressBar, 500);

        player.setOnCompletionListener(mp -> switchTrack(1));
    }

    private void setAlbumImage(int resId) {
        try {
            if (resId >= 0) {
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                retriever.setDataSource(getResources().openRawResourceFd(resId).getFileDescriptor());
                byte[] art = retriever.getEmbeddedPicture();
                if (art != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
                    albumImage.setImageBitmap(bitmap);
                } else {
                    albumImage.setImageResource(android.R.drawable.ic_menu_gallery);
                }
                retriever.release();
            } else {
                albumImage.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } catch (Exception e) {
            albumImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }

    private void switchTrack(int direction) {
        currentTrackIndex = (currentTrackIndex + direction + trackList.size()) % trackList.size();
        loadAndPlayTrack(currentTrackIndex);
    }

    private void togglePlayPause() {
        if (player.isPlaying()) {
            player.pause();
            playPauseBtn.setImageResource(android.R.drawable.ic_media_play);
        } else {
            player.start();
            playPauseBtn.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    private void seekTrackBy(int milliseconds) {
        if (player != null) {
            int position = player.getCurrentPosition();
            player.seekTo(Math.max(0, Math.min(position + milliseconds, player.getDuration())));
        }
    }

    private Runnable updateProgressBar = new Runnable() {
        @Override
        public void run() {
            if (player != null) {
                progressBar.setProgress(player.getCurrentPosition());
                currentProgress.setText(formatTime(player.getCurrentPosition()));
                progressHandler.postDelayed(this, 500);
            }
        }
    };

    private String formatTime(int millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            progressHandler.removeCallbacks(updateProgressBar);
            player.release();
        }
    }
}
package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Creating a context object to start a new activity
        Context currentContext = HomeActivity.this;

        // Creating the intent to navigate to PlaylistActivity
        Intent playListIntent = new Intent(currentContext, PlaylistActivity.class);

        // Starting the new activity with the intent
        startActivity(playListIntent);

        // Finishing the current activity to avoid it staying in the back stack
        finish();
    }
}
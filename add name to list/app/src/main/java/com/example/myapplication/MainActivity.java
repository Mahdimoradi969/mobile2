package com.example.reload_basic_topics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Button actionButton = findViewById(R.id.actionButton);
        EditText inputField = findViewById(R.id.inputField);

        final Intent newActivityIntent = new Intent(HomeActivity.this, DetailActivity.class);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = inputField.getText().toString();
                newActivityIntent.putExtra("userInput", enteredText);
                startActivity(newActivityIntent);
            }
        });

    }
}

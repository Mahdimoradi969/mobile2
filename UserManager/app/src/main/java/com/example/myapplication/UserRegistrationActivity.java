package com.example.database;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class UserRegistrationScreen extends AppCompatActivity {

    private EditText nameInput;
    private EditText lastNameInput;
    private EditText emailInput;
    private Button submitButton;
    private Button showButton;
    private ListView userListView;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_screen);

        appDatabase = AppDatabase.getInstance(this);

        nameInput = findViewById(R.id.nameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        submitButton = findViewById(R.id.submitButton);
        showButton = findViewById(R.id.showButton);
        userListView = findViewById(R.id.userListView);

        submitButton.setOnClickListener(v -> addNewUser());
        showButton.setOnClickListener(v -> displayAllUsers());
    }

    private void addNewUser() {
        final String firstName = nameInput.getText().toString().trim();
        final String lastName = lastNameInput.getText().toString().trim();
        final String email = emailInput.getText().toString().trim();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            UserInfo user = new UserInfo(firstName, lastName, email);
            appDatabase.userOperations().addUser(user);
            runOnUiThread(() -> {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
                nameInput.setText("");
                lastNameInput.setText("");
                emailInput.setText("");
            });
        });
    }

    private void displayAllUsers() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<UserInfo> users = appDatabase.userOperations().fetchAllUsers();
            final List<String> userDisplayList = new ArrayList<>();
            for (UserInfo user : users) {
                userDisplayList.add(user.getId() + " - " + user.getFirstName() + " " + user.getLastName() + " (" + user.getUserEmail() + ")");
            }

            runOnUiThread(() -> {
                if (userDisplayList.isEmpty()) {
                    Toast.makeText(this, "No users found", Toast.LENGTH_SHORT).show();
                    userListView.setAdapter(null);
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_list_item_1,
                            userDisplayList
                    );
                    userListView.setAdapter(adapter);
                }
            });
        });
    }
}

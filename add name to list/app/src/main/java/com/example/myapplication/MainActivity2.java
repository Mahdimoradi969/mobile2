package com.example.reload_basic_topics;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<String> itemsList;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ListView listView = findViewById(R.id.itemsListView);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Set<String> savedItems = preferences.getStringSet("itemsSet", new HashSet<>());
        itemsList = new ArrayList<>(savedItems);

        String newItem = getIntent().getStringExtra("userInput");
        if (newItem != null && !newItem.isEmpty()) {
            itemsList.add(newItem);
        }

        preferences.edit().putStringSet("itemsSet", new HashSet<>(itemsList)).apply();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                itemsList
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = itemsList.get(position);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("saved_item", selectedItem);
                editor.apply();

                Toast.makeText(DetailActivity.this, "Item saved: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

    }
}

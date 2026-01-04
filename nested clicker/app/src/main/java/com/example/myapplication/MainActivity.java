package com.example.nestedclicker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        // استفاده از داده‌های تصادفی به جای داده‌های ثابت
        List<List<PersonData>> userDataLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            userDataLists.add(PersonData.getRandomUsers());
        }

        CustomAdapter customAdapter = new CustomAdapter(this, userDataLists, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void onItemClicked(PersonData personData) {
        Toast.makeText(this, "You clicked on " + personData.getName(), Toast.LENGTH_SHORT).show();
    }
}
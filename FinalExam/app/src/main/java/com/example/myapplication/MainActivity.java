package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TicketRepository repo;
    private TicketAdapter adapter;

    private EditText edtName;
    private Button btnAdd;
    private TextView txtCount;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new TicketRepository(this);

        edtName = findViewById(R.id.edtName);
        btnAdd = findViewById(R.id.btnAdd);
        txtCount = findViewById(R.id.txtCount);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new TicketAdapter(ticket ->
                repo.deleteTicket(ticket, list -> {
                    adapter.setItems(list);
                    updateCount();
                    Toast.makeText(this, "نوبت لغو شد", Toast.LENGTH_SHORT).show();
                })
        );

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ItemTouchHelper ith = new ItemTouchHelper(new DragDropCallback(adapter, () -> {
            repo.saveOrder(adapter.getCurrentItems(), list -> {
                adapter.setItems(list);
                updateCount();
                Toast.makeText(this, "ترتیب صف ذخیره شد", Toast.LENGTH_SHORT).show();
            });
        }));
        ith.attachToRecyclerView(recyclerView);

        btnAdd.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "نام را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }
            repo.addTicket(name, list -> {
                edtName.setText("");
                adapter.setItems(list);
                updateCount();
                Toast.makeText(this, "نوبت ثبت شد", Toast.LENGTH_SHORT).show();
            });
        });

        loadInitial();
    }

    private void loadInitial() {
        repo.getAll(list -> {
            adapter.setItems(list);
            updateCount();
        });
    }

    private void updateCount() {
        txtCount.setText("تعداد افراد در صف: " + adapter.getItemCount());
    }
}

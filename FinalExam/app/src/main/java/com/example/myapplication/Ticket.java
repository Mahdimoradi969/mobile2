package com.example.myapplication;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets")
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String clientName;

    // ترتیب در صف (0-based)
    public int position;

    public long createdAt;

    public Ticket(String clientName, int position, long createdAt) {
        this.clientName = clientName;
        this.position = position;
        this.createdAt = createdAt;
    }
}

package com.example.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TicketDao {

    @Query("SELECT * FROM tickets ORDER BY position ASC")
    List<Ticket> getAllOrdered();

    @Query("SELECT COALESCE(MAX(position), -1) FROM tickets")
    int getMaxPosition();

    @Insert
    long insert(Ticket ticket);

    @Update
    void update(Ticket ticket);

    @Delete
    void delete(Ticket ticket);

    @Query("UPDATE tickets SET position = :pos WHERE id = :id")
    void updatePosition(long id, int pos);
}

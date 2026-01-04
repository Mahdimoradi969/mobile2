package com.example.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserOperations {

    @Insert
    long addUser(UserInfo user);

    @Update
    int modifyUser(UserInfo user);

    @Delete
    int removeUser(UserInfo user);

    @Query("SELECT * FROM users")
    List<UserInfo> fetchAllUsers();

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    UserInfo fetchUserById(long userId);

    @Query("DELETE FROM users")
    void removeAllUsers();
}

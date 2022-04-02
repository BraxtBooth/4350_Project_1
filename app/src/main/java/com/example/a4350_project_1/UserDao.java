package com.example.a4350_project_1;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTable userTable);

    @Query("DELETE FROM user_table")
    void deleteAll();

//    @Query("SELECT * from user_table ORDER BY userdata ASC")
//    LiveData<List<UserTable>> getAll();
}

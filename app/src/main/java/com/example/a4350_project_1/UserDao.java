package com.example.a4350_project_1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTable userTable);

    @Query("DELETE FROM user_table")
    void deleteAll();

//    @Query("SELECT * from user_table ORDER BY userdata ASC")
//    LiveData<List<UserTable>> getAll();

//    @Query("SELECT * from user_table WHERE name=:name")
//    LiveData<UserTable> getUser(String name);

    @Query("SELECT * from user_table WHERE selected=1 LIMIT 1")
    LiveData<UserTable> getUser();

    @Query("UPDATE user_table SET email=:email, name=:name, age=:age, location=:location, feet=:feet, " +
            "inches=:inches, weight=:weight, sex=:sex, goal=:goal, activity=:activity, " +
            "goalChange=:goalChange WHERE selected=1")
    void updateUser(String email, String name, int age, String location, int feet, int inches,
                    int weight, int sex, int goal, int activity, int goalChange);

    @Query("UPDATE user_table SET imageURI=:imageURI WHERE selected=1")
    void updateUserImageURI(String imageURI);

    @Query("SELECT * from user_table LIMIT 1")
    LiveData<UserTable> getAllUsers();

//    @Update();
}

package com.example.a4350_project_1;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserTable user);

    @Delete
    void delete(UserTable user);

    @Query("DELETE FROM user_table")
    void deleteAll();

//    @Query("SELECT * from user_table ORDER BY userdata ASC")
//    LiveData<List<UserTable>> getAll();

//    @Query("SELECT * from user_table WHERE name=:name")
//    LiveData<UserTable> getUser(String name);

    @Query("SELECT * from user_table WHERE selected=1 LIMIT 1")
    LiveData<UserTable> getUser();

    @Query("SELECT * from user_table WHERE name=:name LIMIT 1")
    LiveData<UserTable> findUserByName(String name);

    @Query("SELECT * from user_table WHERE email=:email LIMIT 1")
    LiveData<UserTable> findUserByEmail(String email);

    @Query("SELECT * from user_table WHERE age=:age LIMIT 1")
    LiveData<UserTable> findUserByAge(int age);

    @Query("UPDATE user_table SET email=:email, name=:name, age=:age, location=:location, feet=:feet, " +
            "inches=:inches, weight=:weight, sex=:sex, goal=:goal, activity=:activity, " +
            "goalChange=:goalChange WHERE selected=1")
    void updateUser(String email, String name, int age, String location, int feet, int inches,
                    int weight, int sex, int goal, int activity, int goalChange);

    @Query("UPDATE user_table SET imageURI=:imageURI WHERE selected=1")
    void updateUserImageURI(String imageURI);

    @Query("UPDATE user_table SET steps=:steps WHERE selected=1")
    void updateUserSteps(String steps);

    @Query("UPDATE user_table SET dates=:dates WHERE selected=1")
    void updateUserDates(String dates);

    @Query("SELECT * from user_table LIMIT 1")
    LiveData<UserTable> getAllUsers();

//    @Update();
}

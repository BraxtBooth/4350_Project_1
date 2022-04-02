package com.example.a4350_project_1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserTable {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int userID;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "age")
    private int age;

    @NonNull
    @ColumnInfo(name = "location")
    private String location;

    @NonNull
    @ColumnInfo(name = "feet")
    private int feet;

    @NonNull
    @ColumnInfo(name = "inches")
    private int inches;

    @NonNull
    @ColumnInfo(name = "weight")
    private int weight;

    @NonNull
    @ColumnInfo(name = "sex")
    private String sex;

    @NonNull
    @ColumnInfo(name = "goal")
    private String goal;

    @NonNull
    @ColumnInfo(name = "activity")
    private String activity;

    @NonNull
    @ColumnInfo(name = "goalChange")
    private int goalChange;

    public UserTable(@NonNull String name, @NonNull int age, @NonNull String location, @NonNull int feet,
                     @NonNull int inches, @NonNull int weight, @NonNull String sex, @NonNull String goal,
                     @NonNull String activity, @NonNull int goalChange){
        setName(name);
        setAge(age);
        setLocation(location);
        setFeet(feet);
        setInches(inches);
        setWeight(weight);
        setSex(sex);
        setGoal(goal);
        setActivity(activity);
        setGoalChange(goalChange);

    }

    public void setName(String name){
        this.name = name;
    }
    public void setAge(int age){ this.age = age; }
    public void setLocation(String location){
        this.location = location;
    }
    public void setFeet(int feet){ this.feet = feet; }
    public void setInches(int inches){ this.inches = inches; }
    public void setWeight(int weight){ this.weight = weight; }
    public void setSex(String sex){
        this.sex = sex;
    }
    public void setGoal(String goal){ this.goal = goal; }
    public void setActivity(String activity){
        this.activity = activity;
    }
    public void setGoalChange(int goalChange){ this.goalChange = goalChange; }


    public String getName(){
        return name;
    }
    public int getAge(){ return age; }
    public String getLocation(){
        return location;
    }
    public int getFeet(){ return feet; }
    public int getInches(){ return inches; }
    public int getWeight(){ return weight; }
    public String getSex(){
        return sex;
    }
    public String getGoal(){
        return goal;
    }
    public String getActivity(){
        return activity;
    }
    public int getGoalChange(){ return goalChange; }

}

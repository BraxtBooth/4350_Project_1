package com.example.a4350_project_1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @ColumnInfo(name = "selected")
    private boolean selected;

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
    @ColumnInfo(name = "sex") // 0 for male, 1 for female, 2 for ...
    private int sex;

    @NonNull
    @ColumnInfo(name = "goal") //
    private int goal;

    @NonNull
    @ColumnInfo(name = "activity") // 0 for sedentary, 1 for active
    private int activity;

    @NonNull
    @ColumnInfo(name = "goalChange")
    private int goalChange;

    @NonNull
    @ColumnInfo(name = "imageURI")
    private String imageURI;

    @NonNull
    @ColumnInfo(name = "steps")
    private String steps;

    @NonNull
    @ColumnInfo(name = "dates")
    private String dates;

    public UserTable(@NonNull String email, @NonNull boolean selected, @NonNull String name, @NonNull int age, @NonNull String location, @NonNull int feet,
                     @NonNull int inches, @NonNull int weight, @NonNull int sex, @NonNull int goal,
                     @NonNull int activity, @NonNull int goalChange, @NonNull String imageURI,  @NonNull String steps,  @NonNull String dates){
        setEmail(email);
        setSelected(selected);
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
        setImageURI(imageURI);
        setSteps(steps);
        setDates(dates);
    }

    public void setEmail(String email){
        this.email = email;
    }
    public void setSelected(boolean selected){
        this.selected = selected;
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
    public void setSex(int sex){
        this.sex = sex;
    }
    public void setGoal(int goal){ this.goal = goal; }
    public void setActivity(int activity){
        this.activity = activity;
    }
    public void setGoalChange(int goalChange){ this.goalChange = goalChange; }
    public void setImageURI(String imageURI) { this.imageURI = imageURI; }
    public void setSteps(String steps){ this.steps = steps; }
    public void setDates(String dates) { this.dates = dates; }

    public String getEmail(){
        return email;
    }
    public boolean getSelected(){
        return selected;
    }
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
    public int getSex(){
        return sex;
    }
    public int getGoal(){
        return goal;
    }
    public int getActivity(){
        return activity;
    }
    public int getGoalChange(){ return goalChange; }
    public String getImageURI(){
        return imageURI;
    }
    public String getSteps(){ return steps; }
    public String getDates(){
        return dates;
    }
}

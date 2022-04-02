package com.example.a4350_project_1;


public class UserTableBuilder {
    private int userID;
    private String name;
    private int age;
    private String location;
    private int feet;
    private int inches;
    private int weight;
    private String sex;
    private String goal;
    private String activity;
    private int goalChange;

    public UserTableBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserTableBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserTableBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public UserTableBuilder setFeet(int feet) {
        this.feet = feet;
        return this;
    }

    public UserTableBuilder setInches(int inches) {
        this.inches = inches;
        return this;
    }

    public UserTableBuilder setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public UserTableBuilder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public UserTableBuilder setGoal(String goal) {
        this.goal = goal;
        return this;
    }

    public UserTableBuilder setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public UserTableBuilder setGoalChange(int goalChange) {
        this.goalChange = goalChange;
        return this;
    }

    public UserTable createUserTable() {
        return new UserTable(name, age, location, feet, inches, weight, sex, goal, activity, goalChange);
    }
}

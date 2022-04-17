package com.example.a4350_project_1;


public class UserTableBuilder {
    private String email;
    private boolean selected;
    private String name;
    private int age;
    private String location;
    private int feet;
    private int inches;
    private int weight;
    private int sex;
    private int goal;
    private int activity;
    private int goalChange;
    private String imageURI;

    public UserTableBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserTableBuilder setSelected(boolean isSelected) {
        this.selected = isSelected;
        return this;
    }

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

    public UserTableBuilder setSex(int sex) {
        this.sex = sex;
        return this;
    }

    public UserTableBuilder setGoal(int goal) {
        this.goal = goal;
        return this;
    }

    public UserTableBuilder setActivity(int activity) {
        this.activity = activity;
        return this;
    }

    public UserTableBuilder setGoalChange(int goalChange) {
        this.goalChange = goalChange;
        return this;
    }

    public UserTableBuilder setImageURI(String imageURI) {
        this.imageURI = imageURI;
        return this;
    }

    public UserTable createUserTable() {
        return new UserTable(email, selected, name, age, location, feet, inches, weight, sex, goal, activity, goalChange, imageURI);
    }
}

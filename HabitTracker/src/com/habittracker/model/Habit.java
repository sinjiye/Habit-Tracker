package com.habittracker.model;

/**
 * 사용자가 등록한 습관에 대한 정보를 저장하는 클래스이다.
 */
public class Habit {

    private int habitID;
    private String userID;
    private String name;
    private String description;
    private String createdDate; // yyyy-MM-dd

    public Habit(int habitID, String userID, String name, String description, String createdDate) {
        this.habitID = habitID;
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.createdDate = createdDate;
    }

    public int getHabitID() {
        return habitID;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}

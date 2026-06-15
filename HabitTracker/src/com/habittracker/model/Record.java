package com.habittracker.model;

/**
 * 각 습관의 날짜별 수행 기록을 저장하는 클래스이다.
 */
public class Record {

    private int recordID;
    private int habitID;
    private String date; // yyyy-MM-dd
    private boolean isDone;

    public Record(int recordID, int habitID, String date, boolean isDone) {
        this.recordID = recordID;
        this.habitID = habitID;
        this.date = date;
        this.isDone = isDone;
    }

    public int getRecordID() {
        return recordID;
    }

    public int getHabitID() {
        return habitID;
    }

    public String getDate() {
        return date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }
}

package com.habittracker.logic;

import com.habittracker.data.Database;
import com.habittracker.model.Habit;
import com.habittracker.model.Record;

import java.time.LocalDate;
import java.util.List;

/**
 * 습관 등록(registerHabit), 삭제(deleteHabit), 수행 체크(checkHabit)와
 * 같은 회원의 습관 관리 기능을 처리하는 클래스이다.
 */
public class HabitService {

    private final Database database;

    public HabitService() {
        this.database = Database.getInstance();
    }

    /**
     * 동일한 이름의 습관이 이미 등록되어 있는지 확인한다.
     */
    public boolean isDuplicateName(String userID, String name) {
        for (Habit h : database.getHabitsByUser(userID)) {
            if (h.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 새로운 습관을 등록하는 메소드이다.
     * 이름이 비어있거나 이미 존재하는 이름이면 null을 반환한다.
     */
    public Habit registerHabit(String userID, String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        if (isDuplicateName(userID, name.trim())) {
            return null;
        }
        int newID = database.generateHabitID();
        String today = LocalDate.now().toString();
        Habit habit = new Habit(newID, userID, name.trim(), description.trim(), today);
        database.saveHabit(habit);
        return habit;
    }

    /**
     * 기존 습관을 삭제하는 메소드이다. (수행 기록도 함께 삭제된다)
     */
    public void deleteHabit(int habitID) {
        database.deleteHabit(habitID);
    }

    /**
     * 특정 날짜의 습관 수행 여부를 체크(토글)하는 메소드이다.
     * 이미 체크된 항목을 다시 클릭하면 수행 기록을 false로 변경한다.
     *
     * @return 변경 후의 isDone 상태
     */
    public boolean checkHabit(int habitID, String date) {
        Record record = database.getRecord(habitID, date);
        if (record == null) {
            record = new Record(database.generateRecordID(), habitID, date, true);
        } else {
            record.setDone(!record.isDone());
        }
        database.saveRecord(record);
        return record.isDone();
    }

    public List<Habit> getHabitsByUser(String userID) {
        return database.getHabitsByUser(userID);
    }

    public List<Record> getRecords(int habitID) {
        return database.getRecords(habitID);
    }
}

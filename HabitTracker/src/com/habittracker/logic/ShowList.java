package com.habittracker.logic;

import com.habittracker.data.Database;
import com.habittracker.model.Habit;
import com.habittracker.model.Record;

import java.time.LocalDate;
import java.util.List;

/**
 * 사용자의 습관 목록과 각 습관의 오늘 수행 상태를 조회하는 클래스이다.
 */
public class ShowList {

    private final Database database;
    private List<Habit> habitList;
    private String currentDate;

    public ShowList() {
        this.database = Database.getInstance();
    }

    /**
     * 사용자의 습관 목록을 Database로부터 불러온다.
     */
    public void loadHabitList(String userID) {
        this.habitList = database.getHabitsByUser(userID);
        this.currentDate = LocalDate.now().toString();
    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * 오늘 날짜 기준으로 수행 완료(체크)된 습관의 개수를 반환한다.
     */
    public int getCheckedCount() {
        int count = 0;
        if (habitList == null) {
            return 0;
        }
        for (Habit h : habitList) {
            Record r = database.getRecord(h.getHabitID(), currentDate);
            if (r != null && r.isDone()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 오늘 날짜 기준 해당 습관의 수행 여부를 반환한다.
     */
    public boolean isCheckedToday(int habitID) {
        Record r = database.getRecord(habitID, currentDate);
        return r != null && r.isDone();
    }
}

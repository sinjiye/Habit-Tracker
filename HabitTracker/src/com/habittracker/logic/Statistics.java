package com.habittracker.logic;

import com.habittracker.model.Record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 습관의 달성률과 연속 성공일(Streak)을 계산하는 클래스이다.
 */
public class Statistics {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private final int habitID;
    private double achievementRate;
    private int maxStreak;
    private int currentStreak;
    private int totalRecords;
    private int doneCount;

    public Statistics(int habitID) {
        this.habitID = habitID;
    }

    public int getHabitID() {
        return habitID;
    }

    /**
     * 전체 기록을 기반으로 습관 달성률(%)을 계산하는 메소드이다.
     * 달성률 = (수행한 날짜 수 / 전체 기록 날짜 수) * 100
     */
    public double calculateAchievementRate(List<Record> records) {
        totalRecords = records.size();
        doneCount = 0;
        for (Record r : records) {
            if (r.isDone()) {
                doneCount++;
            }
        }
        if (totalRecords == 0) {
            achievementRate = 0.0;
        } else {
            achievementRate = (doneCount * 100.0) / totalRecords;
        }
        return achievementRate;
    }

    /**
     * 수행 기록을 날짜순으로 분석하여 최대 연속 성공일과
     * 현재(가장 최근까지의) 연속 성공일을 계산하는 메소드이다.
     * 반환값은 현재 연속 성공일이다.
     */
    public int calculateStreak(List<Record> records) {
        List<Record> sorted = new ArrayList<>(records);
        sorted.sort(Comparator.comparing(Record::getDate));

        int max = 0;
        int running = 0;
        LocalDate prevDate = null;

        for (Record r : sorted) {
            LocalDate date = LocalDate.parse(r.getDate(), FORMAT);
            if (r.isDone()) {
                if (prevDate != null && date.equals(prevDate.plusDays(1))) {
                    running++;
                } else {
                    running = 1;
                }
                if (running > max) {
                    max = running;
                }
            } else {
                running = 0;
            }
            prevDate = date;
        }

        // 현재 연속 성공일: 가장 마지막 기록부터 연속으로 true인 구간의 길이
        int current = 0;
        for (int i = sorted.size() - 1; i >= 0; i--) {
            if (sorted.get(i).isDone()) {
                current++;
            } else {
                break;
            }
        }

        this.maxStreak = max;
        this.currentStreak = current;
        return currentStreak;
    }

    public double getAchievementRate() {
        return achievementRate;
    }

    public int getMaxStreak() {
        return maxStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getDoneCount() {
        return doneCount;
    }
}

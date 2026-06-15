package com.habittracker.data;

import com.habittracker.model.Habit;
import com.habittracker.model.Manager;
import com.habittracker.model.Member;
import com.habittracker.model.Record;
import com.habittracker.model.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 시스템 내 유일하게 사용자 데이터에 직접 접근할 수 있는 클래스이다.
 * 회원 정보, 습관 데이터, 수행 기록을 텍스트 파일(.txt)에 저장하고 조회한다.
 *
 * - members.txt : userID,password,nickname,role
 * - habits.txt  : habitID,userID,name,description,createdDate
 * - records.txt : recordID,habitID,date,isDone
 */
public class Database {

    private static final String DATA_DIR = "data";
    private static final String MEMBER_FILE = DATA_DIR + File.separator + "members.txt";
    private static final String HABIT_FILE = DATA_DIR + File.separator + "habits.txt";
    private static final String RECORD_FILE = DATA_DIR + File.separator + "records.txt";

    private static final String DELIM = ",";

    private static Database instance;

    private final Map<String, User> userMap = new LinkedHashMap<>();
    private final Map<Integer, Habit> habitMap = new LinkedHashMap<>();
    private final Map<Integer, Record> recordMap = new LinkedHashMap<>();

    private int nextHabitID = 1;
    private int nextRecordID = 1;

    private Database() {
        loadAll();
        if (userMap.isEmpty()) {
            createDefaultData();
        }
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // ─────────────────────────────────────────────────────────
    // User (Member / Manager)
    // ─────────────────────────────────────────────────────────

    public User getUser(String userID) {
        return userMap.get(userID);
    }

    public boolean checkDuplicateID(String userID) {
        return userMap.containsKey(userID);
    }

    public void saveUser(User user) {
        userMap.put(user.getUserID(), user);
        writeUsers();
    }

    public void deleteUser(String userID) {
        userMap.remove(userID);
        // 해당 사용자의 습관과 기록도 함께 삭제한다.
        List<Habit> userHabits = getHabitsByUser(userID);
        for (Habit h : userHabits) {
            deleteHabit(h.getHabitID());
        }
        writeUsers();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    // ─────────────────────────────────────────────────────────
    // Habit
    // ─────────────────────────────────────────────────────────

    public Habit getHabit(int habitID) {
        return habitMap.get(habitID);
    }

    public List<Habit> getHabitsByUser(String userID) {
        List<Habit> result = new ArrayList<>();
        for (Habit h : habitMap.values()) {
            if (h.getUserID().equals(userID)) {
                result.add(h);
            }
        }
        return result;
    }

    public int generateHabitID() {
        return nextHabitID++;
    }

    public void saveHabit(Habit habit) {
        habitMap.put(habit.getHabitID(), habit);
        writeHabits();
    }

    public void deleteHabit(int habitID) {
        habitMap.remove(habitID);
        // 해당 습관의 모든 수행 기록도 함께 삭제한다.
        List<Integer> toRemove = new ArrayList<>();
        for (Record r : recordMap.values()) {
            if (r.getHabitID() == habitID) {
                toRemove.add(r.getRecordID());
            }
        }
        for (Integer id : toRemove) {
            recordMap.remove(id);
        }
        writeHabits();
        writeRecords();
    }

    // ─────────────────────────────────────────────────────────
    // Record
    // ─────────────────────────────────────────────────────────

    public List<Record> getRecords(int habitID) {
        List<Record> result = new ArrayList<>();
        for (Record r : recordMap.values()) {
            if (r.getHabitID() == habitID) {
                result.add(r);
            }
        }
        return result;
    }

    public Record getRecord(int habitID, String date) {
        for (Record r : recordMap.values()) {
            if (r.getHabitID() == habitID && r.getDate().equals(date)) {
                return r;
            }
        }
        return null;
    }

    public int generateRecordID() {
        return nextRecordID++;
    }

    public void saveRecord(Record record) {
        recordMap.put(record.getRecordID(), record);
        writeRecords();
    }

    // ─────────────────────────────────────────────────────────
    // File I/O
    // ─────────────────────────────────────────────────────────

    private void loadAll() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadUsers();
        loadHabits();
        loadRecords();
    }

    private void loadUsers() {
        File file = new File(MEMBER_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split(DELIM);
                if (tokens.length < 4) {
                    continue;
                }
                String id = tokens[0].trim();
                String pw = tokens[1].trim();
                String nickname = tokens[2].trim();
                String role = tokens[3].trim();

                User user;
                if (role.equalsIgnoreCase("MANAGER")) {
                    user = new Manager(id, pw, nickname);
                } else {
                    user = new Member(id, pw, nickname);
                }
                userMap.put(id, user);
            }
        } catch (IOException e) {
            System.err.println("회원 정보 로드 중 오류: " + e.getMessage());
        }
    }

    private void loadHabits() {
        File file = new File(HABIT_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split(DELIM, 5);
                if (tokens.length < 5) {
                    continue;
                }
                int habitID = Integer.parseInt(tokens[0].trim());
                String userID = tokens[1].trim();
                String name = tokens[2].trim();
                String description = tokens[3].trim();
                String createdDate = tokens[4].trim();

                Habit habit = new Habit(habitID, userID, name, description, createdDate);
                habitMap.put(habitID, habit);
                if (habitID >= nextHabitID) {
                    nextHabitID = habitID + 1;
                }
            }
        } catch (IOException e) {
            System.err.println("습관 정보 로드 중 오류: " + e.getMessage());
        }
    }

    private void loadRecords() {
        File file = new File(RECORD_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split(DELIM);
                if (tokens.length < 4) {
                    continue;
                }
                int recordID = Integer.parseInt(tokens[0].trim());
                int habitID = Integer.parseInt(tokens[1].trim());
                String date = tokens[2].trim();
                boolean isDone = Boolean.parseBoolean(tokens[3].trim());

                Record record = new Record(recordID, habitID, date, isDone);
                recordMap.put(recordID, record);
                if (recordID >= nextRecordID) {
                    nextRecordID = recordID + 1;
                }
            }
        } catch (IOException e) {
            System.err.println("기록 정보 로드 중 오류: " + e.getMessage());
        }
    }

    private void writeUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEMBER_FILE))) {
            for (User u : userMap.values()) {
                String role = u.isManager() ? "MANAGER" : "MEMBER";
                bw.write(u.getUserID() + DELIM + u.getPassword() + DELIM
                        + u.getNickname() + DELIM + role);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("회원 정보 저장 중 오류: " + e.getMessage());
        }
    }

    private void writeHabits() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HABIT_FILE))) {
            for (Habit h : habitMap.values()) {
                bw.write(h.getHabitID() + DELIM + h.getUserID() + DELIM
                        + h.getName() + DELIM + h.getDescription() + DELIM
                        + h.getCreatedDate());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("습관 정보 저장 중 오류: " + e.getMessage());
        }
    }

    private void writeRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RECORD_FILE))) {
            for (Record r : recordMap.values()) {
                bw.write(r.getRecordID() + DELIM + r.getHabitID() + DELIM
                        + r.getDate() + DELIM + r.isDone());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("기록 정보 저장 중 오류: " + e.getMessage());
        }
    }

    /**
     * 데이터 파일이 존재하지 않을 때 사용할 기본(예시) 데이터를 생성한다.
     * - 관리자 계정 1개 (admin / 1234)
     * - 일반 사용자 계정 1개 (user1 / 1234)
     */
    private void createDefaultData() {
        User admin = new Manager("admin", "1234", "관리자");
        User member = new Member("user1", "1234", "홍길동");
        userMap.put(admin.getUserID(), admin);
        userMap.put(member.getUserID(), member);
        writeUsers();
        writeHabits();
        writeRecords();
    }
}

# 당신을 바꾸는 습관 (Habit Tracker)

Conceptualization / Analysis / Design 문서를 기반으로 구현한
Java Swing 기반 습관 관리 프로그램입니다.

---

## 1. 실행 방법

### (1) JAR 파일로 바로 실행 (Java 11 이상 필요)

```
java -jar HabitTracker.jar
```

실행하면 `data/` 폴더가 자동으로 생성되고, 기본 계정이 만들어집니다.

| 구분   | ID     | PW   | 비고            |
|--------|--------|------|-----------------|
| 관리자 | admin  | 1234 | 회원 관리 가능  |
| 일반회원 | user1 | 1234 | 닉네임: 홍길동  |

### (2) 소스 직접 컴파일

```
cd src
javac -encoding UTF-8 -d ../build $(find . -name "*.java")
cd ..
java -cp build com.habittracker.ui.AppFrame
```

---

## 2. 패키지 구조 (Design 문서의 Class Diagram 대응)

```
com.habittracker
├── model
│   ├── User.java         - 추상 클래스 (공통 속성: userID, password, nickname)
│   ├── Member.java        - 일반 사용자 (User 상속)
│   ├── Manager.java        - 관리자 (Member 상속, 회원 관리 권한)
│   ├── Habit.java          - 습관 정보 (habitID, name, description, createdDate)
│   └── Record.java         - 날짜별 수행 기록 (habitID, date, isDone)
│
├── data
│   └── Database.java       - 파일(.txt) 기반 데이터 저장소 (싱글톤)
│                              data/members.txt, habits.txt, records.txt
│
├── logic
│   ├── Login.java          - loginCheck / isManager / registerMember / logout
│   ├── HabitService.java   - registerHabit / deleteHabit / checkHabit
│   ├── ShowList.java        - 습관 목록 및 오늘 체크 현황 조회
│   └── Statistics.java      - 달성률 / 연속 성공일(Streak) 계산
│
└── ui
    ├── AppFrame.java         - 메인 프레임 (CardLayout 화면 전환)
    ├── LoginPanel.java        - 로그인 화면
    ├── RegisterPanel.java     - 회원가입 화면
    ├── MainPanel.java          - 메인(습관 목록) 화면
    ├── HabitDialog.java        - 습관 추가 다이얼로그
    ├── StatisticsPanel.java    - 통계 화면 (달성률, 연속 성공일, 14일 현황)
    ├── ManageMemberPanel.java  - 회원 관리 화면 (관리자)
    ├── RoundButton.java        - 공통 버튼 컴포넌트
    └── UIConstants.java        - 색상/폰트 상수
```

---

## 3. 구현된 Use Case (Conceptualization 문서 대응)

| # | Use Case          | 구현 위치 |
|---|-------------------|-----------|
| 1 | Register Member   | RegisterPanel + Login.registerMember() |
| 2 | Log in            | LoginPanel + Login.loginCheck() |
| 3 | Log out           | MainPanel 로그아웃 버튼 + Login.logout() |
| 4 | Register Habit    | HabitDialog + HabitService.registerHabit() |
| 5 | Delete Habit      | MainPanel 삭제 버튼 + HabitService.deleteHabit() |
| 6 | View Habit List   | MainPanel + ShowList.loadHabitList() |
| 7 | Check Habit       | MainPanel 체크박스 + HabitService.checkHabit() |
| 8 | View Statistics   | StatisticsPanel + Statistics 클래스 |
| 9 | Manage Member     | ManageMemberPanel (관리자 전용) |

---

## 4. 데이터 저장 형식

`data/` 폴더에 CSV 형태의 텍스트 파일로 저장됩니다 (프로그램 종료/재시작
후에도 데이터가 유지됩니다).

- `members.txt` : `userID,password,nickname,role(MEMBER/MANAGER)`
- `habits.txt`  : `habitID,userID,name,description,createdDate`
- `records.txt` : `recordID,habitID,date,isDone(true/false)`

---

## 5. 화면 흐름 (State Machine Diagram 대응)

```
[로그인 화면] --회원가입--> [회원가입 화면] --완료--> [로그인 화면]
[로그인 화면] --로그인 성공--> [메인 화면(습관 목록)]
[메인 화면] --습관 추가--> (다이얼로그) --저장--> [메인 화면]
[메인 화면] --통계 버튼--> [통계 화면] --목록으로--> [메인 화면]
[메인 화면] --회원 관리(관리자만)--> [회원 관리 화면] --메인으로--> [메인 화면]
[메인 화면] --로그아웃--> [로그인 화면]
```

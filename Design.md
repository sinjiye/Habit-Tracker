# 당신을 바꾸는 습관

# 3. Design

Student No: 22421575

Name: 신지예

E-mail: sinjiye0506@naver.com

깃허브: https://github.com/sinjiye/Habit-Tracker


## Revision history
|Revision date|Version #|Description|Author|
|:------:|:---:|:-------:|:------:|
| 6/5/2026 | 1.00 | 초안 | 신지예 |

---

## Contents

### 1. Introduction
### 2. Class diagram
### 3. Sequence diagram
### 4. State machine diagram
### 5. Implementation requirements
### 6. Glossary
### 7. References

---

## 1. Introduction

### 1) Summary
'당신을 바꾸는 습관'은 사용자가 자신의 습관을 등록하고, 수행 여부를 기록하며, 달성률과 연속 성공일을 확인할 수 있는 습관 관리 시스템이다.

본 문서는 실제 구현에 필요한 클래스 구조와 객체 간 상호작용을 설계한다. 또한 주요 가능에 대한 Sequence Diagram과 State Machine Diagram을 정의하여 구현 단계에서 일괄된 구조를 유지하도록한다.

### 2) Important Points of Design
- 사용자와 관리자의 권한을 구분한다.
- 습관 데이터와 회원 데이터를 독립적으로 관리한다.
- 모든 데이터 접근을 Database 클래스를 통해 수행된다.
- 객체지향 설계를 기반으로 기능별 클래스를 분리한다.
- 향후 가능 확장이 가능하도록 설계한다.

---

## 2. Class diagram

![Class diagram](class_diagram.png)


---

## 3. Sequence diagram



---

## 4. State machine diagram



---

## 5. Implementation requirements



---

## 6. Glossary

| Term | Description |
|------|-------------|
| Habit | 사용자가 반복적으로 수행하려는 행동 |
| Habit Record | 특정 날짜에 수행한 습관 기록 |
| Streak | 연속으로 습관을 수행한 일수 |
| Statistics | 습관 수행 결과를 분석한 데이터 |
| User | 프로그램을 사용하는 사용자 |
| Storage | 데이터를 저장하는 시스템 |
| 메인화면 | 시스템에 처음 접속하였을 때 볼 수 있는 화면이다. 습관 리스트를 볼 수 있다. |
| 데이터베이스 | 사용자들의 회원 정보와 습관 데이터들이 저장되어 있는 곳이다. |

---

## 7. References

[Design] Example 1

[Design] Example 2

[Design] Example 3

[Design] Example 4

[Design] Example 5

[Design] Example 6

[Design] Example 7

[Design] Example 8

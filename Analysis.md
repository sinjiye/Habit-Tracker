# 당신을 바꾸는 습관

# 2. Analysis

Student No: 22421575

Name: 신지예

E-mail: sinjiye0506@naver.com

## Revision history
|Revision date|Version #|Description|Author|
|------|---|-------|------|
|||||

## Contents

### 1. Introduction
### 2. Use case analysis
### 3. Domain analysis
### 4. User Interface prototype
### 5. Glossary
### 6. References



## 1. Introduction

### 1) Summary
현대 사회에서 많은 사람들을 자기계발을 위해 다양한 습관을 형성하려고 노력한다. 하지만 습관을 지속적으로 유지하고 관리하는 것은 쉽지 않으며, 이를 체계적으로 쉽게 기록하고 분석하는 도구가 부족한 경우도 있다. 이에 따라 본 프로젝트에서는 사용자가 자신의 습관을 등록하고, 수행 여부를 기록하며, 이를 기반으로 통계를 확인할 수 있는 '당신을 바꾸는 습관'을 개발하고자 한다.

### 2) Goals
'당신을 바꾸는 습관'은 사용자의 습관 형성을 돕는 것이 목적이다. 꾸준히 실행할 수 있도록 통계를 보여주며 동기를 부여해준다. 다양한 사용자들이 사용하기 쉬운 인터페이스로 진입장벽은 낮추고 사용자들의 만족도는 높일 것이다.


## 2. Use case analysis

### 1) Use case diagram



### 2) Use case description

Use Case #1 : Register

GENERAL CHARACTERISTICS
|Summary|사용자가 이 시스템을 처음 사용할 때 사용한다.|
|-------|-----------------------------|
|Scope|당신을 바꾸는 습관|
|Level|User Level|
|Author|신지예|
|Last Update|2026-04-29|
|Status|Analysis|
|Primary Actor|User, Manager|
|Preconditions|시스템이 실행되어 있어야 한다.|
|Trigger|메인 화면에서 회원가입 버튼을 누른 경우|
|Success Post Condition|회원가입이 완료되어 로그인 창이 뜬다.|
|Faild Post Condidtion|회원가입이 되지 않는다.|

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1|사용자가 회원가입 버튼을 누른다.|
|2|ID와 PW를 적는 창이 뜬다.|
|3|사용자가 ID와 PW를 기입한다.|
|4|회원 가입이 완료되어 로그인 창으로 이동한다.|

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|3|3a. 기입한 아이디가 이미 존재하는 경우|
||3a.1. 이미 존재하는 아이디라는 메세지를 띄워준다.|
||3a.2 ID와 PW를 입력하는 페이지로 돌아간다.|
||3b. ID와 PW를 입력하지 않은 경우|
||3b.1. ID 또는 PW가 입력되지 않았다는 메세지를 띄워준다.|
||3b.2. ID와 PW를 입력하는 페이지도 돌아간다.|

RELATED INFORMATION
|Performance|< 3Seconds|
|----------|------------------------|
|Frequency|Variable|
|Concurrency|None|
|Due Date|2026-05-01|



Use Case #2 : Log in

GENERAL CHARACTERISTICS
|Summary|시스템의 기능들을 사용하기 위해 회원 인증을 받을 때 사용한다.|
|-------|-----------------------------|
|Scope|당신을 바꾸는 습관|
|Level|User Level|
|Author|신지예|
|Last Update|2026-04-29|
|Status|Analysis|
|Primary Actor|User, Manager|
|Preconditions|시스템이 실행되어 있어야 한다. 그리고 회원가입이 되어있어야 한다.|
|Trigger|로그인 버튼을 누른다.|
|Success Post Condition|저장되어 있는 회원임이 인증되어서 시스템을 사용할 수 있다.|
|Faild Post Condidtion|로그인 실패 메세지가 출력되고 로그인 창으로 돌아간다.|

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1|사용자가 등록된 ID와 PW를 입력한다.|
|2|사용자가 로그인 버튼을 누른다.|
|3|시스템이 데이터베이스와 대조하여 일치함을 확인한다.|
|4|시스템이 해당 사용자의 데이터를 불러온다.|
|5|메인 화면이 출력된다.|

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #3 : Log out

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #4 : Register Habit

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #5 : Delete Habit

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #6 : Wiew Habit List

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #7 : Check Habit

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||


Use Case #8 : View Statistics

GENERAL CHARACTERISTICS
|Summary||
|-------|-----------------------------|
|Scope||
|Level||
|Author||
|Last Update||
|Status||
|Primary Actor||
|Preconditions||
|Trigger||
|Success Post Condition||
|Faild Post Condidtion||

MAIN SUCCESS SCENARIO
|Step|Action|
|-------|----------------------------|
|1||
|2||

EXTENSION SCENARIO
|Step|Branching Action|
|--------|---------------------------|
|||

RELATED INFORMATION
|Performance||
|----------|------------------------|
|Frequency||
|Concurrency||
|Due Date||

## 3. Domain analysis



## 4. User Interface prototype



## 5. Grossary



## 6. References



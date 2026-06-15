package com.habittracker.model;

/**
 * User를 상속받는 일반 사용자 클래스이다.
 * 습관 등록, 삭제, 체크, 통계 조회 등의 기능을 사용할 수 있다.
 */
public class Member extends User {

    public Member(String userID, String password, String nickname) {
        super(userID, password, nickname);
    }

    @Override
    public boolean isManager() {
        return false;
    }
}

package com.habittracker.model;

/**
 * Member의 모든 기능을 사용할 수 있으며, 추가로 회원 정보를
 * 관리하는 기능(회원 조회, 삭제)을 가지는 관리자 클래스이다.
 *
 * Manager는 Member의 모든 권한(습관 등록/삭제/체크/통계 조회)을
 * 그대로 사용할 수 있어야 하므로 Member를 상속받는다.
 */
public class Manager extends Member {

    public Manager(String userID, String password, String nickname) {
        super(userID, password, nickname);
    }

    @Override
    public boolean isManager() {
        return true;
    }
}

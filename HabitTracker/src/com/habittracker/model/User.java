package com.habittracker.model;

/**
 * 시스템 사용자의 공통 속성을 정의하는 추상 클래스이다.
 * Member와 Manager가 이 클래스를 상속받는다.
 */
public abstract class User {

    private String userID;
    private String password;
    private String nickname;

    public User(String userID, String password, String nickname) {
        this.userID = userID;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 이 사용자가 관리자 권한을 가지는지 반환한다.
     * Manager 클래스에서 true로 오버라이드한다.
     */
    public abstract boolean isManager();
}

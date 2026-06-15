package com.habittracker.logic;

import com.habittracker.data.Database;
import com.habittracker.model.Manager;
import com.habittracker.model.Member;
import com.habittracker.model.User;

/**
 * 시스템 접근을 위해 사용자 인증을 처리하는 클래스이다.
 * 입력된 ID와 비밀번호를 Database 클래스를 통해 검증하고,
 * 인증 결과와 사용자 권한을 반환한다.
 */
public class Login {

    private final Database database;
    private User currentUser;

    public Login() {
        this.database = Database.getInstance();
    }

    /**
     * ID와 비밀번호를 검증하여 로그인 성공 여부를 반환하는 메소드이다.
     * 성공 시 현재 로그인한 사용자(currentUser)를 설정한다.
     */
    public boolean loginCheck(String id, String password) {
        User user = database.getUser(id);
        if (user == null) {
            return false;
        }
        if (!user.getPassword().equals(password)) {
            return false;
        }
        this.currentUser = user;
        return true;
    }

    /**
     * 로그인한 사용자가 관리자인지 판별하는 메소드이다.
     * true이면 Manager, false이면 Member이다.
     */
    public boolean isManager(String id) {
        User user = database.getUser(id);
        return user != null && user.isManager();
    }

    /**
     * 회원가입을 처리한다.
     * - hasaID(id) : 입력한 ID가 이미 존재하는지 확인한다.
     * - 중복이 없으면 새로운 Member를 생성하여 Database에 저장한다.
     *
     * @return true: 가입 성공, false: 이미 존재하는 ID(가입 실패)
     */
    public boolean registerMember(String id, String password, String nickname) {
        if (hasaID(id)) {
            return false;
        }
        Member member = new Member(id, password, nickname);
        database.saveUser(member);
        return true;
    }

    /**
     * 입력한 ID가 이미 등록되어 있는지 확인하는 메소드이다.
     */
    public boolean hasaID(String id) {
        return database.checkDuplicateID(id);
    }

    /**
     * 현재 세션을 종료하고 로그인 정보를 초기화하는 메소드이다.
     */
    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

package com.habittracker.ui;

import com.habittracker.logic.Login;
import com.habittracker.model.User;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.Dimension;

/**
 * 프로그램의 메인 프레임이다.
 * CardLayout을 사용하여 로그인 / 회원가입 / 메인(습관 목록) /
 * 통계 / 회원 관리 화면을 전환한다.
 *
 * State Machine Diagram의
 * Launch System → (Register Member | Wait Inserting User Info) →
 * Main Screen(Member/Manager) → View Statistics / Manage Member → Logout/Exit
 * 흐름을 화면 전환으로 구현한다.
 */
public class AppFrame extends JFrame {

    public static final String CARD_LOGIN = "LOGIN";
    public static final String CARD_REGISTER = "REGISTER";
    public static final String CARD_MAIN = "MAIN";
    public static final String CARD_STATISTICS = "STATISTICS";
    public static final String CARD_MANAGE_MEMBER = "MANAGE_MEMBER";

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final Login loginService;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private MainPanel mainPanel;
    private StatisticsPanel statisticsPanel;
    private ManageMemberPanel manageMemberPanel;

    public AppFrame() {
        super("당신을 바꾸는 습관");

        this.loginService = new Login();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 560));
        setSize(720, 620);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        mainPanel = new MainPanel(this);
        statisticsPanel = new StatisticsPanel(this);
        manageMemberPanel = new ManageMemberPanel(this);

        cardPanel.add(loginPanel, CARD_LOGIN);
        cardPanel.add(registerPanel, CARD_REGISTER);
        cardPanel.add(mainPanel, CARD_MAIN);
        cardPanel.add(statisticsPanel, CARD_STATISTICS);
        cardPanel.add(manageMemberPanel, CARD_MANAGE_MEMBER);

        setContentPane(cardPanel);
    }

    public Login getLoginService() {
        return loginService;
    }

    public User getCurrentUser() {
        return loginService.getCurrentUser();
    }

    public void showCard(String name) {
        if (name.equals(CARD_MAIN)) {
            mainPanel.refresh();
        } else if (name.equals(CARD_MANAGE_MEMBER)) {
            manageMemberPanel.refresh();
        }
        cardLayout.show(cardPanel, name);
    }

    /**
     * 통계 화면으로 이동하며, 표시할 습관 ID를 전달한다.
     */
    public void showStatistics(int habitID) {
        statisticsPanel.loadHabit(habitID);
        cardLayout.show(cardPanel, CARD_STATISTICS);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.setVisible(true);
        });
    }
}

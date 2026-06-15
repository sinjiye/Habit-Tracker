package com.habittracker.ui;

import com.habittracker.logic.HabitService;
import com.habittracker.logic.ShowList;
import com.habittracker.logic.Statistics;
import com.habittracker.model.Habit;
import com.habittracker.model.User;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

/**
 * Use Case #6 : View Habit List / Use Case #7 : Check Habit
 *
 * 로그인 직후 또는 메인으로 돌아올 때 출력되는 메인 화면이다.
 * 사용자의 습관 목록을 불러오고(ShowList), 오늘의 수행 여부를
 * 체크박스로 표시한다. 습관 추가/삭제, 통계 보기, 회원 관리(관리자),
 * 로그아웃 기능을 제공한다.
 */
public class MainPanel extends JPanel {

    private final AppFrame appFrame;
    private final ShowList showList;
    private final HabitService habitService;

    private JLabel welcomeLabel;
    private JLabel summaryLabel;
    private JPanel listContainer;
    private RoundButton manageMemberBtn;

    public MainPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.showList = new ShowList();
        this.habitService = new HabitService();

        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND);
        buildUI();
    }

    private void buildUI() {
        // ── 상단 헤더 ────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.PRIMARY);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);

        JLabel title = new JLabel("당신을 바꾸는 습관");
        title.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 20));
        title.setForeground(java.awt.Color.WHITE);

        welcomeLabel = new JLabel(" ");
        welcomeLabel.setFont(UIConstants.FONT_SMALL);
        welcomeLabel.setForeground(new java.awt.Color(0xDD, 0xE8, 0xF5));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(welcomeLabel);

        JPanel rightBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightBox.setOpaque(false);

        manageMemberBtn = new RoundButton("회원 관리", UIConstants.PRIMARY_LIGHT);
        manageMemberBtn.addActionListener(e -> appFrame.showCard(AppFrame.CARD_MANAGE_MEMBER));

        RoundButton logoutBtn = new RoundButton("로그아웃", UIConstants.DANGER);
        logoutBtn.addActionListener(e -> doLogout());

        rightBox.add(manageMemberBtn);
        rightBox.add(logoutBtn);

        header.add(titleBox, BorderLayout.WEST);
        header.add(rightBox, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ── 가운데 습관 목록 ──────────────────────────────────
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setBackground(UIConstants.BACKGROUND);
        listContainer.setBorder(new EmptyBorder(16, 24, 16, 24));

        JScrollPane scrollPane = new JScrollPane(listContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(UIConstants.BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);

        // ── 하단 영역 ────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(UIConstants.CARD_BG);
        footer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, UIConstants.BORDER),
                new EmptyBorder(12, 24, 12, 24)));

        summaryLabel = new JLabel(" ");
        summaryLabel.setFont(UIConstants.FONT_BOLD);
        summaryLabel.setForeground(UIConstants.TEXT_DARK);

        RoundButton addHabitBtn = new RoundButton("+ 새 습관 추가", UIConstants.ACCENT);
        addHabitBtn.addActionListener(e -> doAddHabit());

        footer.add(summaryLabel, BorderLayout.WEST);
        footer.add(addHabitBtn, BorderLayout.EAST);

        add(footer, BorderLayout.SOUTH);
    }

    /**
     * 화면이 다시 보여질 때마다 습관 목록을 새로 불러와 화면을 갱신한다.
     */
    public void refresh() {
        User user = appFrame.getCurrentUser();
        if (user == null) {
            return;
        }

        welcomeLabel.setText(user.getNickname() + "님, 오늘도 좋은 하루 보내세요!");
        manageMemberBtn.setVisible(user.isManager());

        showList.loadHabitList(user.getUserID());
        List<Habit> habits = showList.getHabitList();

        listContainer.removeAll();

        if (habits.isEmpty()) {
            JLabel empty = new JLabel("등록된 습관이 없습니다. '새 습관 추가'로 시작해보세요!");
            empty.setFont(UIConstants.FONT_NORMAL);
            empty.setForeground(UIConstants.TEXT_MUTED);
            empty.setAlignmentX(0f);
            empty.setBorder(new EmptyBorder(40, 0, 0, 0));
            empty.setHorizontalAlignment(SwingConstants.CENTER);
            listContainer.add(empty);
        } else {
            for (Habit habit : habits) {
                listContainer.add(createHabitRow(habit));
                listContainer.add(Box.createVerticalStrut(10));
            }
        }

        listContainer.revalidate();
        listContainer.repaint();

        int total = habits.size();
        int checked = showList.getCheckedCount();
        summaryLabel.setText("오늘의 진행 상황 : " + checked + " / " + total + " 완료");
    }

    /**
     * 하나의 습관을 표현하는 행(카드)을 생성한다.
     */
    private JPanel createHabitRow(Habit habit) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setBackground(UIConstants.CARD_BG);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                new EmptyBorder(12, 16, 12, 16)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        row.setAlignmentX(0f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 6, 2, 6);
        gbc.anchor = GridBagConstraints.WEST;

        // 체크박스
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBackground(UIConstants.CARD_BG);
        checkBox.setSelected(showList.isCheckedToday(habit.getHabitID()));
        checkBox.addActionListener(e -> doCheckHabit(habit, checkBox));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        row.add(checkBox, gbc);

        // 이름 + 설명
        JPanel infoBox = new JPanel();
        infoBox.setOpaque(false);
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(habit.getName());
        nameLabel.setFont(UIConstants.FONT_SUBTITLE);
        nameLabel.setForeground(UIConstants.TEXT_DARK);

        String desc = habit.getDescription();
        JLabel descLabel = new JLabel(desc == null || desc.isEmpty() ? "설명 없음" : desc);
        descLabel.setFont(UIConstants.FONT_SMALL);
        descLabel.setForeground(UIConstants.TEXT_MUTED);

        infoBox.add(nameLabel);
        infoBox.add(descLabel);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        row.add(infoBox, gbc);

        // 연속 성공일 (Streak)
        Statistics stats = new Statistics(habit.getHabitID());
        stats.calculateAchievementRate(habitService.getRecords(habit.getHabitID()));
        stats.calculateStreak(habitService.getRecords(habit.getHabitID()));

        JLabel streakLabel = new JLabel(streakText(stats.getCurrentStreak()));
        streakLabel.setFont(UIConstants.FONT_BOLD);
        streakLabel.setForeground(UIConstants.PRIMARY_LIGHT);
        streakLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 16, 2, 16);
        row.add(streakLabel, gbc);

        // 버튼들 (통계 / 삭제)
        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        btnBox.setOpaque(false);

        RoundButton statsBtn = new RoundButton("통계", UIConstants.PRIMARY_LIGHT);
        statsBtn.setMargin(new Insets(4, 10, 4, 10));
        statsBtn.addActionListener(e -> appFrame.showStatistics(habit.getHabitID()));

        RoundButton deleteBtn = new RoundButton("삭제", UIConstants.DANGER);
        deleteBtn.setMargin(new Insets(4, 10, 4, 10));
        deleteBtn.addActionListener(e -> doDeleteHabit(habit));

        btnBox.add(statsBtn);
        btnBox.add(deleteBtn);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.insets = new Insets(2, 6, 2, 6);
        row.add(btnBox, gbc);

        return row;
    }

    private String streakText(int streak) {
        if (streak <= 0) {
            return "연속 0일";
        }
        return "🔥 연속 " + streak + "일";
    }

    /**
     * Use Case #7 : Check Habit
     * 체크박스를 클릭하면 오늘 날짜의 수행 기록을 토글하고 화면을 갱신한다.
     */
    private void doCheckHabit(Habit habit, JCheckBox checkBox) {
        habitService.checkHabit(habit.getHabitID(), showList.getCurrentDate());
        refresh();
    }

    /**
     * Use Case #4 : Register Habit
     */
    private void doAddHabit() {
        HabitDialog dialog = new HabitDialog(appFrame);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String name = dialog.getHabitName();
            String desc = dialog.getHabitDescription();

            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "습관 이름을 입력해주세요.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            User user = appFrame.getCurrentUser();
            if (habitService.isDuplicateName(user.getUserID(), name.trim())) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 습관 이름입니다.",
                        "입력 오류", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Habit habit = habitService.registerHabit(user.getUserID(), name, desc);
            if (habit == null) {
                JOptionPane.showMessageDialog(this, "습관 등록에 실패했습니다.",
                        "오류", JOptionPane.ERROR_MESSAGE);
            }
            refresh();
        }
    }

    /**
     * Use Case #5 : Delete Habit
     */
    private void doDeleteHabit(Habit habit) {
        int result = JOptionPane.showConfirmDialog(this,
                "'" + habit.getName() + "' 습관을 정말 삭제하시겠습니까?\n관련된 수행 기록도 모두 삭제됩니다.",
                "습관 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            habitService.deleteHabit(habit.getHabitID());
            refresh();
        }
    }

    /**
     * Use Case #3 : Log out
     */
    private void doLogout() {
        appFrame.getLoginService().logout();
        appFrame.showCard(AppFrame.CARD_LOGIN);
    }
}

package com.habittracker.ui;

import com.habittracker.data.Database;
import com.habittracker.logic.Statistics;
import com.habittracker.model.Habit;
import com.habittracker.model.Record;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * Use Case #8 : View Statistics
 *
 * 특정 습관의 달성률, 최대/현재 연속 성공일을 계산하여 보여주고,
 * 최근 14일간의 수행 여부를 시각적으로 표시한다.
 */
public class StatisticsPanel extends JPanel {

    private final AppFrame appFrame;
    private final Database database;

    private JLabel titleLabel;
    private JLabel achievementValueLabel;
    private JProgressBar achievementBar;
    private JLabel maxStreakLabel;
    private JLabel currentStreakLabel;
    private JLabel countLabel;
    private JPanel recentHistoryPanel;

    private static final int RECENT_DAYS = 14;

    public StatisticsPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.database = Database.getInstance();

        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND);
        buildUI();
    }

    private void buildUI() {
        // ── 상단 헤더 ────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIConstants.PRIMARY);
        header.setBorder(new EmptyBorder(16, 24, 16, 24));

        titleLabel = new JLabel("통계");
        titleLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        RoundButton backBtn = new RoundButton("← 목록으로", UIConstants.PRIMARY_LIGHT);
        backBtn.addActionListener(e -> appFrame.showCard(AppFrame.CARD_MAIN));

        header.add(titleLabel, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ── 본문 ─────────────────────────────────────────────
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(UIConstants.BACKGROUND);
        body.setBorder(new EmptyBorder(24, 32, 24, 32));

        // 달성률 카드
        JPanel achievementCard = card();
        achievementCard.setLayout(new BorderLayout(0, 12));

        JLabel achievementTitle = new JLabel("습관 달성률");
        achievementTitle.setFont(UIConstants.FONT_SUBTITLE);
        achievementTitle.setForeground(UIConstants.TEXT_DARK);

        achievementValueLabel = new JLabel("0%");
        achievementValueLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 32));
        achievementValueLabel.setForeground(UIConstants.PRIMARY);
        achievementValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel achievementHeader = new JPanel(new BorderLayout());
        achievementHeader.setOpaque(false);
        achievementHeader.add(achievementTitle, BorderLayout.WEST);
        achievementHeader.add(achievementValueLabel, BorderLayout.EAST);

        achievementBar = new JProgressBar(0, 100);
        achievementBar.setValue(0);
        achievementBar.setForeground(UIConstants.ACCENT);
        achievementBar.setBackground(UIConstants.BORDER);
        achievementBar.setPreferredSize(new java.awt.Dimension(100, 22));
        achievementBar.setStringPainted(false);

        countLabel = new JLabel(" ");
        countLabel.setFont(UIConstants.FONT_SMALL);
        countLabel.setForeground(UIConstants.TEXT_MUTED);

        JPanel achievementBody = new JPanel();
        achievementBody.setOpaque(false);
        achievementBody.setLayout(new BoxLayout(achievementBody, BoxLayout.Y_AXIS));
        achievementBody.add(achievementBar);
        achievementBody.add(Box.createVerticalStrut(6));
        achievementBody.add(countLabel);

        achievementCard.add(achievementHeader, BorderLayout.NORTH);
        achievementCard.add(achievementBody, BorderLayout.CENTER);

        body.add(achievementCard);
        body.add(Box.createVerticalStrut(16));

        // 연속 성공일 카드
        JPanel streakCard = card();
        streakCard.setLayout(new GridLayout(1, 2, 16, 0));

        streakCard.add(streakBox("현재 연속 성공일", true));
        streakCard.add(streakBox("최대 연속 성공일", false));

        body.add(streakCard);
        body.add(Box.createVerticalStrut(16));

        // 최근 기록 카드
        JPanel recentCard = card();
        recentCard.setLayout(new BorderLayout(0, 12));

        JLabel recentTitle = new JLabel("최근 " + RECENT_DAYS + "일 수행 현황");
        recentTitle.setFont(UIConstants.FONT_SUBTITLE);
        recentTitle.setForeground(UIConstants.TEXT_DARK);

        recentHistoryPanel = new JPanel(new GridLayout(1, RECENT_DAYS, 4, 0));
        recentHistoryPanel.setOpaque(false);

        recentCard.add(recentTitle, BorderLayout.NORTH);
        recentCard.add(recentHistoryPanel, BorderLayout.CENTER);

        body.add(recentCard);

        add(body, BorderLayout.CENTER);
    }

    /**
     * 간단한 카드 형태의 패널을 생성한다.
     */
    private JPanel card() {
        JPanel panel = new JPanel();
        panel.setBackground(UIConstants.CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                new EmptyBorder(18, 20, 18, 20)));
        panel.setAlignmentX(0f);
        return panel;
    }

    /**
     * 연속 성공일을 표시하는 작은 박스를 생성한다.
     * isCurrent가 true이면 currentStreakLabel, false이면 maxStreakLabel에 보관한다.
     */
    private JPanel streakBox(String title, boolean isCurrent) {
        JPanel box = new JPanel(new GridBagLayout());
        box.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 6, 0);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(UIConstants.FONT_NORMAL);
        titleLbl.setForeground(UIConstants.TEXT_MUTED);
        box.add(titleLbl, gbc);

        gbc.gridy++;
        JLabel valueLbl = new JLabel("0일");
        valueLbl.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 28));
        valueLbl.setForeground(UIConstants.PRIMARY);
        box.add(valueLbl, gbc);

        if (isCurrent) {
            currentStreakLabel = valueLbl;
        } else {
            maxStreakLabel = valueLbl;
        }

        return box;
    }

    /**
     * 통계 화면으로 진입할 때 호출되며, 해당 habitID의 통계를 계산하여 표시한다.
     */
    public void loadHabit(int habitID) {
        Habit habit = database.getHabit(habitID);
        if (habit == null) {
            return;
        }

        titleLabel.setText("통계 - " + habit.getName());

        List<Record> records = database.getRecords(habitID);

        Statistics stats = new Statistics(habitID);
        double rate = stats.calculateAchievementRate(records);
        stats.calculateStreak(records);

        achievementValueLabel.setText(String.format(Locale.US, "%.1f%%", rate));
        achievementBar.setValue((int) Math.round(rate));
        countLabel.setText("전체 " + stats.getTotalRecords() + "일 중 "
                + stats.getDoneCount() + "일 수행");

        currentStreakLabel.setText(stats.getCurrentStreak() + "일");
        maxStreakLabel.setText(stats.getMaxStreak() + "일");

        renderRecentHistory(habitID);
    }

    /**
     * 최근 RECENT_DAYS일간의 수행 여부를 작은 색상 박스로 표시한다.
     */
    private void renderRecentHistory(int habitID) {
        recentHistoryPanel.removeAll();

        LocalDate today = LocalDate.now();
        DateTimeFormatter iso = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("d");

        for (int i = RECENT_DAYS - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            Record record = database.getRecord(habitID, date.format(iso));
            boolean done = record != null && record.isDone();

            JPanel dayBox = new JPanel();
            dayBox.setLayout(new BorderLayout());
            dayBox.setBackground(done ? UIConstants.ACCENT : UIConstants.BORDER);
            dayBox.setPreferredSize(new java.awt.Dimension(28, 40));

            JLabel dayLabel = new JLabel(date.format(dayFormat));
            dayLabel.setFont(UIConstants.FONT_SMALL);
            dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
            dayLabel.setForeground(done ? Color.WHITE : UIConstants.TEXT_MUTED);

            dayBox.add(dayLabel, BorderLayout.CENTER);
            recentHistoryPanel.add(dayBox);
        }

        recentHistoryPanel.revalidate();
        recentHistoryPanel.repaint();
    }
}

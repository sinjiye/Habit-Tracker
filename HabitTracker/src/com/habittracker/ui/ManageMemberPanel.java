package com.habittracker.ui;

import com.habittracker.data.Database;
import com.habittracker.model.User;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.util.List;

/**
 * Use Case #9 : Manage Member
 *
 * 관리자가 전체 회원 목록을 조회하고, 선택한 회원을 삭제할 수 있는 화면이다.
 * 관리자 권한을 가진 사용자만 이 화면에 접근할 수 있다(메인 화면에서
 * '회원 관리' 버튼이 관리자에게만 보여진다).
 */
public class ManageMemberPanel extends JPanel {

    private final AppFrame appFrame;
    private final Database database;

    private DefaultTableModel tableModel;
    private JTable table;
    private JLabel countLabel;

    public ManageMemberPanel(AppFrame appFrame) {
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

        JLabel title = new JLabel("회원 관리");
        title.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        RoundButton backBtn = new RoundButton("← 메인으로", UIConstants.PRIMARY_LIGHT);
        backBtn.addActionListener(e -> appFrame.showCard(AppFrame.CARD_MAIN));

        header.add(title, BorderLayout.WEST);
        header.add(backBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ── 본문: 테이블 ─────────────────────────────────────
        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UIConstants.BACKGROUND);
        body.setBorder(new EmptyBorder(20, 24, 20, 24));

        tableModel = new DefaultTableModel(new Object[]{"ID", "닉네임", "권한"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(UIConstants.FONT_NORMAL);
        table.setRowHeight(28);
        table.getTableHeader().setFont(UIConstants.FONT_BOLD);
        table.getTableHeader().setBackground(UIConstants.BORDER);
        table.setSelectionBackground(new Color(0xDD, 0xE8, 0xF5));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.BORDER, 1, true));

        body.add(scrollPane, BorderLayout.CENTER);

        // ── 하단 ─────────────────────────────────────────────
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(12, 0, 0, 0));

        countLabel = new JLabel(" ");
        countLabel.setFont(UIConstants.FONT_SMALL);
        countLabel.setForeground(UIConstants.TEXT_MUTED);

        JPanel buttonBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonBox.setOpaque(false);

        RoundButton deleteBtn = new RoundButton("선택한 회원 삭제", UIConstants.DANGER);
        deleteBtn.addActionListener(e -> doDeleteMember());
        buttonBox.add(deleteBtn);

        footer.add(countLabel, BorderLayout.WEST);
        footer.add(buttonBox, BorderLayout.EAST);

        body.add(footer, BorderLayout.SOUTH);

        add(body, BorderLayout.CENTER);
    }

    /**
     * 화면 진입 시 전체 회원 목록을 다시 불러온다.
     */
    public void refresh() {
        tableModel.setRowCount(0);

        List<User> users = database.getAllUsers();
        for (User u : users) {
            tableModel.addRow(new Object[]{
                    u.getUserID(),
                    u.getNickname(),
                    u.isManager() ? "관리자" : "일반회원"
            });
        }

        countLabel.setText("전체 회원 수 : " + users.size() + "명");
    }

    /**
     * 선택한 행의 회원을 삭제한다. 현재 로그인한 관리자 본인은 삭제할 수 없다.
     */
    private void doDeleteMember() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "삭제할 회원을 선택해주세요.",
                    "안내", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String userID = (String) tableModel.getValueAt(row, 0);
        String nickname = (String) tableModel.getValueAt(row, 1);

        User current = appFrame.getCurrentUser();
        if (current != null && current.getUserID().equals(userID)) {
            JOptionPane.showMessageDialog(this, "현재 로그인한 계정은 삭제할 수 없습니다.",
                    "삭제 불가", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "'" + nickname + "(" + userID + ")' 회원을 정말 삭제하시겠습니까?\n"
                        + "해당 회원의 습관 및 기록도 모두 삭제됩니다.",
                "회원 삭제", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            database.deleteUser(userID);
            refresh();
        }
    }
}

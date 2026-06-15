package com.habittracker.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Frame;

/**
 * Use Case #4 : Register Habit
 * 새로운 습관의 이름과 상세 설명을 입력받는 모달 다이얼로그이다.
 */
public class HabitDialog extends JDialog {

    private JTextField nameField;
    private JTextArea descArea;
    private boolean confirmed = false;

    public HabitDialog(Frame owner) {
        super(owner, "새 습관 추가", true);
        setSize(480, 420);
        setLocationRelativeTo(owner);
        setResizable(true);
        setMinimumSize(new Dimension(420, 380));
        buildUI();
    }

    private void buildUI() {
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(UIConstants.CARD_BG);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("습관 이름");
        nameLabel.setFont(UIConstants.FONT_BOLD);
        form.add(nameLabel, gbc);

        gbc.gridy++;
        nameField = new JTextField(20);
        nameField.setFont(new java.awt.Font(UIConstants.FONT_FAMILY, java.awt.Font.PLAIN, 18));
        nameField.setPreferredSize(new Dimension(300, 40));
        form.add(nameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(16, 6, 6, 6);
        JLabel descLabel = new JLabel("설명");
        descLabel.setFont(UIConstants.FONT_BOLD);
        form.add(descLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descArea = new JTextArea(6, 20);
        descArea.setFont(new java.awt.Font(UIConstants.FONT_FAMILY, java.awt.Font.PLAIN, 18));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setMargin(new Insets(8, 8, 8, 8));
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(300, 160));
        form.add(descScroll, gbc);

        content.add(form, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        RoundButton saveBtn = new RoundButton("저장", UIConstants.PRIMARY);
        saveBtn.setPreferredSize(new Dimension(90, 36));
        saveBtn.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        RoundButton cancelBtn = new RoundButton("취소", UIConstants.TEXT_MUTED);
        cancelBtn.setPreferredSize(new Dimension(90, 36));
        cancelBtn.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        content.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(content);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getHabitName() {
        return nameField.getText();
    }

    public String getHabitDescription() {
        return descArea.getText();
    }
}

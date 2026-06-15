package com.habittracker.ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Use Case #1 : Register Member
 * 사용자가 시스템에 새 계정을 생성하는 화면이다.
 * ID 중복, 빈 입력, 비밀번호 불일치 등을 검사한다.
 */
public class RegisterPanel extends JPanel {

    private final AppFrame appFrame;

    private JTextField idField;
    private JPasswordField pwField;
    private JPasswordField pwConfirmField;
    private JTextField nicknameField;
    private JLabel messageLabel;

    public RegisterPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        setLayout(new BorderLayout());
        setBackground(UIConstants.BACKGROUND);
        buildUI();
    }

    private void buildUI() {
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(UIConstants.BACKGROUND);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(UIConstants.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.BORDER, 1, true),
                BorderFactory.createEmptyBorder(36, 48, 36, 48)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel title = new JLabel("회원가입");
        title.setFont(UIConstants.FONT_TITLE);
        title.setForeground(UIConstants.PRIMARY);
        card.add(title, gbc);

        gbc.gridwidth = 1;

        idField = addRow(card, gbc, "ID", new JTextField(16));
        pwField = (JPasswordField) addRow(card, gbc, "PW", new JPasswordField(16));
        pwConfirmField = (JPasswordField) addRow(card, gbc, "PW 확인", new JPasswordField(16));
        nicknameField = addRow(card, gbc, "닉네임", new JTextField(16));

        // 메세지 라벨
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        messageLabel = new JLabel(" ");
        messageLabel.setFont(UIConstants.FONT_SMALL);
        messageLabel.setForeground(UIConstants.DANGER);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(messageLabel, gbc);

        // 버튼
        gbc.gridy++;
        gbc.insets = new Insets(16, 8, 4, 8);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setBackground(UIConstants.CARD_BG);

        RoundButton submitBtn = new RoundButton("가입하기", UIConstants.PRIMARY);
        submitBtn.setPreferredSize(new Dimension(110, 40));
        submitBtn.addActionListener(e -> doRegister());

        RoundButton cancelBtn = new RoundButton("취소", UIConstants.TEXT_MUTED);
        cancelBtn.setPreferredSize(new Dimension(110, 40));
        cancelBtn.addActionListener(e -> {
            clearFields();
            appFrame.showCard(AppFrame.CARD_LOGIN);
        });

        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        card.add(buttonPanel, gbc);

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    @SuppressWarnings("unchecked")
    private <T extends JTextField> T addRow(JPanel card, GridBagConstraints gbc, String label, T field) {
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel l = new JLabel(label);
        l.setFont(UIConstants.FONT_BOLD);
        card.add(l, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        field.setFont(UIConstants.FONT_NORMAL);
        card.add(field, gbc);

        return field;
    }

    private void doRegister() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword());
        String pwConfirm = new String(pwConfirmField.getPassword());
        String nickname = nicknameField.getText().trim();

        if (id.isEmpty() || pw.isEmpty() || pwConfirm.isEmpty() || nickname.isEmpty()) {
            showError("모든 항목을 입력해주세요.");
            return;
        }

        if (!pw.equals(pwConfirm)) {
            showError("비밀번호가 일치하지 않습니다.");
            return;
        }

        if (appFrame.getLoginService().hasaID(id)) {
            showError("이미 존재하는 ID입니다.");
            return;
        }

        boolean success = appFrame.getLoginService().registerMember(id, pw, nickname);
        if (!success) {
            showError("회원가입에 실패했습니다. 다시 시도해주세요.");
            return;
        }

        clearFields();
        appFrame.showCard(AppFrame.CARD_LOGIN);
    }

    private void clearFields() {
        idField.setText("");
        pwField.setText("");
        pwConfirmField.setText("");
        nicknameField.setText("");
        messageLabel.setText(" ");
    }

    private void showError(String msg) {
        messageLabel.setText(msg);
    }
}

package com.habittracker.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * Use Case #2 : Log in
 * 사용자가 ID와 PW를 입력하여 로그인을 시도하는 화면이다.
 * 입력값이 비어있거나, ID/PW가 일치하지 않을 경우 오류 메세지를 출력한다.
 */
public class LoginPanel extends JPanel {

    private final AppFrame appFrame;

    private JTextField idField;
    private JPasswordField pwField;
    private JLabel messageLabel;

    public LoginPanel(AppFrame appFrame) {
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

        JLabel title = new JLabel("당신을 바꾸는 습관");
        title.setFont(UIConstants.FONT_TITLE);
        title.setForeground(UIConstants.PRIMARY);
        card.add(title, gbc);

        gbc.gridy++;
        JLabel subtitle = new JLabel("로그인");
        subtitle.setFont(UIConstants.FONT_SUBTITLE);
        subtitle.setForeground(UIConstants.TEXT_MUTED);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(4, 8, 24, 8);
        card.add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // ID
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(UIConstants.FONT_BOLD);
        card.add(idLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        idField = new JTextField(16);
        idField.setFont(UIConstants.FONT_NORMAL);
        card.add(idField, gbc);

        // PW
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel pwLabel = new JLabel("PW");
        pwLabel.setFont(UIConstants.FONT_BOLD);
        card.add(pwLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        pwField = new JPasswordField(16);
        pwField.setFont(UIConstants.FONT_NORMAL);
        card.add(pwField, gbc);

        // 메세지 라벨 (오류/안내)
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        messageLabel = new JLabel(" ");
        messageLabel.setFont(UIConstants.FONT_SMALL);
        messageLabel.setForeground(UIConstants.DANGER);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(messageLabel, gbc);

        // 버튼들
        gbc.gridy++;
        gbc.insets = new Insets(16, 8, 4, 8);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setBackground(UIConstants.CARD_BG);

        RoundButton loginBtn = new RoundButton("로그인", UIConstants.PRIMARY);
        loginBtn.setPreferredSize(new Dimension(110, 40));
        loginBtn.addActionListener(e -> doLogin());

        RoundButton registerBtn = new RoundButton("회원가입", UIConstants.PRIMARY_LIGHT);
        registerBtn.setPreferredSize(new Dimension(110, 40));
        registerBtn.addActionListener(e -> {
            messageLabel.setText(" ");
            appFrame.showCard(AppFrame.CARD_REGISTER);
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        card.add(buttonPanel, gbc);

        // Enter 키로 로그인
        pwField.addActionListener(e -> doLogin());

        center.add(card);
        add(center, BorderLayout.CENTER);
    }

    private void doLogin() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword());

        if (id.isEmpty() || pw.isEmpty()) {
            showError("ID 또는 PW가 입력되지 않았습니다.");
            return;
        }

        boolean success = appFrame.getLoginService().loginCheck(id, pw);
        if (!success) {
            showError("ID 또는 PW가 틀렸습니다.");
            pwField.setText("");
            return;
        }

        messageLabel.setText(" ");
        idField.setText("");
        pwField.setText("");
        appFrame.showCard(AppFrame.CARD_MAIN);
    }

    private void showError(String msg) {
        messageLabel.setText(msg);
    }
}

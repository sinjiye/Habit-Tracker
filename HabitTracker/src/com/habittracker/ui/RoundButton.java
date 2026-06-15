package com.habittracker.ui;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 색상이 적용된 둥근 버튼. 마우스 오버 시 색이 약간 진해진다.
 */
public class RoundButton extends JButton {

    private final Color baseColor;
    private final Color hoverColor;

    public RoundButton(String text, Color baseColor) {
        super(text);
        this.baseColor = baseColor;
        this.hoverColor = baseColor.darker();

        setFont(UIConstants.FONT_BUTTON);
        setForeground(Color.WHITE);
        setBackground(baseColor);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setMargin(new java.awt.Insets(8, 18, 8, 18));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(hoverColor);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(baseColor);
                }
            }
        });
    }
}

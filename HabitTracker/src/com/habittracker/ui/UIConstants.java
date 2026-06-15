package com.habittracker.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * 화면 전체에서 공통으로 사용하는 색상 및 폰트 상수를 정의한다.
 */
public final class UIConstants {

    private UIConstants() {
    }

    // 색상
    public static final Color PRIMARY = new Color(0x1F, 0x4E, 0x79);     // 진한 네이비 블루
    public static final Color PRIMARY_LIGHT = new Color(0x2E, 0x75, 0xB6); // 밝은 블루
    public static final Color ACCENT = new Color(0x4C, 0xAF, 0x50);       // 그린 (성공/체크)
    public static final Color DANGER = new Color(0xE5, 0x73, 0x73);       // 레드 (삭제/오류)
    public static final Color BACKGROUND = new Color(0xF4, 0xF6, 0xF9);   // 화면 배경
    public static final Color CARD_BG = Color.WHITE;
    public static final Color BORDER = new Color(0xD9, 0xE2, 0xEC);
    public static final Color TEXT_DARK = new Color(0x33, 0x33, 0x33);
    public static final Color TEXT_MUTED = new Color(0x88, 0x88, 0x88);

    // 폰트
    public static final String FONT_FAMILY = "맑은 고딕";

    public static final Font FONT_TITLE = new Font(FONT_FAMILY, Font.BOLD, 26);
    public static final Font FONT_SUBTITLE = new Font(FONT_FAMILY, Font.BOLD, 16);
    public static final Font FONT_NORMAL = new Font(FONT_FAMILY, Font.PLAIN, 14);
    public static final Font FONT_BOLD = new Font(FONT_FAMILY, Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font(FONT_FAMILY, Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font(FONT_FAMILY, Font.BOLD, 14);
}

package com.sup.dev.java_pc.views;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class GUI {

    //  2

    public static final String ic_minus_18 =  "ic_minus_18.png";
    public static final String ic_back_24 = "ic_back_24.png";

    public static final String RES_DIR = "res\\";
    public static final String ICONS_DIR = RES_DIR +"icons\\";
    public static final String FONTS_DIR = RES_DIR +"fonts\\";

    public static final int ANIMATION_ENTER = 300;

    public static final Font DISPLAY_4 = createFont("Roboto-Light", 112);
    public static final Font DISPLAY_4_ITALIC = createFont("Roboto-LightItalic", 112);
    public static final Font DISPLAY_3 = createFont("Roboto-Regular", 56);
    public static final Font DISPLAY_2 = createFont("Roboto-Regular", 45);
    public static final Font DISPLAY_1 = createFont("Roboto-Regular", 34);
    public static final Font HEADLINE = createFont("Roboto-Regular", 24);
    public static final Font TITLE = createFont("Roboto-Medium", 20);
    public static final Font TITLE_ITALIC = createFont("Roboto-MediumItalic", 20);
    public static final Font SUBHEADING = createFont("Roboto-Medium", 15);
    public static final Font SUBHEADING_ITALIC = createFont("Roboto-MediumItalic", 15);
    public static final Font BODY_2 = createFont("Roboto-Medium", 13);
    public static final Font BODY_2_ITALIC = createFont("Roboto-MediumItalic", 13);
    public static final Font BODY_1 = createFont("Roboto-Regular", 13);
    public static final Font CAPTION = createFont("Roboto-Regular", 12);
    public static final Font BUTTON = createFont("Roboto-Medium", 14);
    public static final Font BUTTON_ITALIC = createFont("Roboto-MediumItalic", 14);

    public static final int S_16 = 16;
    public static final int S_24 = 24;
    public static final int S_32 = 32;
    public static final int S_64 = 64;
    public static final int S_128 = 128;
    public static final int S_256 = 256;
    public static final int S_384 = 384;
    public static final int S_512 = 512;
    public static final int S_1024 = 1024;

    public static final int SLEEP_2000 = 2000;

    public static final Color WHITE = Color.WHITE;
    public static final Color ALPHA = new Color(0x00000000);

    public static final Color TEAL_50 = new Color(0xE0F2F1);
    public static final Color TEAL_100 = new Color(0xB2DFDB);
    public static final Color TEAL_200 = new Color(0x80CBC4);
    public static final Color TEAL_300 = new Color(0x4DB6AC);
    public static final Color TEAL_400 = new Color(0x26A69A);
    public static final Color TEAL_500 = new Color(0x009688);
    public static final Color TEAL_600 = new Color(0x00897B);
    public static final Color TEAL_700 = new Color(0x00796B);
    public static final Color TEAL_800 = new Color(0x00695C);
    public static final Color TEAL_900 = new Color(0x004D40);
    public static final Color TEAL_A_100 = new Color(0xA7FFEB);
    public static final Color TEAL_A_200 = new Color(0x64FFDA);
    public static final Color TEAL_A_400 = new Color(0x1DE9B6);
    public static final Color TEAL_A_700 = new Color(0x00BFA5);


    public static final Color GREEN_50 = new Color(0xE8F5E9);
    public static final Color GREEN_100 = new Color(0xC8E6C9);
    public static final Color GREEN_200 = new Color(0xA5D6A7);
    public static final Color GREEN_300 = new Color(0x81C784);
    public static final Color GREEN_400 = new Color(0x66BB6A);
    public static final Color GREEN_500 = new Color(0x4CAF50);
    public static final Color GREEN_600 = new Color(0x43A047);
    public static final Color GREEN_700 = new Color(0x388E3C);
    public static final Color GREEN_800 = new Color(0x2E7D32);
    public static final Color GREEN_900 = new Color(0x1B5E20);
    public static final Color GREEN_A_100 = new Color(0xB9F6CA);
    public static final Color GREEN_A_200 = new Color(0x69F0AE);
    public static final Color GREEN_A_400 = new Color(0x00E676);
    public static final Color GREEN_A_700 = new Color(0x00C853);

    public static final Color LIGHT_GREEN_50 = new Color(0xF1F8E9);
    public static final Color LIGHT_GREEN_100 = new Color(0xDCEDC8);
    public static final Color LIGHT_GREEN_200 = new Color(0xC5E1A5);
    public static final Color LIGHT_GREEN_300 = new Color(0xAED581);
    public static final Color LIGHT_GREEN_400 = new Color(0x9CCC65);
    public static final Color LIGHT_GREEN_500 = new Color(0x8BC34A);
    public static final Color LIGHT_GREEN_600 = new Color(0x7CB342);
    public static final Color LIGHT_GREEN_700 = new Color(0x3689F38);
    public static final Color LIGHT_GREEN_800 = new Color(0x558B2F);
    public static final Color LIGHT_GREEN_900 = new Color(0x33691E);
    public static final Color LIGHT_GREEN_A_100 = new Color(0xCCFF90);
    public static final Color LIGHT_GREEN_A_200 = new Color(0xB2FF59);
    public static final Color LIGHT_GREEN_A_400 = new Color(0x76FF03);
    public static final Color LIGHT_GREEN_A_700 = new Color(0x64DD17);

    public static final Color GREY_50 = new Color(0xFAFAFA);
    public static final Color GREY_100 = new Color(0xF5F5F5);
    public static final Color GREY_200 = new Color(0xEEEEEE);
    public static final Color GREY_300 = new Color(0xE0E0E0);
    public static final Color GREY_400 = new Color(0xBDBDBD);
    public static final Color GREY_500 = new Color(0x9E9E9E);
    public static final Color GREY_600 = new Color(0x757575);
    public static final Color GREY_700 = new Color(0x616161);
    public static final Color GREY_800 = new Color(0x424242);
    public static final Color GREY_900 = new Color(0x212121);

    public static final Color RED_50 = new Color(0xFFEBEE);
    public static final Color RED_100 = new Color(0xFFCDD2);
    public static final Color RED_200 = new Color(0xEF9A9A);
    public static final Color RED_300 = new Color(0xE57373);
    public static final Color RED_400 = new Color(0xEF5350);
    public static final Color RED_500 = new Color(0xF44336);
    public static final Color RED_600 = new Color(0xE53935);
    public static final Color RED_700 = new Color(0xD32F2F);
    public static final Color RED_800 = new Color(0xC62828);
    public static final Color RED_900 = new Color(0xB71C1C);
    public static final Color RED_A_100 = new Color(0xFF8A80);
    public static final Color RED_A_200 = new Color(0xFF5252);
    public static final Color RED_A_400 = new Color(0xFF1744);
    public static final Color RED_A_700 = new Color(0xD50000);

    public static final Color ORANGE_50 = new Color(0xFFF3E0);
    public static final Color ORANGE_100 = new Color(0xFFE0B2);
    public static final Color ORANGE_200 = new Color(0xFFCC80);
    public static final Color ORANGE_300 = new Color(0xFFB74D);
    public static final Color ORANGE_400 = new Color(0xFFA726);
    public static final Color ORANGE_500 = new Color(0xFF9800);
    public static final Color ORANGE_600 = new Color(0xFB8C00);
    public static final Color ORANGE_700 = new Color(0xF57C00);
    public static final Color ORANGE_800 = new Color(0xEF6C00);
    public static final Color ORANGE_900 = new Color(0xE65100);
    public static final Color ORANGE_A_100 = new Color(0xFFD180);
    public static final Color ORANGE_A_200 = new Color(0xFFAB40);
    public static final Color ORANGE_A_400 = new Color(0xFF9100);
    public static final Color ORANGE_A_700 = new Color(0xFF6D00);

    public static final Color DEEP_ORANGE_50 = new Color(0xFBE9E7);
    public static final Color DEEP_ORANGE_100 = new Color(0xFFCCBC);
    public static final Color DEEP_ORANGE_200 = new Color(0xFFAB91);
    public static final Color DEEP_ORANGE_300 = new Color(0xFF8A65);
    public static final Color DEEP_ORANGE_400 = new Color(0xFF7043);
    public static final Color DEEP_ORANGE_500 = new Color(0xFF5722);
    public static final Color DEEP_ORANGE_600 = new Color(0xF4511E);
    public static final Color DEEP_ORANGE_700 = new Color(0xE64A19);
    public static final Color DEEP_ORANGE_800 = new Color(0xD84315);
    public static final Color DEEP_ORANGE_900 = new Color(0xBF360C);
    public static final Color DEEP_ORANGE_A_100 = new Color(0xFF9E80);
    public static final Color DEEP_ORANGE_A_200 = new Color(0xFF6E40);
    public static final Color DEEP_ORANGE_A_400 = new Color(0xFF3D00);
    public static final Color DEEP_ORANGE_A_700 = new Color(0xDD2C00);


    public static final Color COLOR_LINE = GREY_400;
    public static final Color COLOR_ACCENT = TEAL_A_700;
    public static final Color COLOR_ACCENT_FOCUS = TEAL_A_400;
    public static final Color COLOR_BACKGROUND = GREY_200;

    static {

        UIManager.put("control", COLOR_BACKGROUND);
        UIManager.put("controlShadow", COLOR_BACKGROUND);
        UIManager.put("controlDkShadow", COLOR_BACKGROUND);
        UIManager.put("controlLtHighlight", COLOR_BACKGROUND);

        UIManager.put("scrollbar", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.background", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.darkShadow", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.highlight", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.shadow", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.gradient", Arrays.asList(0, 0, GREY_300, GREY_300, GREY_300));

        UIManager.put("ScrollBar.thumb", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.thumbDarkShadow", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.thumbShadow", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.thumbHighlight", COLOR_BACKGROUND);

        UIManager.put("ScrollBar.track", COLOR_BACKGROUND);
        UIManager.put("ScrollBar.trackHighlight", COLOR_BACKGROUND);

    }


    private static Font createFont(String name, int size) {
        Font telegraficoFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(FONTS_DIR + name + ".ttf"));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            telegraficoFont = ttfBase.deriveFont(Font.PLAIN, size);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return telegraficoFont;
    }


}

package com.sup.dev.java_pc.views

import java.awt.Color
import java.awt.Font
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.util.*
import javax.swing.UIManager


object GUI {

    //  2

    val ic_minus_18 = "ic_minus_18.png"
    val ic_back_24 = "ic_back_24.png"

    val RES_DIR = "res\\"
    val ICONS_DIR = RES_DIR + "icons\\"
    val FONTS_DIR = RES_DIR + "fonts\\"

    val ANIMATION_ENTER = 300

    val DISPLAY_4 = createFont("Roboto-Light", 112)
    val DISPLAY_4_ITALIC = createFont("Roboto-LightItalic", 112)
    val DISPLAY_3 = createFont("Roboto-Regular", 56)
    val DISPLAY_2 = createFont("Roboto-Regular", 45)
    val DISPLAY_1 = createFont("Roboto-Regular", 34)
    val HEADLINE = createFont("Roboto-Regular", 24)
    val TITLE = createFont("Roboto-Medium", 20)
    val TITLE_ITALIC = createFont("Roboto-MediumItalic", 20)
    val SUBHEADING = createFont("Roboto-Medium", 15)
    val SUBHEADING_ITALIC = createFont("Roboto-MediumItalic", 15)
    val BODY_2 = createFont("Roboto-Medium", 13)
    val BODY_2_ITALIC = createFont("Roboto-MediumItalic", 13)
    val BODY_1 = createFont("Roboto-Regular", 13)
    val CAPTION = createFont("Roboto-Regular", 12)
    val BUTTON = createFont("Roboto-Medium", 14)
    val BUTTON_ITALIC = createFont("Roboto-MediumItalic", 14)

    val S_16 = 16
    val S_24 = 24
    val S_32 = 32
    val S_64 = 64
    val S_128 = 128
    val S_256 = 256
    val S_384 = 384
    val S_512 = 512
    val S_1024 = 1024

    val SLEEP_2000 = 2000

    val WHITE = Color.WHITE
    val ALPHA = Color(0x00000000)

    val TEAL_50 = Color(0xE0F2F1)
    val TEAL_100 = Color(0xB2DFDB)
    val TEAL_200 = Color(0x80CBC4)
    val TEAL_300 = Color(0x4DB6AC)
    val TEAL_400 = Color(0x26A69A)
    val TEAL_500 = Color(0x009688)
    val TEAL_600 = Color(0x00897B)
    val TEAL_700 = Color(0x00796B)
    val TEAL_800 = Color(0x00695C)
    val TEAL_900 = Color(0x004D40)
    val TEAL_A_100 = Color(0xA7FFEB)
    val TEAL_A_200 = Color(0x64FFDA)
    val TEAL_A_400 = Color(0x1DE9B6)
    val TEAL_A_700 = Color(0x00BFA5)


    val GREEN_50 = Color(0xE8F5E9)
    val GREEN_100 = Color(0xC8E6C9)
    val GREEN_200 = Color(0xA5D6A7)
    val GREEN_300 = Color(0x81C784)
    val GREEN_400 = Color(0x66BB6A)
    val GREEN_500 = Color(0x4CAF50)
    val GREEN_600 = Color(0x43A047)
    val GREEN_700 = Color(0x388E3C)
    val GREEN_800 = Color(0x2E7D32)
    val GREEN_900 = Color(0x1B5E20)
    val GREEN_A_100 = Color(0xB9F6CA)
    val GREEN_A_200 = Color(0x69F0AE)
    val GREEN_A_400 = Color(0x00E676)
    val GREEN_A_700 = Color(0x00C853)

    val LIGHT_GREEN_50 = Color(0xF1F8E9)
    val LIGHT_GREEN_100 = Color(0xDCEDC8)
    val LIGHT_GREEN_200 = Color(0xC5E1A5)
    val LIGHT_GREEN_300 = Color(0xAED581)
    val LIGHT_GREEN_400 = Color(0x9CCC65)
    val LIGHT_GREEN_500 = Color(0x8BC34A)
    val LIGHT_GREEN_600 = Color(0x7CB342)
    val LIGHT_GREEN_700 = Color(0x3689F38)
    val LIGHT_GREEN_800 = Color(0x558B2F)
    val LIGHT_GREEN_900 = Color(0x33691E)
    val LIGHT_GREEN_A_100 = Color(0xCCFF90)
    val LIGHT_GREEN_A_200 = Color(0xB2FF59)
    val LIGHT_GREEN_A_400 = Color(0x76FF03)
    val LIGHT_GREEN_A_700 = Color(0x64DD17)

    val GREY_50 = Color(0xFAFAFA)
    val GREY_100 = Color(0xF5F5F5)
    val GREY_200 = Color(0xEEEEEE)
    val GREY_300 = Color(0xE0E0E0)
    val GREY_400 = Color(0xBDBDBD)
    val GREY_500 = Color(0x9E9E9E)
    val GREY_600 = Color(0x757575)
    val GREY_700 = Color(0x616161)
    val GREY_800 = Color(0x424242)
    val GREY_900 = Color(0x212121)

    val RED_50 = Color(0xFFEBEE)
    val RED_100 = Color(0xFFCDD2)
    val RED_200 = Color(0xEF9A9A)
    val RED_300 = Color(0xE57373)
    val RED_400 = Color(0xEF5350)
    val RED_500 = Color(0xF44336)
    val RED_600 = Color(0xE53935)
    val RED_700 = Color(0xD32F2F)
    val RED_800 = Color(0xC62828)
    val RED_900 = Color(0xB71C1C)
    val RED_A_100 = Color(0xFF8A80)
    val RED_A_200 = Color(0xFF5252)
    val RED_A_400 = Color(0xFF1744)
    val RED_A_700 = Color(0xD50000)

    val ORANGE_50 = Color(0xFFF3E0)
    val ORANGE_100 = Color(0xFFE0B2)
    val ORANGE_200 = Color(0xFFCC80)
    val ORANGE_300 = Color(0xFFB74D)
    val ORANGE_400 = Color(0xFFA726)
    val ORANGE_500 = Color(0xFF9800)
    val ORANGE_600 = Color(0xFB8C00)
    val ORANGE_700 = Color(0xF57C00)
    val ORANGE_800 = Color(0xEF6C00)
    val ORANGE_900 = Color(0xE65100)
    val ORANGE_A_100 = Color(0xFFD180)
    val ORANGE_A_200 = Color(0xFFAB40)
    val ORANGE_A_400 = Color(0xFF9100)
    val ORANGE_A_700 = Color(0xFF6D00)

    val DEEP_ORANGE_50 = Color(0xFBE9E7)
    val DEEP_ORANGE_100 = Color(0xFFCCBC)
    val DEEP_ORANGE_200 = Color(0xFFAB91)
    val DEEP_ORANGE_300 = Color(0xFF8A65)
    val DEEP_ORANGE_400 = Color(0xFF7043)
    val DEEP_ORANGE_500 = Color(0xFF5722)
    val DEEP_ORANGE_600 = Color(0xF4511E)
    val DEEP_ORANGE_700 = Color(0xE64A19)
    val DEEP_ORANGE_800 = Color(0xD84315)
    val DEEP_ORANGE_900 = Color(0xBF360C)
    val DEEP_ORANGE_A_100 = Color(0xFF9E80)
    val DEEP_ORANGE_A_200 = Color(0xFF6E40)
    val DEEP_ORANGE_A_400 = Color(0xFF3D00)
    val DEEP_ORANGE_A_700 = Color(0xDD2C00)


    val COLOR_LINE = GREY_400
    val COLOR_ACCENT = TEAL_A_700
    val COLOR_ACCENT_FOCUS = TEAL_A_400
    val COLOR_BACKGROUND = GREY_200

    init {

        UIManager.put("control", COLOR_BACKGROUND)
        UIManager.put("controlShadow", COLOR_BACKGROUND)
        UIManager.put("controlDkShadow", COLOR_BACKGROUND)
        UIManager.put("controlLtHighlight", COLOR_BACKGROUND)

        UIManager.put("scrollbar", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.background", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.darkShadow", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.highlight", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.shadow", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.gradient", Arrays.asList(0, 0, GREY_300, GREY_300, GREY_300))

        UIManager.put("ScrollBar.thumb", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.thumbDarkShadow", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.thumbShadow", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.thumbHighlight", COLOR_BACKGROUND)

        UIManager.put("ScrollBar.track", COLOR_BACKGROUND)
        UIManager.put("ScrollBar.trackHighlight", COLOR_BACKGROUND)

    }


    private fun createFont(name: String, size: Int): Font? {
        var telegraficoFont: Font? = null
        try {
            val myStream = BufferedInputStream(FileInputStream("$FONTS_DIR$name.ttf"))
            val ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream)
            telegraficoFont = ttfBase.deriveFont(Font.PLAIN, size.toFloat())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return telegraficoFont
    }


}

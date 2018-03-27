package com.sup.dev.java_pc.views.widgets;

import com.sup.dev.java_pc.views.GUI;

import javax.swing.*;
import java.awt.*;

public class ZLabel extends JLabel {

    private int w;

    public ZLabel() {
        this("");
    }

    public ZLabel(String text) {
        this(text, -1, SwingConstants.LEFT);
    }
    public ZLabel(String text, Font font) {
        this(text, -1, SwingConstants.LEFT, font);
    }

    public ZLabel(String text, int w) {
        this(text, w, SwingConstants.LEFT);
    }

    public ZLabel(String text, int w, int alignment) {
        this(text, w, alignment, GUI.BODY_1);
    }

    public ZLabel(String text, int w, int alignment, Font font) {
        super("", alignment);
        this.w = w;
        setFont(font);
        setText(text);
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if (getFont() != null)
            setPreferredSize(new Dimension(w == -1 ? getFontMetrics(getFont()).stringWidth(text) : w, getFontMetrics(getFont()).getHeight() + 16));
    }

    public static ZLabel title(String text) {
        ZLabel label = new ZLabel(text);
        label.setFont(GUI.TITLE);
        return label;
    }

}

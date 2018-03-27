package com.sup.dev.java_pc.views.panels;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java_pc.views.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ZClickPanel extends ZPanel implements MouseListener {

    public static final Color FOCUS = GUI.COLOR_ACCENT_FOCUS;
    public static final Color DEFAULT = GUI.WHITE;

    private Color defColor;

    private Callback onClick;

    public ZClickPanel() {
        setOpaque(false);
        setBackground(DEFAULT);
    }

    public ZClickPanel setOnClick(Callback onClick) {
        this.onClick = onClick;
        addMouseListener(this);
        return this;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        defColor = bg;
    }

    @Override
    public void paint(Graphics g) {

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());

        g.fillOval(0,0,getWidth(),getHeight());


        super.paint(g);

    }

    //
    //  Mouse
    //

    private boolean pressed;

    @Override
    public void mouseClicked(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.setBackground(defColor);
        if (pressed && onClick != null) onClick.callback();
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (onClick != null) super.setBackground(FOCUS);
        pressed = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.setBackground(defColor);
        pressed = false;
    }

}

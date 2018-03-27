package com.sup.dev.java_pc.views.widgets;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.tools.ToolsGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ZButton extends JButton implements MouseListener {

    public static final Color DEFAULT = GUI.COLOR_ACCENT;
    public static final Color FOCUS = GUI.COLOR_ACCENT_FOCUS;

    protected Color color_default = DEFAULT;
    protected Color color_focus = FOCUS;
    private boolean cornedLeft = true;
    private boolean cornedRight = true;

    public ZButton() {
        this("");
    }

    public ZButton(String text) {
        this(GUI.S_256, text);
    }

    public ZButton(int w, String text, Callback onClick) {
        this(w, text);
        setOnClick(onClick);
    }

    public ZButton(int w, String text, boolean cornedLeft, boolean cornedRight, Callback onClick) {
        this(w, text, onClick);
        this.cornedLeft = cornedLeft;
        this.cornedRight = cornedRight;
    }

    public ZButton(int w, String text) {
        setFont(GUI.BUTTON);
        setBackground(DEFAULT);
        setText(text);
        setPreferredSize(new Dimension(w, getFontMetrics(getFont()).getHeight() + 16));
        setBorder(null);
        addMouseListener(this);
        setOpaque(false);
        setContentAreaFilled(false);
    }

    public ZButton setOnClick(Callback callback) {
        for (ActionListener l : getActionListeners())
            removeActionListener(l);
        super.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.callback();
            }
        });
        return this;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(getBackground());

        ToolsGui.paintCorners(this, g, cornedLeft, cornedRight);

        super.paint(g);

    }

    public void setColor_default(Color color_default) {
        this.color_default = color_default;
        setBackground(color_default);
        repaint();
    }

    //
    //  Mouse
    //

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(color_default);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(color_focus);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(color_default);
    }

}

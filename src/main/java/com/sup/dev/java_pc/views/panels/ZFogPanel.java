package com.sup.dev.java_pc.views.panels;

import com.sup.dev.java.classes.callbacks.simple.Callback;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ZFogPanel extends ZPanel {

    private Callback onPressed;
    private ZPanel contentPanel = new ZPanel();

    public ZFogPanel() {

        setBackground(new Color(0x80000000, true));
        contentPanel.setPadding(24,24,24,24);
        add(contentPanel);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (onPressed != null)
                    onPressed.callback();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void setContent(Component content){
        contentPanel.removeAll();
        contentPanel.add(content);
    }

    public void setOnPressed(Callback onPressed) {
        this.onPressed = onPressed;
    }

}

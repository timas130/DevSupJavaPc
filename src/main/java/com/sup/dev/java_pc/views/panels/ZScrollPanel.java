package com.sup.dev.java_pc.views.panels;

import javax.swing.*;
import java.awt.*;

public class ZScrollPanel extends JScrollPane {

    private final ZPanel panel = new ZPanel();

    public ZScrollPanel(){
        setViewportView(panel);
        setBorder(null);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        setViewportView(panel);

        getVerticalScrollBar().setUnitIncrement(10);
    }

    @Override
    public Component add(Component comp) {
        return panel.add(comp);
    }

    @Override
    public void remove(Component comp) {
        panel.remove(comp);
    }

    @Override
    public void remove(int index) {
        panel.remove(index);
    }

    @Override
    public void removeAll() {
        panel.removeAll();
    }

    public ZPanel getPanel() {
        return panel;
    }
}

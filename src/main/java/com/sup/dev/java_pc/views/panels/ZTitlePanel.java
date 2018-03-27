package com.sup.dev.java_pc.views.panels;

import javax.swing.*;
import java.awt.*;

public class ZTitlePanel {

    private final JP jPanel = new JP();
    private Component title;
    private Component content;

    public void setTitle(JComponent title) {
        if (this.title != null)
            jPanel.remove(this.title);
        this.title = title;
        jPanel.add(title);
        jPanel.setBounds(jPanel.getX(), jPanel.getY(), jPanel.getWidth(), jPanel.getHeight());
        jPanel.repaint();
    }

    public void setContent(Component content) {
        if (this.content != null)
            jPanel.remove(this.content);
        this.content = content;
        jPanel.add(content);
        jPanel.setBounds(jPanel.getX(), jPanel.getY(), jPanel.getWidth(), jPanel.getHeight());
        jPanel.repaint();
    }

    public JComponent getView() {
        return jPanel;
    }

    public void setBounds(int x, int y, int width, int height) {
        jPanel.setBounds(x, y, width, height);
    }

    private class JP extends JPanel{

        public JP(){
            super(null);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            if (title != null)
                title.setBounds(0, 0, width, 48);
            if (content != null)
                content.setBounds(0, 48, width, height - 48);
        }
    }




}

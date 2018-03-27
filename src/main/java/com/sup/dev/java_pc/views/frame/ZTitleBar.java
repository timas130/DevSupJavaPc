package com.sup.dev.java_pc.views.frame;

import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.widgets.ZIcon;
import com.sup.dev.java_pc.views.widgets.ZLabel;

import javax.swing.*;

public class ZTitleBar extends JPanel{

    private final ZIcon iBack =new ZIcon(GUI.ic_back_24);
    private final ZLabel title = new ZLabel();


    public ZTitleBar(ZFrame frame) {
        title.setFont(GUI.HEADLINE);

        add(iBack);
        add(title);

        iBack.setOnClick(frame::onBackPressed);

        setLayout(null);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setBackVisible(boolean b) {
        iBack.setVisible(b);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        iBack.setBounds(0, 0, height, height);
        title.setBounds(height + 24, 0, width - height * 2, height);
        super.setBounds(x, y, width, height);
    }

}

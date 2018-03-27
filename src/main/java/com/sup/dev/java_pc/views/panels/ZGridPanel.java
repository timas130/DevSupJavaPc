package com.sup.dev.java_pc.views.panels;

import javax.swing.*;
import java.awt.*;

public class ZGridPanel extends JPanel{

    private final FlowLayout  layout;

    public ZGridPanel(){
        layout = new FlowLayout();
        setLayout(layout);
    }

    @Override
    public Component add(Component comp) {
        return add(getComponentCount(), comp);
    }

    public Component add(int position, Component comp) {

       /* GridBagConstraints c = new GridBagConstraints();

        layout.set(comp, c);*/
        Component add = super.add(comp, position);

        updateUI();

        return add;
    }

}

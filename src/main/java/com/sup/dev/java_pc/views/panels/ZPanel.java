package com.sup.dev.java_pc.views.panels;

import javax.swing.*;
import java.awt.*;

public class ZPanel extends JPanel {

    public enum Orientation {VERTICAL, HORIZONTAL}

    private final GridBagLayout layout;
    private final Orientation orientation;

    private int pl = 0, pt = 0, pr = 0, pb = 0;
    private int gravity = GridBagConstraints.CENTER;

    public ZPanel() {
        this(Orientation.VERTICAL);
    }

    public ZPanel(Orientation orientation) {
        this.orientation = orientation;

        layout = new GridBagLayout();
        setLayout(layout);
    }


    public void setGravity(int g) {
        this.gravity = g;
    }

    @Override
    public Component add(Component comp) {
        return addWithMargin(comp, pl, pt, pr, pb);
    }

    @Override
    public Component add(Component comp, int position) {
        return add(position, comp, pl, pt, pr, pb, gravity);
    }

    public Component addWithMargin(Component comp, int l, int t, int r, int b) {
        return add(getComponentCount(), comp, l, t, r, b, gravity);
    }

    public Component addWithGravity(Component comp, int gravity) {
        return add(getComponentCount(), comp, pl, pt, pr, pb, gravity);
    }

    public Component add(Component comp, int l, int t, int r, int b) {
        return add(getComponentCount(), comp, l, t, r, b, gravity);
    }

    public Component add(int position, Component comp, int l, int t, int r, int b) {
        return add(position, comp, l, t, r, b, gravity);
    }

    public Component add(int position, Component comp, int l, int t, int r, int b, int gravity) {

        GridBagConstraints c = new GridBagConstraints();

        if (orientation == Orientation.VERTICAL)
            c.gridwidth = GridBagConstraints.REMAINDER;
        else
            c.gridheight = GridBagConstraints.REMAINDER;


        c.insets = new Insets(t, l, b, r);

        c.anchor = gravity;
        c.fill = GridBagConstraints.NONE;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;

        layout.setConstraints(comp, c);
        Component add = super.add(comp, position);

        updateUI();

        return add;
    }

    public void setPadding(int pl, int pt, int pr, int pb) {
        this.pl = pl;
        this.pt = pt;
        this.pr = pr;
        this.pb = pb;
    }


    @Override
    public void removeAll() {
        super.removeAll();
        updateUI();
    }

    @Override
    public void remove(int index) {
        super.remove(index);
        updateUI();
    }

}

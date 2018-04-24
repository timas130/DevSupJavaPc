package com.sup.dev.java_pc.views.fields;


import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.Callback1;
import com.sup.dev.java.classes.providers.Provider1;
import com.sup.dev.java_pc.views.GUI;

import javax.swing.*;
import java.awt.*;

public class ZTextPane extends JScrollPane{

    private final TextPane textPane;

    private class TextPane extends JTextPane implements Field{

        @Override
        public void setBackgroundSuper(Color bg) {
            super.setBackground(bg);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            logic.paint(g);
        }

    }

    public ZTextPane() {
        this(GUI.S_512);
    }

    protected final Logic logic;

    public ZTextPane(int w) {
        this(w, "");
    }

    public ZTextPane(int w, String hint) {
        super();
        textPane = new TextPane();
        logic = new Logic(textPane, w, hint);
        setViewportView(textPane);
        setPreferredSize(textPane.getPreferredSize());

        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        repaint();
    }



    public void showIfError() {
        logic.showIfError();
    }

    public void setErrorIfEmpty() {
        logic.setErrorIfEmpty();
    }

    public void setOnChangedErrorChecker(Provider1<String, Boolean> onChanged) {
        logic.setOnChangedErrorChecker(onChanged);
    }

    public void setOnChanged(Callback1<String> onChanged) {
        logic.addOnChanged(onChanged);
    }

    public void setFilter(Provider1<String, Boolean> filter) {
        logic.setFilter(filter);
    }

    public void setHint(String hint) {
        logic.setHint(hint);
    }

    public void setLines(int lines) {
        logic.setLines(lines);
        setPreferredSize(textPane.getPreferredSize());
    }

    public void setOnRightClick(Callback onRightClick) {
        logic.setOnRightClick(onRightClick);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(GUI.COLOR_LINE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public void setBackground(Color bg) {
        if(logic == null)
            super.setBackground(bg);
        else
            logic.setBackground(bg);

    }

    public void setText(String text) {
        textPane.setText(text);
    }

    public void setOnlyNum(boolean onlyNum) {
        logic.setOnlyNum();
    }

    public void setOnlyNumDouble(boolean onlyNumDouble) {
        logic.setOnlyNumDouble();
    }

    //
    //  Getters
    //


    public String getText() {
        return textPane.getText();
    }

    public int getInt() {
        return logic.getInt();
    }

    public double getDouble() {
        return logic.getDouble();
    }

    public String getHint(){
        return logic.getHint();
    }


}

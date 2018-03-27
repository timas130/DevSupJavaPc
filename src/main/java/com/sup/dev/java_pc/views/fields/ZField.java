package com.sup.dev.java_pc.views.fields;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.CallbackSource;
import com.sup.dev.java.classes.providers.ProviderArg;
import com.sup.dev.java_pc.views.GUI;

import javax.swing.*;
import java.awt.*;

public class ZField extends JTextField implements Field{

    public static final Color COLOR_ERROR = Logic.COLOR_ERROR;

    protected final Logic logic;

    public ZField() {
        this(GUI.S_256);
    }

    public ZField(int w) {
        this(w, "");
    }

    public ZField(int w, String hint) {
        logic = new Logic(this, w, hint);
        setDisabledTextColor(GUI.GREY_500);
    }

    public void showIfError() {
        logic.showIfError();
    }

    public void setErrorIfEmpty() {
        logic.setErrorIfEmpty();
    }

    public void setOnChangedErrorChecker(ProviderArg<String, Boolean> onChanged) {
        logic.setOnChangedErrorChecker(onChanged);
    }

    public void addOnChanged(CallbackSource<String> onChanged) {
        logic.addOnChanged(onChanged);
    }

    public void setFilter(ProviderArg<String, Boolean> filter) {
        logic.setFilter(filter);
    }

    public void setHint(String hint) {
        logic.setHint(hint);
    }

    public void setLines(int lines) {
        logic.setLines(lines);
    }

    public void setOnRightClick(Callback onRightClick) {
       logic.setOnRightClick(onRightClick);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        logic.paint(g);

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

    @Override
    public void setBackgroundSuper(Color bg) {
        super.setBackground(bg);
    }

    public void setOnlyInt( ) {
        logic.setOnlyNum();
    }

    public void setOnlyDouble( ) {
        logic.setOnlyNumDouble();
    }

    public void setErrorChecker(ProviderArg<String, Boolean> errorChecker) {
        logic.setErrorChecker(errorChecker);
    }

    //
    //  Getters
    //

    public int getInt() {
        return logic.getInt();
    }

    public double getDouble() {
        return logic.getDouble();
    }

    public String getHint(){
        return logic.getHint();
    }

    public boolean isError(){
        return logic.isError();
    }


}

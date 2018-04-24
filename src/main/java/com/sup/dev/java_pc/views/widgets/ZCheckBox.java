package com.sup.dev.java_pc.views.widgets;

import com.sup.dev.java.classes.callbacks.simple.Callback1;
import com.sup.dev.java_pc.views.GUI;

import java.awt.*;

public class ZCheckBox extends ZButton{

    private static final Color ACTIVE = GUI.GREEN_A_400;
    private static final Color DEACTIVATED = GUI.WHITE;

    private boolean isChecked;
    private Callback1<Boolean> onCheckChange;

    public ZCheckBox(){
        this("");
    }

    public ZCheckBox(String text){
        this(GUI.S_128, text);
    }

    public ZCheckBox(int w, String text){

        color_default = DEACTIVATED;

        setFont(GUI.BODY_2);
        setText(text);
        setBackground(DEACTIVATED);
        setPreferredSize(new Dimension(w, getFontMetrics(getFont()).getHeight() + 16));
        setMargin(new Insets(0, 0, 0, 0));

        addActionListener(v -> onClick());
    }

    private void onClick(){
        setChecked(!isChecked);
    }

    public void setChecked(boolean b){
        isChecked = b;
        color_default = isChecked ?ACTIVE:DEACTIVATED;
        setBackground(color_default);
        if(onCheckChange != null)onCheckChange.callback(isChecked);
    }

    public void setOnCheckChange(Callback1<Boolean> onCheckChange) {
        this.onCheckChange = onCheckChange;
    }

    public boolean isChecked(){
        return isChecked;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(GUI.COLOR_LINE);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

}

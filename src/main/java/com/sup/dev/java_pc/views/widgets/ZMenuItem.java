package com.sup.dev.java_pc.views.widgets;

import com.sup.dev.java.classes.callbacks.simple.CallbackSource;
import com.sup.dev.java_pc.views.GUI;

import javax.swing.*;
import java.awt.*;

public class ZMenuItem extends JMenuItem {


    public ZMenuItem(String text, CallbackSource<String> callback){
        setBackground(Color.WHITE);
        setFont(GUI.BODY_2);
        setText(text);

        addActionListener(e ->  callback.callback(text));

    }

}

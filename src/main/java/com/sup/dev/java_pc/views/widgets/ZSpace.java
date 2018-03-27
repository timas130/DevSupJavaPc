package com.sup.dev.java_pc.views.widgets;

import javax.swing.*;
import java.awt.*;

public class ZSpace extends JComponent{

    public ZSpace(int w){
        setPreferredSize(new Dimension(w, 0));
    }

    public ZSpace(int w, int h){
        setPreferredSize(new Dimension(w, h));
    }

}

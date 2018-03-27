package com.sup.dev.java_pc.tools;

import java.awt.*;

public class ToolsPcScreen {


    public static double getScreenWidth(){
        return Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }

    public static double getScreenHeight(){
        return Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }

    public static double getScreenDPI(){
        return 240;
    }

    public static double getScreenDPRation(){
        return getScreenDPI()/160;
    }

    public static double getScreenSPRation(){
        return getScreenDPI()/160;
    }

}

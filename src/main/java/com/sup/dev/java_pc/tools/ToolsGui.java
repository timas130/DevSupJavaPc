package com.sup.dev.java_pc.tools;

import javax.swing.*;
import java.awt.*;

public class ToolsGui {

    public static boolean childOf(Component child, Component parent) {
        Container c = child.getParent();
        return c == parent || c != null && childOf(c, parent);
    }
    
    public static void paintCorners(JComponent c, Graphics g, boolean cornedLeft, boolean cornedRight){
        
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if(cornedLeft && cornedRight){
            g.fillRect(4, 0, c.getWidth() - 8, c.getHeight());
            g.fillRect(0, 4, c.getWidth(), c.getHeight() - 8);
            g.fillOval(0, 0, 8, 8);
            g.fillOval(0, c.getHeight() - 8, 8, 8);
            g.fillOval(c.getWidth() - 8, 0, 8, 8);
            g.fillOval(c.getWidth() - 8, c.getHeight() - 8, 8, 8);
        }else if(cornedLeft){
            g.fillRect(4, 0, c.getWidth() - 4, c.getHeight());
            g.fillRect(0, 4, c.getWidth(), c.getHeight() - 8);
            g.fillOval(0, 0, 8, 8);
            g.fillOval(0, c.getHeight() - 8, 8, 8);
        }else if(cornedRight){
            g.fillRect(0, 0, c.getWidth() - 4, c.getHeight());
            g.fillRect(0, 4, c.getWidth(), c.getHeight() - 8);
            g.fillOval(c.getWidth() - 8, 0, 8, 8);
            g.fillOval(c.getWidth() - 8, c.getHeight() - 8, 8, 8);
        }else{
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        
    }


}

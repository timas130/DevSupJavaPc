package com.sup.dev.java_pc.views.widgets;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.tools.ToolsImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ZIcon extends JComponent implements Callback, MouseListener {

    private static final Color FOCUS = GUI.COLOR_ACCENT_FOCUS;
    private static final Color DEFAULT = null;

    private boolean iconVisible = true;
    private BufferedImage image;

    private Callback onClick;

    public ZIcon(String iconPath) {
        setImage(iconPath);
        setOpaque(false);
    }

    public ZIcon(String iconPath,Callback onClick) {
       this(iconPath);
       setOnClick(onClick);
    }

    public void setImage(String iconPath) {
        try {
            image = ImageIO.read(new File(GUI.ICONS_DIR + iconPath));
            if (image.getWidth() > 24)
                ToolsImage.replaceColors(image, 0xFF7474a6);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        setPreferredSize(new Dimension(image.getWidth(null) + 8, image.getHeight(null) + 8));
        repaint();
    }

    public void setOnClick(Callback onClick) {
        this.onClick = onClick;
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {

        if(iconVisible) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getBackground() == FOCUS) {
                g.setColor(getBackground());
                int r = Math.min(getWidth(), getHeight()) / 2;
                g.fillOval(getWidth() / 2 - r, getHeight() / 2 - r, r * 2, r * 2);
            }
            g.drawImage(image, (getWidth() - image.getWidth(null)) / 2, (getHeight() - image.getHeight(null)) / 2, null);
        }
        super.paint(g);
    }

    public void setIconVisible(boolean iconVisible) {
        this.iconVisible = iconVisible;
    }

    //
    //  Mouse
    //

    private boolean pressed;

    @Override
    public void mouseClicked(MouseEvent e) {
        pressed = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        setBackground(DEFAULT);
        if (pressed && iconVisible && onClick != null) onClick.callback();
        pressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (onClick != null) setBackground(FOCUS);
        pressed = false;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(DEFAULT);
        pressed = false;
    }

    @Override
    public void callback() {

    }



}

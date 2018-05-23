package com.sup.dev.java_pc.views.frame;

import com.sup.dev.java.tools.ToolsThreads;
import com.sup.dev.java.utils.interfaces.ToolsThreads;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.panels.ZFogPanel;
import com.sup.dev.java_pc.views.panels.ZTitlePanel;
import com.sup.dev.java_pc.views.widgets.ZLabel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ZFrame extends JFrame {

    private final ToolsThreads ToolsThreads = new ToolsThreads(null);
    public static ZFrame instance;

    private final ZTitleBar bar;
    private final ZTitlePanel titlePanel;
    private final ArrayList<ZScreen> backStack = new ArrayList<>();

    private ZScreen currentScreen;

    public ZFrame() {

        if(instance != null)
            throw new RuntimeException("Multi frame not unsupported yet.");

        this.instance = this;

        titlePanel = new ZTitlePanel();
        bar = new ZTitleBar(this);

        titlePanel.setTitle(bar);

        setSize(1200, 800);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new OverlayLayout(getContentPane()));
        getContentPane().add(titlePanel.getView());
    }

    void onBackPressed() {
        if (currentScreen.isCanBack())
            back();
    }

    public void back() {
        setScreen(backStack.remove(backStack.size() - 1));
    }

    public void replaceScreen(ZScreen contentPanel) {
        setScreen(contentPanel);
    }

    public void addScreen(ZScreen contentPanel) {
        backStack.add(currentScreen);
        setScreen(contentPanel);
    }

    private void setScreen(ZScreen contentPanel) {

        hideMessage();

        if (currentScreen != null)
            getContentPane().remove(currentScreen.getView());

        currentScreen = contentPanel;
        titlePanel.setContent(currentScreen.getView());
        bar.setBackVisible(currentScreen.isCanBack());
        bar.setTitle(currentScreen.getTitle());
    }

    //
    //  Message
    //

    private ZLabel message;

    public void showMessage(String text) {

        hideMessage();

        ZLabel label = new ZLabel(text, GUI.S_512, SwingConstants.CENTER){
            @Override
            public void paint(Graphics g) {

                g.setColor(GUI.GREY_500);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.fillRect(getHeight()/2, 0, getWidth()-getHeight(), getHeight());
                g.fillOval(0, 0, getHeight(), getHeight());
                g.fillOval(getWidth()-getHeight(), 0, getHeight(), getHeight());

                super.paint(g);
            }
        };
        message = label;
        message.setForeground(GUI.GREY_50);
        label.setFont(GUI.TITLE);

        label.setBounds( getWidth()/5, getHeight() - getHeight()/5, getWidth() -  getWidth()/5*2, 60);
        getContentPane().add(label, 0);
        getContentPane().setComponentZOrder(label, 0);
        getContentPane().repaint();
        ToolsThreads.thread(GUI.SLEEP_2000, () -> {
            if(message == label)
                hideMessage();
        });
    }

    public void hideMessage(){

        if(message == null)return;

        getContentPane().remove(message);
        message = null;
        repaint();
    }

    //
    //  Dialog
    //

    private ZFogPanel dialogPanel;

    public void showDialog(Component component) {
        showDialog(component, true);
    }

    public void showDialog(Component component, boolean hideOnClick) {

        if(dialogPanel != null)
            hideDialog();

        dialogPanel = new ZFogPanel();
        dialogPanel.setContent(component);
        getContentPane().add(dialogPanel, 0);

        if (hideOnClick) dialogPanel.setOnPressed(this::hideDialog);

        requestFocus();
        revalidate();
        repaint();
    }

    public void hideDialog(){
        getContentPane().remove(dialogPanel);
        dialogPanel = null;
        requestFocus();
        repaint();
    }

}

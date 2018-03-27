package com.sup.dev.java_pc.views.frame;

import javax.swing.*;

public abstract class ZScreen{

    private String title;
    private boolean canBack = true;

    public ZScreen() {
        this("");
    }

    public ZScreen(String title) {
        this.title = title;
    }

    protected void setCanBack(boolean canBack) {
        this.canBack = canBack;
    }

    public boolean isCanBack() {
        return canBack;
    }

    public String getTitle() {
        return title;
    }

    protected abstract JComponent getView();

}

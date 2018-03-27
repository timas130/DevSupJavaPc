package com.sup.dev.java_pc.views.panels;

import java.awt.*;

public class ZPairPanel {

    private final ZPanel container = new ZPanel(ZPanel.Orientation.HORIZONTAL);
    protected final ZPanel right = new ZPanel(ZPanel.Orientation.VERTICAL);
    protected final ZPanel left = new ZPanel(ZPanel.Orientation.VERTICAL);

    public ZPairPanel(){

        container.setGravity(GridBagConstraints.NORTH);

        container.add(right);
        container.add(left);

    }

    public ZPanel getView() {
        return container;
    }


}

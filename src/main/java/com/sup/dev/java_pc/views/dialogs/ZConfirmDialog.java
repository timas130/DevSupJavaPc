package com.sup.dev.java_pc.views.dialogs;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java_pc.views.panels.ZPanel;
import com.sup.dev.java_pc.views.widgets.ZButton;
import com.sup.dev.java_pc.views.widgets.ZLabel;

public class ZConfirmDialog extends ZPanel {

    private final ZButton yes;
    private final ZButton no;

    public ZConfirmDialog(String text, String yesText, String noText){
        super(Orientation.VERTICAL);

        ZLabel label = new ZLabel(text);
        yes = new ZButton(yesText);
        no = new ZButton(noText);

        ZPanel panel = new ZPanel(Orientation.HORIZONTAL);
        panel.add(no, 0, 0, 24, 0);
        panel.add(yes);

        add(label, 0, 0, 0, 24);
        add(panel);
    }

    public void setOnYes(Callback onYes){
        yes.setOnClick(onYes);
    }

    public void setOnNo(Callback onNo){
        no.setOnClick(onNo);
    }

}

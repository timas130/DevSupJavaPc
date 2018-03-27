package com.sup.dev.java_pc.views.table;

import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.panels.ZPanel;
import com.sup.dev.java_pc.views.widgets.ZLabel;
import com.sup.dev.java_pc.views.widgets.ZSpace;

class ZTableLabels extends ZPanel {

  //  private boolean completed = false;

    ZTableLabels(){
        super(Orientation.HORIZONTAL);
    }

    protected void complete(ZTableRow row){
       // add(new ZSpace(8));

      // completed = true;

        for(ZTableCell cell : row.getCells())
            add(cell.getLabel(), cell.getSize());

        add(new ZSpace( GUI.S_24)); //  Удаление
    }

    private void add(String text, int w){
        ZLabel label = new ZLabel(text, w);
        label.setFont(GUI.CAPTION);
        add(label);
    }

}

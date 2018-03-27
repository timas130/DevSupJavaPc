package com.sup.dev.java_pc.views.table;


import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.libs.json.Json;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.dialogs.ZConfirmDialog;
import com.sup.dev.java_pc.views.frame.ZFrame;
import com.sup.dev.java_pc.views.panels.ZPanel;
import com.sup.dev.java_pc.views.widgets.ZIcon;
import com.sup.dev.java_pc.views.widgets.ZSpace;

import java.awt.*;
import java.util.ArrayList;

public class ZTableRow {

    private final ZTable table;
    private final ZPanel panel = new ZPanel(ZPanel.Orientation.HORIZONTAL);
    private final ArrayList<ZTableCell> cells = new ArrayList<>();
    private final ZIcon icon_Remove = new ZIcon(GUI.ic_minus_18, this::onRemoveClicked);

    private int type;
    private Callback onUpdate;

    public ZTableRow(ZTable table, int type) {
        this.table = table;
        this.type = type;
    }

    public <K extends ZTableCell> K addCell(K cell) {

        cell.init(this, cells.size());
        cells.add(cell);

        if (cell.getType() >= type)
            panel.add(cell.getView());
        else
            panel.add(new ZSpace(cell.getView().getPreferredSize().width));

        return cell;
    }

    public void onCreate() {
        panel.add(icon_Remove);
    }

    private void onRemoveClicked() {

        if(isEmpty() && table.getChildren(this).isEmpty()){
            table.removeRow(this);
            return;
        }

        ZConfirmDialog confirmDialog = new ZConfirmDialog("Remove row and all subrows?", "Remove", "Cancel");
        confirmDialog.setOnYes(() -> {
            table.removeRow(this);
            ZFrame.instance.hideDialog();
        });
        confirmDialog.setOnNo(() -> ZFrame.instance.hideDialog());
        ZFrame.instance.showDialog(confirmDialog);
    }

    public final void update() {
        icon_Remove.setIconVisible(!table.isUnremovable(type));
        if (onUpdate != null)
            onUpdate.callback();
    }

    public void showErrors() {
        for (ZTableCell cell : cells)
            cell.showIfError();
    }

    public Json toJson(String... keys){
        Json json = new Json();
        json.put("row_type", type);
        for(int i = 0; i < cells.size(); i++)
            cells.get(i).toJson(keys[i], json);
        return json;
    }

    public void fromJson(Json json, String... keys){
        for(int i = 0; i < cells.size(); i++)
            cells.get(i).fromJson(keys[i],json);

    }

    //
    //  Setters
    //

    public void setOnUpdate(Callback onUpdate) {
        this.onUpdate = onUpdate;
    }

    public void setValue(int index, Object value, boolean asOriginal) {
        cells.get(index).setValue(value, asOriginal);
    }

    //
    //  Getters
    //

    public boolean isError(){
        for(ZTableCell cell : cells)
            if(cell.isError())
                return true;
        return false;
    }


    public ZTable getTable() {
        return table;
    }

    public int getType() {
        return type;
    }

    public Component getView() {
        return panel;
    }

    public ArrayList<ZTableCell> getCells() {
        return cells;
    }

    public <K extends ZTableCell> K getCell(int index) {
        return (K) cells.get(index);
    }

    public <K> K getValue(int index) {
        return (K) cells.get(index).getValue();
    }

    public boolean isEmpty() {
        for (ZTableCell cell : cells)
            if (!cell.isEmpty())
                return false;
        return true;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.sup.dev.java_pc.views.table.cells;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.libs.json.Json;
import com.sup.dev.java_pc.views.table.ZTableCell;
import com.sup.dev.java_pc.views.widgets.ZCheckBox;

import java.awt.*;

public class ZTableCellCheck extends ZTableCell {

    private final ZCheckBox checkBox;

    public ZTableCellCheck(int size, String label) {
        this(0, size, label);
    }

    public ZTableCellCheck(int type, int size, String label) {
        super(type, size, label);

        checkBox = new ZCheckBox(size, label);

        setValue(false, true);
    }

    @Override
    public void toJson(String key, Json json) {
        json.put(key, checkBox.isChecked());
    }

    @Override
    public void fromJson(String key, Json json) {
        checkBox.setChecked(json.getBoolean(key));
    }

    @Override
    public void setShowChanges() {

    }

    //
    //  Setters
    //


    @Override
    public void setEnabled(boolean b) {
        checkBox.setEnabled(b);
    }

    @Override
    public void setOnRightClick(Callback onRightClick) {

    }

    @Override
    protected void setValue(Object value) {
        checkBox.setChecked((Boolean)value);
    }

    //
    //  Getters
    //

    @Override
    public boolean isEmpty() {
        return checkBox.isChecked() == (Boolean) getOriginalValue();
    }

    @Override
    public void showIfError() {

    }

    @Override
    protected Object getCellValue() {
        return checkBox.isChecked();
    }

    @Override
    protected Component getView() {
        return checkBox;
    }

    @Override
    public boolean isError() {
        return false;
    }
}

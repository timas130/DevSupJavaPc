package com.sup.dev.java_pc.views.table.cells;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.libs.json.Json;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.fields.ZField;
import com.sup.dev.java_pc.views.table.ZTableCell;

import java.awt.*;

public abstract class ZTableCellField extends ZTableCell {

    protected final ZField field;

    protected ZTableCellField(int size, String label, boolean canBeEmpty) {
        this(0, size, label, canBeEmpty);
    }

    protected ZTableCellField(int type, int size, String label, boolean canBeEmpty) {
        super(type, size, label);

        field = new ZField(size, label);

        if (!canBeEmpty) field.setErrorIfEmpty();
    }

    @Override
    public void toJson(String key, Json json) {
        json.put(key, field.getText());
    }

    @Override
    public void fromJson(String key, Json json) {
        field.setText(json.getString(key));
    }

    @Override
    public void showIfError() {
        field.showIfError();
    }

    @Override
    public void setShowChanges() {
        field.addOnChanged(source -> field.setBackground(isChanged()? GUI.LIGHT_GREEN_100:GUI.WHITE));
    }

    //
    //  Setters
    //


    @Override
    public void setEnabled(boolean b) {
        field.setEnabled(b);
    }

    @Override
    protected void setValue(Object value) {
        field.setText(value == null ? "" : value.toString());
    }

    @Override
    public void setOnRightClick(Callback onRightClick) {
        field.setOnRightClick(onRightClick);
    }

    //
    //  Getters
    //

    public String getText() {
        return field.getText();
    }

    @Override
    public boolean isEmpty() {
        return field.getText().isEmpty();
    }

    @Override
    protected Component getView() {
        return field;
    }

    @Override
    public boolean isError() {
        return field.isError();
    }
}

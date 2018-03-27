package com.sup.dev.java_pc.views.table;

import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.providers.ProviderArg;
import com.sup.dev.java.libs.json.Json;

import java.awt.*;

public abstract class ZTableCell {

    private final int type;
    private final int size;
    private final String label;

    private int index;
    private ZTableRow row;
    private Object originalValue;

    public ZTableCell(int type, int size, String label) {
        this.type = type;
        this.size = size;
        this.label = label;
    }

    public final void setValue(Object value, boolean asOriginal) {
        if (asOriginal)
            originalValue = value;
        setValue(value);
    }

    public final boolean isChanged() {
        Object value = getValue();

        if(value == null && originalValue == null)
            return false;
        if(value == null)
            return true;
        if(originalValue == null)
            return true;
        if(value == originalValue)
            return false;
        if(value.equals(originalValue))
            return false;
        if(value.toString().equals(originalValue.toString()))
            return false;

        return true;
    }

    public final <K> K getValue() {
        if (row.getType() > type) {
            return row.getTable().getParentRow(row).getCell(index).getValue();
        } else
            return (K) getCellValue();
    }

    public abstract void toJson(String key, Json json);

    public abstract void fromJson(String key, Json json);


    //
    //  Abstract
    //

    public abstract void setShowChanges();

    public abstract void setOnRightClick(Callback onRightClick);

    public abstract boolean isEmpty();

    public abstract void setEnabled(boolean b);

    public abstract void showIfError();

    public abstract boolean isError();

    protected abstract Object getCellValue();

    protected abstract void setValue(Object value);

    protected abstract Component getView();

    //
    //  Setters
    //

    void init(ZTableRow row, int index) {
        this.row = row;
        this.index = index;
    }

    public void setOnChangedErrorChecker(ProviderArg<String, Boolean> checker) {

    }

    //
    //  Getters
    //

    public final int getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public int getSize() {
        return size;
    }

    public Object getOriginalValue() {
        return originalValue;
    }
}

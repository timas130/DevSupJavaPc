package com.sup.dev.java_pc.views.table.cells;


import com.sup.dev.java.classes.callbacks.simple.Callback;
import com.sup.dev.java.classes.callbacks.simple.CallbackSource;
import com.sup.dev.java.classes.providers.ProviderArg;
import com.sup.dev.java.libs.json.Json;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.fields.ZFieldSelect;
import com.sup.dev.java_pc.views.table.ZTableCell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ZTableCellSelect extends ZTableCell {

    protected final ZFieldSelect field;

    public ZTableCellSelect(int size, String label, boolean canBeEmpty) {
        this(0, size, label, canBeEmpty, null);
    }

    public ZTableCellSelect(int size, String label, boolean canBeEmpty, List values) {
        this(0, size, label, canBeEmpty, values);
    }

    public ZTableCellSelect(int type, int size, String label, boolean canBeEmpty) {
        this(type, size, label, canBeEmpty, null);
    }

    public ZTableCellSelect(int type, int size, String label, boolean canBeEmpty, List values) {
        super(type, size, label);

        field = new ZFieldSelect<>(size, label, values);

        if(!canBeEmpty) field.setErrorIfEmpty();
    }

    @Override
    public void toJson(String key, Json json) {
        json.put(key, field.getText());
    }

    @Override
    public void fromJson(String key,Json json) {
        field.setText(json.getString(key));
    }



    @Override
    public void showIfError() {
        field.showIfError();
    }

    //
    //  Setters
    //


    @Override
    public void setEnabled(boolean b) {
        field.setEnabled(b);
    }

    public void setValues(ArrayList values){
        field.setValues(values);
    }

    public void setOnSelect(Callback onSelect){
      setOnSelect(source -> onSelect.callback());
    }

    public void setOnSelect(CallbackSource onSelect){
        field.setOnSelect(onSelect);
    }

    @Override
    protected void setValue(Object value) {
        field.setText(value.toString());
    }

    @Override
    public void setShowChanges() {
        field.addOnChanged(source -> field.setBackground(isChanged()? GUI.LIGHT_GREEN_100:GUI.WHITE));
    }

    @Override
    public void setOnRightClick(Callback onRightClick) {
        field.setOnRightClick(onRightClick);
    }

    @Override
    public void setOnChangedErrorChecker(ProviderArg<String, Boolean> checker) {
        field.setOnChangedErrorChecker(checker);
    }

    public String getText(){
        return field.getText();
    }

    //
    //  Getters
    //


    @Override
    public boolean isError() {
        return field.isError();
    }

    @Override
    protected Component getView() {
        return field;
    }

    @Override
    public boolean isEmpty() {
        return field.getText().isEmpty();
    }

    @Override
    protected Object getCellValue() {
        return field.getSelected();
    }


}

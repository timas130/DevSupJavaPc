package com.sup.dev.java_pc.views.table.cells;

public class ZTableCellString extends ZTableCellField {

    public ZTableCellString(int size, String label, boolean canBeEmpty) {
        this(0, size, label, canBeEmpty);
    }

    public ZTableCellString(int type, int size, String label, boolean canBeEmpty) {
        super(type, size, label, canBeEmpty);
        setValue("", true);
    }

    @Override
    protected Object getCellValue() {
        return field.getText();
    }
}

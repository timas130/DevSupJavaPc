package com.sup.dev.java_pc.views.table.cells;

public class ZTableCellInt extends ZTableCellField {

    public ZTableCellInt(int size, String label, boolean canBeEmpty) {
        this(0, size, label, canBeEmpty);
    }

    public ZTableCellInt(int type, int size, String label, boolean canBeEmpty) {
        super(type, size, label, canBeEmpty);

        field.setOnlyInt();
        setValue(null, true);
    }

    @Override
    protected Object getCellValue() {
        return field.getInt();
    }

}

package com.sup.dev.java_pc.views.table.cells;

public class ZTableCellDouble extends ZTableCellField{

    public ZTableCellDouble(int size, String label, boolean canBeEmpty) {
        this(0, size, label, canBeEmpty);
    }

    public ZTableCellDouble(int type, int size, String label, boolean canBeEmpty) {
        super(type, size, label, canBeEmpty);

        field.setOnlyDouble();
        setValue(null, true);
    }

    @Override
    protected Object getCellValue() {
        return field.getDouble();
    }
}

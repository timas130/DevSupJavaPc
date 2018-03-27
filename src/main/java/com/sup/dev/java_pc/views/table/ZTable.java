package com.sup.dev.java_pc.views.table;

import com.sup.dev.java.classes.collections.Parser;
import com.sup.dev.java.libs.json.Json;
import com.sup.dev.java.libs.json.JsonArray;
import com.sup.dev.java_pc.views.GUI;
import com.sup.dev.java_pc.views.panels.ZPanel;
import com.sup.dev.java_pc.views.panels.ZScrollPanel;
import com.sup.dev.java_pc.views.widgets.ZSpace;

import java.awt.*;
import java.util.ArrayList;

public abstract class ZTable {

    private final ZScrollPanel scrollPanel = new ZScrollPanel();
    private final ZPanel panel_top = new ZPanel(ZPanel.Orientation.VERTICAL);
    private final ZPanel panel_table = new ZPanel(ZPanel.Orientation.VERTICAL);
    private final ArrayList<ZTableRow> rows = new ArrayList<>();
    private final ArrayList<Integer> unremovable = new ArrayList<>();

    private boolean allUnremovable;
    private boolean errorIfEmpty;
    private ZTableRow currentInitRow;

    public ZTable() {
        ZTableLabels labels = new ZTableLabels();

        scrollPanel.add(panel_top);
        scrollPanel.add(labels);
        scrollPanel.add(panel_table);
        scrollPanel.add(new ZSpace(0, GUI.S_128));

        addRow(0, -1);

        labels.complete(rows.get(0));
    }

    //
    //  Table
    //

    protected void addTopView(Component component, boolean space) {
        panel_top.add(component);
        if (space)
            panel_top.add(new ZSpace(0, 24));

    }

    public boolean isEmpty() {
        return rows.size() == 1 && rows.get(0).isEmpty();
    }

    //
    //  Rows
    //

    protected final ZTableRow instanceRow(int type) {
        ZTableRow row = new ZTableRow(this, type);
        currentInitRow = row;
        initRow(row);
        currentInitRow = null;
        row.onCreate();
        return row;
    }

    protected final ZTableRow addRowEnd(int type, Object... values) {
        return addRow(type, -1, values);
    }

    protected final ZTableRow addRow(int type, int upRow, Object... values) {
        return  addRowNow(type, upRow, true, values);
    }

    private ZTableRow addRowNow(int type, int upRow, boolean tryUseFirst, Object... values) {
        ZTableRow row;
        if (tryUseFirst && rows.size() == 1 && isEmpty()) {
            row = rows.get(0);
            row.setType(type);
        }else
            row = instanceRow(type);

        int offset = 0;
        if (values.length < row.getCells().size())
            for (int i = 0; i < row.getCells().size(); i++) {
                if (row.getCells().get(i).getType() >= type)
                    break;
                offset = i + 1;
            }

        if (!rows.contains(row)) {

            int position = rows.size();

            if (upRow > -1) {
                for (int i = upRow + 1; i < rows.size(); i++)
                    if (rows.get(i).getType() <= row.getType()) {
                        position = i;
                        break;
                    }
            }

            rows.add(position, row);
            panel_table.add(row.getView(), position);
        }

        updateRow(row);

        for (int i = 0; i < values.length; i++)
            row.setValue(i + offset, values[i], true);

        return row;
    }

    public void removeRow(ZTableRow row) {
        for (ZTableRow c : getChildren(row)) {
            panel_table.remove(c.getView());
            rows.remove(c);
        }
        panel_table.remove(row.getView());
        rows.remove(row);

        if (rows.size() == 0)
            addRow(0, -1);
    }

    public void updateRow(ZTableRow row) {
        row.update();
        for (int i = rows.indexOf(row) + 1; i < rows.size(); i++) {
            if (rows.get(i).getType() <= row.getType())
                break;
            rows.get(i).update();
        }
    }

    protected void setCreator(ZTableCell cell) {
        ZTableRow row = currentInitRow;
        cell.setOnRightClick(() -> addRowNow(cell.getType(), rows.indexOf(row), false));
    }

    public ArrayList<ZTableRow> getChildren(ZTableRow row) {
        ArrayList<ZTableRow> children = new ArrayList<>();
        for (int i = rows.indexOf(row) + 1; i < rows.size(); i++) {
            if (rows.get(i).getType() <= row.getType())
                break;
            children.add(rows.get(i));
        }
        return children;
    }

    public ZTableRow getParentRow(ZTableRow row) {
        for (int i = rows.indexOf(row); i > -1; i--) {
            if (rows.get(i).getType() < row.getType())
                return rows.get(i);
        }
        return null;
    }

    public void showErrors() {
        if (!isEmpty() || errorIfEmpty)
            for (ZTableRow row : rows)
                row.showErrors();
    }

    public void updateRows(){
        for (ZTableRow row : rows)
            row.update();
    }

    //
    //  Abstract
    //

    protected abstract void initRow(ZTableRow row);

    //
    //  Setters
    //

    public void setAllUnremovable(){
        allUnremovable = true;
        updateRows();
    }

    public void addUnremovable(int type){
        unremovable.add(type);
        updateRows();
    }

    public void setErrorIfEmpty() {
        this.errorIfEmpty = true;
    }


    //
    //  Getters
    //

    public boolean isError(){
        for (ZTableRow row : getRows())
            if(row.isError())
                return true;
        return false;
    }

    public boolean isUnremovable(int type){
        return allUnremovable || unremovable.contains(type);
    }


    public Parser<Parser<String>> getValues() {
        Parser<Parser<String>> table = new Parser<>();
        for (ZTableRow row : getRows()) {
            Parser<String> r = new Parser<>();
            table.add(r);
            for (ZTableCell cell : row.getCells())
                r.add(cell.getValue().toString());
        }
        return table;
    }

    public JsonArray toJson(String... keys){

        JsonArray jsonArray = new JsonArray();
        for (ZTableRow row : rows)
            jsonArray.put(row.toJson(keys));

        return jsonArray;
    }

    public void fromJson(JsonArray jsonArray, String... keys){
        for(Json j : jsonArray.getJsons()){
            int row_type = j.getInt("row_type");
            ZTableRow row = addRowEnd(row_type);
            row.fromJson(j, keys);
        }


    }


    public Component getView() {
        return scrollPanel;
    }

    public ArrayList<ZTableRow> getRows() {
        return rows;
    }
}

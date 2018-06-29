package com.sup.dev.java_pc.sql;

import java.util.ArrayList;

public class SqlQuerySelect extends SqlQueryWithWhere {


    private final ArrayList<Column> columns = new ArrayList<>();

    private String table;
    private boolean limited;
    private Object limited_offset;
    private Object limited_count;
    private String groupColumn;
    private String sortColumn;
    private boolean sortAB;
    private boolean distinct;

    public SqlQuerySelect(String table) {
        this.table = table;
    }

    public SqlQuerySelect(String table, ArrayList columnsArray) {
        this.table = table;
        for (int i = 0; i < columnsArray.size(); i++)
            if (columnsArray.get(i) instanceof SqlQuerySelect)
                columns.add(new ColumnRequest((SqlQuerySelect) columnsArray.get(i)));
            else
                columns.add(new ColumnString(columnsArray.get(i).toString()));
    }

    public SqlQuerySelect(String table, Object... columnsArray) {
        this.table = table;
        for (int i = 0; i < columnsArray.length; i++)
            if (columnsArray[i] instanceof SqlQuerySelect)
                columns.add(new ColumnRequest((SqlQuerySelect) columnsArray[i]));
            else
                columns.add(new ColumnString(columnsArray[i].toString()));
    }

    public void limit() {
        limit("?", "?");
    }

    public void limit(Object limited_offset, Object limited_count) {
        this.limited = true;
        this.limited_offset = limited_offset;
        this.limited_count = limited_count;
    }



    public void sort(String sortColumn, boolean sortAB) {
        this.sortColumn = sortColumn;
        this.sortAB = sortAB;
    }

    public void setGroupColumn(String groupColumn) {
        this.groupColumn = groupColumn;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    protected String createQuery() {
        String sql = Sql.SELECT;
        if (distinct)
            sql += DISTINCT;
        for (int i = 0; i < columns.size(); i++) {
            if (i != 0) {
                sql += ",";
            }
            sql += columns.get(i).toQuery();
        }
        sql += Sql.FROM + table;
        sql += getWhereString();
        if(groupColumn != null)
            sql += Sql.GROUP + groupColumn;
        if (sortColumn != null)
            sql += Sql.ORDER + sortColumn + (sortAB ? Sql.ASC : Sql.DESC);
        if (limited)
            sql += Sql.LIMIT + limited_offset + "," + limited_count;
        return sql;
    }

    public int getColumnsCount() {
        return columns.size();
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setTable(String table) {
        this.table = table;
    }

    //
    //  Columns
    //

    public abstract static class Column {

        protected abstract String toQuery();

    }

    public static class ColumnString extends Column {

        private final String columns;

        public ColumnString(String columns) {
            this.columns = columns;
        }

        @Override
        protected String toQuery() {
            return columns;
        }
    }

    public static class ColumnRequest extends Column {

        private final SqlQuerySelect query;

        public ColumnRequest(SqlQuerySelect query) {
            this.query = query;
        }

        @Override
        protected String toQuery() {
            return "(" + query.getQuery() + ")";
        }
    }
}

package com.sup.dev.java_pc.sql;

import java.util.ArrayList;

public class SqlQueryUpdate extends SqlQueryWithWhere {

    private String table;
    private ArrayList<UpdateColumn> columns = new ArrayList<>();

    public SqlQueryUpdate(String table, Object... columns) {
        this.table = table;
        for (int i = 0; i < columns.length; i++) {
            if (columns[i] instanceof UpdateColumn)
                addUpdate((UpdateColumn) columns[i]);
            else if (columns[i] instanceof String)
                addUpdate(new UpdateColumnSimple((String) columns[i]));
            else throw new RuntimeException("Bad field type [" + columns[i] + "]");
        }
    }

    public SqlQueryUpdate addUpdate(UpdateColumn updateColumn) {
        columns.add(updateColumn);
        return this;
    }

    public SqlQueryUpdate addUpdate(String column) {
        columns.add(new UpdateColumnSimple(column));
        return this;
    }

    public SqlQueryUpdate addUpdate(String column, Object values) {
        columns.add(new UpdateColumnSimple(column, values));
        return this;
    }

    public int getUpdateColumnsCount(){
        return columns.size();
    }

    @Override
    protected String createQuery() {
        StringBuilder sql = new StringBuilder(UPDATE + table + SET + columns.get(0).toQuery());
        for (int i = 1; i < columns.size(); i++)
            sql.append(",").append(columns.get(i).toQuery());
        sql.append(getWhereString());
        return sql.toString();
    }

    public static abstract class UpdateColumn {

        public abstract String toQuery();
    }


    public static class UpdateColumnSimple extends UpdateColumn {

        public final String column;
        public final Object values;

        public UpdateColumnSimple(String column, Object values) {
            this.column = column;
            this.values = values;
        }

        public UpdateColumnSimple(String column) {
            this(column, "?");
        }

        @Override
        public String toQuery() {
            return column + "=" + values;
        }
    }

    public static class UpdateColumnCount extends UpdateColumn {

        public final String column;
        public final String operation;
        public final Object value;

        public UpdateColumnCount(String column) {
            this(column, "?");
        }

        public UpdateColumnCount(String column, Object value) {
            this(column, "+", value);
        }

        public UpdateColumnCount(String column, String operation, Object value) {
            this.column = column;
            this.operation = operation;
            this.value = value;
        }

        @Override
        public String toQuery() {
            return column + "=" + column + operation + "(" + value + ")";
        }

    }

}

package com.sup.dev.java_pc.sql;


public class SqlQueryInsert extends SqlQuery {

    private String table;
    private String[] columns;
    private String[] values;

    public SqlQueryInsert(String table, String... columns){
        this.table =table;
        this.columns = columns;
        values = new String[columns.length];
        for(int i = 0; i < values.length; i++)
            values[i] = "?";
    }


    public void setValue(int index, SqlQuerySelect query){
        values[index] = "("+query.getQuery()+")";
    }

    public void setValue(int index, String value){
        values[index] = value;
    }

    @Override
    protected String createQuery() {
        String sql =
                INSERT + table + "(";
        String valuesString = VALUES+"(";
        for (int i = 0; i < columns.length; i++) {
            if (i != 0) {
                sql += ",";
                valuesString += ",";
            }
            sql += columns[i];
            valuesString += values[i];
        }
        sql += ") " + valuesString + ")";
        return sql;
    }

}

package com.sup.dev.java_pc.sql;

public class SqlQueryRemove extends SqlQueryWithWhere {

    private String table;

    public SqlQueryRemove(String table) {
        this.table = table;
    }

    @Override
    protected String createQuery() {
        return DELETE + table + getWhereString();
    }

}

package com.sup.dev.java_pc.sql;

public abstract class SqlQuery extends Sql {

    private String query;

    protected abstract String createQuery();

    //
    //  Getters
    //

    @Override
    public String toString() {
        return getQuery();
    }

    public String getQuery() {
        if(query == null)
            query = createQuery();
        return query;
    }

}

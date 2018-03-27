package com.sup.dev.java_pc.sql;

import java.util.ArrayList;

abstract class SqlQueryWithWhere extends SqlQuery {

    private SqlWhere currentWhere = new SqlWhere(null);
    private ArrayList<SqlWhere> wheres = new ArrayList<>();
    private int mainConditionsIndex;

    public SqlQueryWithWhere() {
        wheres.add(currentWhere);
    }

    public void nextWhere(String link) {
        nextWhere(link, false);
    }

    public void nextWhere(String link, boolean main) {
        if(currentWhere.getWheresCount() == 0)
            wheres.remove(currentWhere);
        currentWhere = new SqlWhere(link);
        wheres.add(currentWhere);
        if(main)
            mainConditionsIndex = wheres.size()-1;
    }

    public <K extends SqlQueryWithWhere> K where(Object columns, String condition, Object values) {
        return where(new SqlWhere.WhereColumn(columns, condition, values));
    }

    public <K extends SqlQueryWithWhere> K where(Object columns, String condition, Object values, String link) {
        return where(new SqlWhere.WhereColumn(columns, condition, values, link));
    }


    public <K extends SqlQueryWithWhere> K where(SqlWhere.Where... wheres) {
        currentWhere.addWhere(wheres);
        return (K)this;
    }

    protected String getWhereString() {
        StringBuilder whereString = new StringBuilder();
        if(mainConditionsIndex > 0)
            whereString.append("(");
        for (int i = 0; i < wheres.size(); i++) {
            if(mainConditionsIndex > 0 && i == mainConditionsIndex)
                whereString.append(")");
            whereString.append(wheres.get(i).toQuery(i != 0));
        }
        if (whereString.length() != 0)
            return WHERE + whereString;
        else
            return whereString.toString();
    }


}

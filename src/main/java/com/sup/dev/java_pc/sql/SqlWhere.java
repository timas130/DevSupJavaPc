package com.sup.dev.java_pc.sql;

import java.util.ArrayList;
import java.util.Collections;

public class SqlWhere extends Sql {

    private ArrayList<Where> wheres = new ArrayList<>();
    private String link;
    private boolean used = false;

    SqlWhere(String link) {
        this.link = link;
    }

    SqlWhere(String link, Where... wheres) {
        this.link = link;
        for (Where where : wheres)
            addWhere(where);
    }

    void addWhere(Where... wheres) {
        used = true;
        Collections.addAll(this.wheres, wheres);
    }

    String toQuery(boolean useLink) {
        if (!used) return "";
        StringBuilder s;
        if (useLink)
            s = new StringBuilder(link);
        else
            s = new StringBuilder();
        s.append("(").append(wheres.get(0).toQuery());
        for (int i = 1; i < wheres.size(); i++)
            s.append(" ").append(wheres.get(i - 1).link).append(" ").append(wheres.get(i).toQuery());
        return s + ")";
    }

    public int getWheresCount(){
        return wheres.size();
    }

    //
    //  Wheres
    //

    public abstract static class Where {

        private String link;

        private Where(String link) {
            this.link = link;
        }

        protected abstract String toQuery();

    }

    public static class WhereString extends Where {

        public String where;

        public WhereString(String where) {
            this(where, "AND");
        }

        public WhereString(String where, String link) {
            super(link);
            this.where = where;
        }

        @Override
        protected String toQuery() {
            return where;
        }
    }

    public static class WhereColumn extends Where {

        public Object column;
        public String condition;
        public Object value;

        public WhereColumn(Object column, String condition, Object value) {
            this(column, condition, value, "AND");
        }

        public WhereColumn(Object column, String condition) {
            this(column, condition, "?", "AND");
        }

        public WhereColumn(Object column) {
            this(column, "=", "?", "AND");
        }

        public WhereColumn(Object column, String condition, Object value, String link) {
            super(link);
            this.column = column;
            this.condition = condition;
            this.value = value;
        }

        @Override
        protected String toQuery() {
            return column + condition + value;
        }
    }

    public static class WhereLIKE extends Where {

        private final String column;

        public WhereLIKE(String column) {
            this(column, "AND");
        }

        public WhereLIKE(String column, String link) {
            super(link);
            this.column = column;
        }

        @Override
        protected String toQuery() {
            return column + " LIKE(?)";
        }
    }

    public static class WhereIN extends Where {

        private final String column;
        private final boolean not;
        private Object[] values;

        public WhereIN(String column, Object... values) {
            this(column, "AND", false, values);
        }

        public WhereIN(String column, boolean not, Object... values) {
            this(column, "AND", not, values);
        }

        public WhereIN(String column, String link, boolean not, Object... values) {
            super(link);
            this.column = column;
            this.not = not;
            this.values = values;
        }

        @Override
        protected String toQuery() {
            String s = "IN(";
            if (not)
                s = "not " + s;
            s = column + " " + s;
            for (int i = 0; i < values.length; i++) {
                if (i > 0) s += ",";
                s += values[i];
            }
            return s + ")";
        }
    }

    public static class WhereSelect extends Where {

        private final String column;
        private final String condition;
        private final SqlQuerySelect query;

        public WhereSelect(String column, SqlQuerySelect query) {
            this(column, "=", query, "AND");
        }

        public WhereSelect(String column, String condition, SqlQuerySelect query) {
            this(column, condition, query, "AND");
        }

        public WhereSelect(String column, String condition, SqlQuerySelect query, String link) {
            super(link);
            this.column = column;
            this.condition = condition;
            this.query = query;
        }

        @Override
        protected String toQuery() {
            return column + condition + "(" + query.getQuery() + ")";
        }
    }
}

package com.sup.dev.java_pc.sql;

public class Sql {

    public static final String INSERT = "INSERT INTO ";
    public static final String DELETE = "DELETE FROM ";
    public static final String UPDATE = "UPDATE ";
    public static final String SET = " SET ";
    public static final String VALUES = " VALUES ";
    public static final String WHERE = " WHERE ";
    public static final String DISTINCT = "DISTINCT ";
    public static final String LIKE = " LIKE(?) ";
    public static final String SELECT = "SELECT ";
    public static final String COUNT = " COUNT(*) ";
    public static final String FROM = " FROM ";
    public static final String ORDER = " ORDER BY ";
    public static final String GROUP = " GROUP BY ";
    public static final String LIMIT = " LIMIT ";
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";


    public static final String SELECT_LAST_ID = "SELECT LAST_INSERT_ID()";

    public static String prepareColumns(String... columns) {
        if (columns == null || columns.length == 0)
            return "";
        String s = columns[0];
        for (int i = 1; i < columns.length; i++)
            s += "," + columns[1];
        return s;
    }

    public static String SUM(String column) {
        return " SUM(" + column + ")";
    }

    public static String IF(Object param, Object value, Object ret1, Object ret2) {
        return IF(param, "=", value, ret1, ret2);
    }

    public static String IF(Object param, Object condition, Object value, Object ret1, Object ret2) {
        return " IF((" + param + ") " + condition + " (" + value + "),(" + ret1 + "),(" + ret2 + "))";
    }

    public static String IFNULL(Object param, Object ret) {
        return " IFNULL((" + param + "),(" + ret + "))";
    }


    public static String CONCAT(Object... params) {
        StringBuilder s = new StringBuilder(" CONCAT(" + params[0]);
        for (int i = 1; i < params.length; i++)
            s.append(",").append(params[i]);
        return s + ")";
    }

    public static String IN(Object... params) {
        StringBuilder s = new StringBuilder(" IN(" + params[0]);
        for (int i = 1; i < params.length; i++)
            s.append(",").append(params[i]);
        return s + ")";
    }

    public static String IN(long... params) {
        StringBuilder s = new StringBuilder(" IN(" + params[0]);
        for (int i = 1; i < params.length; i++)
            s.append(",").append(params[i]);
        return s + ")";
    }

    public static String increment(String column) {
        return column + "=" + column + "+1";
    }

    public static long parseSum(Object o) {
        if (o == null) return 0;
        else return Long.parseLong(o + "");
    }

}

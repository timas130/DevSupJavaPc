package com.sup.dev.java_pc.sql;

import com.sup.dev.java.classes.collections.AnyArray;
import com.sup.dev.java.libs.debug.Debug;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database extends Sql {

    public static boolean SALIENT = false;

    //
    //  Logic
    //

    private static Connection connection;
    private static PreparedQuery selectLastInsert;

    public static void init(String login, String pass, String base, String mysql_url) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://" + mysql_url + "/" + base, login, pass);
        selectLastInsert = new PreparedQuery(false, SELECT_LAST_ID);
        execute("SET GLOBAL connect_timeout=1000000");
        execute("SET GLOBAL wait_timeout=1000000");
        execute("SET GLOBAL interactive_timeout=1000000");
        execute("SET NAMES utf8mb4");
        execute("SET CHARACTER SET utf8mb4");
        execute("SET character_set_connection=utf8mb4");
    }

    //
    //  Insert
    //

    public static long insertSalient(SqlQueryInsert query, Object... values) {
        SALIENT = true;
        long v = insert(query, values);
        SALIENT = false;
        return v;
    }

    public static long insert(SqlQueryInsert query, Object... values) {
        execute(query.getQuery(), values);
        ResultRows result = select(selectLastInsert, 1);
        if (result.values.get(0) instanceof BigInteger)
            return ((BigInteger) result.values.get(0)).intValue();
        return (long) result.values.get(0);
    }

    //
    //  Select
    //


    public static ResultRows select(SqlQuerySelect query, Object... values) {
        try {
            PreparedQuery preparedQuery = new PreparedQuery(query.getQuery());
            preparedQuery.setParams(values);
            ResultRows select = select(preparedQuery, query.getColumnsCount());
            return select;
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query.getQuery());
                Debug.log(values == null ? "values = null" : values);
            }
            throw new RuntimeException(e);
        }
    }

    public static ResultRows select(int columnsCount, String query, Object... values) {

        try {
            PreparedQuery preparedQuery = new PreparedQuery(query);
            preparedQuery.setParams(values);
            ResultRows select = select(preparedQuery, columnsCount);
            return select;
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query);
                Debug.log(Debug.asString(values));
            }
            throw new RuntimeException(e);
        }
    }

    private static ResultRows select(PreparedQuery query, int columnsCount) {
        try {
            ResultSet rs = query.getStatement().executeQuery();
            AnyArray list = new AnyArray();
            while (rs.next()) {
                for (int i = 1; i < columnsCount + 1; i++) {
                    Object object = rs.getObject(i);
                    if (object instanceof BigDecimal)
                        list.add(((BigDecimal) object).intValue());
                    else
                        list.add(object);
                }
            }
            query.closeIfNeed();
            return new ResultRows(list.size() / columnsCount, list);
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query.getQuery());
                Debug.log(query.getValues());
            }
            throw new RuntimeException(e);
        }
    }

    //
    //  Update
    //

    public static int update(SqlQueryUpdate query, Object... values) {
        try {
            PreparedQuery preparedQuery = new PreparedQuery(query.getQuery());
            preparedQuery.setParams(values);
            int count = preparedQuery.getStatement().executeUpdate();
            preparedQuery.closeIfNeed();
            return count;
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query.getQuery());
                Debug.log(values);
            }
            throw new RuntimeException(e);
        }
    }

    //
    //  Delete
    //

    public static void remove(SqlQueryRemove query, Object... values) {
        try {
            PreparedQuery preparedQuery = new PreparedQuery(query.getQuery());
            preparedQuery.setParams(values);
            preparedQuery.getStatement().execute();
            preparedQuery.closeIfNeed();
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query.getQuery());
                Debug.log(values);
            }
            throw new RuntimeException(e);
        }
    }


    //
    //  Execute
    //

    public static void execute(String query, Object... values) {
        try {
            PreparedQuery preparedQuery = new PreparedQuery(query);
            preparedQuery.setParams(values);
            preparedQuery.getStatement().executeUpdate();
            preparedQuery.closeIfNeed();
        } catch (SQLException e) {
            if(!SALIENT) {
                Debug.log(query);
                Debug.log(values);
            }
            throw new RuntimeException(e);
        }
    }


    //
    //  Getters
    //

    public static Connection getConnection() {
        return connection;
    }
}

package com.sup.dev.java_pc.sql;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class PreparedQuery {

    private final String query;
    private final PreparedStatement statement;
    private final boolean closeable;
    private Object[] values;

    public PreparedQuery(String query) {
        this(true, query);
    }

    public PreparedQuery(boolean closeable, String query) {
        this.query = query;
        this.closeable = closeable;
        try {
            statement = Database.getConnection().prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setParams(Object... values) throws SQLException {
        this.values = values;
        int paramIndex = 1;
        for (Object o : values) {
            if (o instanceof String[])
                for (String x : (String[]) o)
                    setParam(paramIndex++, x);
            else if (o instanceof int[])
                for (int x : (int[]) o)
                    setParam(paramIndex++, x);
            else if (o instanceof long[])
                for (long x : (long[]) o)
                    setParam(paramIndex++, x);
            else if (o instanceof float[])
                for (float x : (float[]) o)
                    setParam(paramIndex++, x);
            else if (o instanceof double[])
                for (double x : (double[]) o)
                    setParam(paramIndex++, x);
            else if (o instanceof ArrayList)
                for (Object x : (ArrayList) o)
                    setParam(paramIndex++, x);
            else
                setParam(paramIndex++, o);
        }
    }

    public void setParam(int index, Object value) throws SQLException {
        if (value == null)
            statement.setNull(index, java.sql.Types.NULL);
        else if (value instanceof byte[])
            statement.setBytes(index, (byte[]) value);
        else if (value instanceof Integer)
            statement.setInt(index, (int) value);
        else if (value instanceof Long)
            statement.setLong(index, (long) value);
        else if (value instanceof Double)
            statement.setDouble(index, (double) value);
        else if (value instanceof Float)
            statement.setFloat(index, (float) value);
        else
            statement.setString(index, value.toString());
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getValues() {
        return values;
    }

    public void closeIfNeed() {
        try {
            if (closeable) statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

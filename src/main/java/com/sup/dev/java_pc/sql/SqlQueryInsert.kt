package com.sup.dev.java_pc.sql


class SqlQueryInsert(private val table: String) : SqlQuery() {

    private val columns = ArrayList<String>()
    private val values = ArrayList<String>()

    fun put(column: String, value: Any): SqlQueryInsert {
        columns.add(column)
        values.add(value.toString())
        return this
    }

    fun putValue(column: String, value: Any): SqlQueryInsert {
        return put(column, "?").value(value)
    }

    override fun value(v: Any): SqlQueryInsert {
        return super.value(v) as SqlQueryInsert
    }

    override fun createQuery(): String {
        var sql = Sql.INSERT + table + "("
        var valuesString = Sql.VALUES + "("
        for (i in columns.indices) {
            if (i != 0) {
                sql += ","
                valuesString += ","
            }
            sql += columns[i]
            valuesString += values[i]
        }
        sql += ") $valuesString)"
        return sql
    }

}

package com.sup.dev.java_pc.sql


class SqlQueryInsert(private val table: String, vararg val columns: String?) : SqlQuery() {
    private val values: Array<String?> = arrayOfNulls(columns.size)

    init {
        for (i in values.indices)
            values[i] = "?"
    }


    fun setValue(index: Int, query: SqlQuerySelect) {
        values[index] = "(" + query.getQuery() + ")"
    }

    fun setValue(index: Int, value: String) {
        values[index] = value
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

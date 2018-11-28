package com.sup.dev.java_pc.sql


class SqlQueryRemove(private val table: String) : SqlQueryWithWhere() {

    override fun createQuery(): String {
        return Sql.DELETE + table + createWhere()
    }

}

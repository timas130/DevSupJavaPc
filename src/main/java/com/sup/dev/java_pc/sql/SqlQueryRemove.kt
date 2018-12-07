package com.sup.dev.java_pc.sql


class SqlQueryRemove(private val table: String) : SqlQueryWithWhere() {

    override fun createQuery(): String {
        return Sql.DELETE + table + createWhere()
    }


    override fun where(vararg wheres: SqlWhere.Where): SqlQueryRemove {
        return super.where(*wheres) as SqlQueryRemove
    }

    override fun where(columns: Any, condition: String, values: Any, link: String): SqlQueryRemove {
        return super.where(columns, condition, values, link) as SqlQueryRemove
    }
}

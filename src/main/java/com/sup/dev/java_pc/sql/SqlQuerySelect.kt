package com.sup.dev.java_pc.sql

import java.util.ArrayList


class SqlQuerySelect : SqlQueryWithWhere {


    val columns = ArrayList<Column>()

    private var table: String? = null
    private var limited: Boolean = false
    private var limited_offset: Any? = null
    private var limited_count: Any? = null
    private var groupColumn: String? = null
    private var sortColumn: String? = null
    private var sortAB: Boolean = false
    private var distinct: Boolean = false

    val columnsCount: Int
        get() = columns.size

    constructor(table: String) {
        this.table = table
    }

    constructor(table: String, columnsArray: ArrayList<*>) {
        this.table = table
        for (i in columnsArray.indices)
            if (columnsArray[i] is SqlQuerySelect)
                columns.add(ColumnRequest(columnsArray[i] as SqlQuerySelect))
            else
                columns.add(ColumnString(columnsArray[i].toString()))
    }

    constructor(table: String, vararg columnsArray: Any) {
        this.table = table
        for (i in columnsArray.indices)
            if (columnsArray[i] is SqlQuerySelect)
                columns.add(ColumnRequest(columnsArray[i] as SqlQuerySelect))
            else
                columns.add(ColumnString(columnsArray[i].toString()))
    }


 //   override fun <K : SqlQueryWithWhere> where(columns: Any, condition: String, values: Any, link: String = "AND"): K {
 //       return where(SqlWhere.WhereColumn(columns, condition, values, link))
 //   }

    fun count(count: Int): SqlQuerySelect {
        return offset_count(0, count)
    }

    @JvmOverloads
    fun offset_count(limited_offset: Any = "?", limited_count: Any = "?"): SqlQuerySelect {
        this.limited = true
        this.limited_offset = limited_offset
        this.limited_count = limited_count
        return this
    }

    fun sort(sortColumn: String, sortAB: Boolean): SqlQuerySelect {
        this.sortColumn = sortColumn
        this.sortAB = sortAB
        return this
    }

    fun setGroupColumn(groupColumn: String): SqlQuerySelect {
        this.groupColumn = groupColumn
        return this
    }

    fun setDistinct(distinct: Boolean): SqlQuerySelect {
        this.distinct = distinct
        return this
    }

    override fun createQuery(): String {
        var sql = Sql.SELECT
        if (distinct)
            sql += Sql.DISTINCT
        for (i in columns.indices) {
            if (i != 0) {
                sql += ","
            }
            sql += columns[i].toQuery()
        }
        sql += Sql.FROM + table!!
        sql += whereString
        if (groupColumn != null)
            sql += Sql.GROUP + groupColumn!!
        if (sortColumn != null)
            sql += Sql.ORDER + sortColumn + if (sortAB) Sql.ASC else Sql.DESC
        if (limited)
            sql += Sql.LIMIT + limited_offset + "," + limited_count
        return sql
    }

    fun setTable(table: String) {
        this.table = table
    }

    //
    //  Columns
    //

    abstract class Column {

        abstract fun toQuery(): String

    }

    class ColumnString(private val columns: String) : Column() {

        override fun toQuery(): String {
            return columns
        }
    }

    class ColumnRequest(private val query: SqlQuerySelect) : Column() {

        override fun toQuery(): String {
            return "(" + query.getQuery() + ")"
        }
    }
}

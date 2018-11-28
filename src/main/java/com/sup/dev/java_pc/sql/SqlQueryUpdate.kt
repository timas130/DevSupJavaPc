package com.sup.dev.java_pc.sql

import java.util.ArrayList


class SqlQueryUpdate(private val table: String, vararg columns: Any) : SqlQueryWithWhere() {
    private val columns = ArrayList<UpdateColumn>()

    val updateColumnsCount: Int
        get() = columns.size

    init {
        for (i in columns.indices) {
            if (columns[i] is UpdateColumn)
                addUpdate(columns[i] as UpdateColumn)
            else if (columns[i] is String)
                addUpdate(UpdateColumnSimple(columns[i] as String))
            else
                throw RuntimeException("Bad field type [" + columns[i] + "]")
        }
    }

    fun addUpdate(updateColumn: UpdateColumn): SqlQueryUpdate {
        columns.add(updateColumn)
        return this
    }

    fun addUpdate(column: String): SqlQueryUpdate {
        columns.add(UpdateColumnSimple(column))
        return this
    }

    fun addUpdate(column: String, values: Any): SqlQueryUpdate {
        columns.add(UpdateColumnSimple(column, values))
        return this
    }

    override fun createQuery(): String {
        val sql = StringBuilder(Sql.UPDATE + table + Sql.SET + columns[0].toQuery())
        for (i in 1 until columns.size)
            sql.append(",").append(columns[i].toQuery())
        sql.append(createWhere())
        return sql.toString()
    }

    abstract class UpdateColumn {

        abstract fun toQuery(): String
    }


    class UpdateColumnSimple @JvmOverloads constructor(val column: String, val value: Any = "?") : UpdateColumn() {

        override fun toQuery(): String {
            return "$column=$value"
        }
    }

    class UpdateColumnCount(val column: String, val operation: String, val value: Any) : UpdateColumn() {

        @JvmOverloads constructor(column: String, value: Any = "?") : this(column, "+", value) {}

        override fun toQuery(): String {
            return "$column=$column$operation($value)"
        }

    }

}

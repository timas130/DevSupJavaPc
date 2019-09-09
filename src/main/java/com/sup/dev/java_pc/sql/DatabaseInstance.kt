package com.sup.dev.java_pc.sql

import com.sup.dev.java.classes.collections.AnyArray
import com.sup.dev.java.libs.debug.info
import java.math.BigDecimal
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class DatabaseInstance(
        login: String,
        pass: String,
        base: String,
        mysql_url: String
) {

    var SALIENT = false
    var connection: Connection? = null

    init {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        connection = DriverManager.getConnection("jdbc:mysql://$mysql_url/$base", login, pass)
        execute("SET GLOBAL connect_timeout=1000000")
        execute("SET GLOBAL wait_timeout=1000000")
        execute("SET GLOBAL interactive_timeout=1000000")
        execute("SET NAMES utf8mb4")
        execute("SET CHARACTER SET utf8mb4")
        execute("SET character_set_connection=utf8mb4")
    }

    //
    //  Insert
    //

    fun insert(query: SqlQueryInsert, vararg values: Any?): Long {
        try {
            val preparedQuery = PreparedQuery(query.getQuery()!!, this, true)
            preparedQuery.setParams(*values)
            preparedQuery.statement.executeUpdate()
            val generatedKeys = preparedQuery.statement.generatedKeys
            generatedKeys.next()
            val id = if (generatedKeys.isFirst && generatedKeys.isLast) generatedKeys.getLong(1) else 0
            preparedQuery.closeIfNeed()
            return id
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(*values)
            }
            throw RuntimeException(e)
        }
    }

    fun insert(tableName: String, vararg o: Any?): Long {
        val columns = ArrayList<String>()
        val values = ArrayList<Any?>()

        for (i in o.indices) {
            if (i % 2 == 1) values.add(o[i])
            else columns.add(o[i] as String)
        }

        val insert = SqlQueryInsert(tableName)
        for (i in columns) insert.put(i, "?")

        return insert(insert, *values.toTypedArray())
    }

    //
    //  Select
    //


    fun select(query: SqlQuerySelect, vararg values: Any?): ResultRows {
        try {
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            return select(preparedQuery, query.getColumnsCount())
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query.getQuery())
                info(values)
            }
            throw RuntimeException(e)
        }

    }

    fun select(columnsCount: Int, query: String, vararg values: Any?): ResultRows {

        try {
            val preparedQuery = PreparedQuery(query, this)
            preparedQuery.setParams(*values)
            return select(preparedQuery, columnsCount)
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(values)
            }
            throw RuntimeException(e)
        }

    }

    private fun select(query: PreparedQuery, columnsCount: Int): ResultRows {
        try {
            val rs = query.statement.executeQuery()
            val list = AnyArray()
            while (rs.next()) {
                for (i in 1 until columnsCount + 1) {
                    val ob = rs.getObject(i)
                    if (ob is BigDecimal)
                        list.add(ob.toInt())
                    else
                        list.add(ob)
                }
            }
            query.closeIfNeed()
            return ResultRows(list.size() / columnsCount, list)
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query.query)
                if (query.values == null) info("null")
                else info(query.values)
            }
            throw RuntimeException(e)
        }

    }

    //
    //  Update
    //

    fun update(query: SqlQueryUpdate, vararg values: Any): Int {
        try {
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            val count = preparedQuery.statement.executeUpdate()
            preparedQuery.closeIfNeed()
            return count
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query.getQuery())
                info(*values)
            }
            throw RuntimeException(e)
        }

    }

    //
    //  Delete
    //

    fun remove(query: SqlQueryRemove, vararg values: Any) {
        try {
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            preparedQuery.statement.execute()
            preparedQuery.closeIfNeed()
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query.getQuery())
                info(*values)
            }
            throw RuntimeException(e)
        }

    }


    //
    //  Execute
    //

    fun execute(query: String?, vararg values: Any?) {
        try {
            val preparedQuery = PreparedQuery(query!!, this)
            preparedQuery.setParams(*values)
            preparedQuery.statement.executeUpdate()
            preparedQuery.closeIfNeed()
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(*values)
            }
            throw RuntimeException(e)
        }

    }
}

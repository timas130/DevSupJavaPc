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
        mysql_url: String,
        val statisticCollector: (String, Long) -> Unit
) {

    var SALIENT = false
    var connection: Connection? = null

    init {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        connection = DriverManager.getConnection("jdbc:mysql://$mysql_url/$base", login, pass)
        execute("DatabaseInstance Init", "SET GLOBAL connect_timeout=1000000")
        execute("DatabaseInstance Init", "SET GLOBAL wait_timeout=1000000")
        execute("DatabaseInstance Init", "SET GLOBAL interactive_timeout=1000000")
        execute("DatabaseInstance Init", "SET NAMES utf8mb4")
        execute("DatabaseInstance Init", "SET CHARACTER SET utf8mb4")
        execute("DatabaseInstance Init", "SET character_set_connection=utf8mb4")
    }

    //
    //  Insert
    //

    fun insert(tag: String, query: SqlQueryInsert, vararg values: Any?): Long {
        try {
            val t = System.currentTimeMillis()
            val preparedQuery = PreparedQuery(query.getQuery()!!, this, true)
            preparedQuery.setParams(*values)
            preparedQuery.statement.executeUpdate()
            val generatedKeys = preparedQuery.statement.generatedKeys
            generatedKeys.next()
            val id = if (generatedKeys.isFirst && generatedKeys.isLast) generatedKeys.getLong(1) else 0
            preparedQuery.closeIfNeed()
            statisticCollector.invoke(tag, System.currentTimeMillis() - t)
            return id
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(*values)
            }
            throw RuntimeException(e)
        }
    }

    fun insert(tag: String, tableName: String, vararg o: Any?): Long {
        val columns = ArrayList<String>()
        val values = ArrayList<Any?>()

        for (i in o.indices) {
            if (i % 2 == 1) values.add(o[i])
            else columns.add(o[i] as String)
        }

        val insert = SqlQueryInsert(tableName)
        for (i in columns) insert.put(i, "?")

        return insert(tag, insert, *values.toTypedArray())
    }

    //
    //  Select
    //


    fun select(tag: String, query: SqlQuerySelect, vararg values: Any?): ResultRows {
        try {
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            return select(tag, preparedQuery, query.getColumnsCount())
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query.getQuery())
                info(values)
            }
            throw RuntimeException(e)
        }

    }

    fun select(tag: String, columnsCount: Int, query: String, vararg values: Any?): ResultRows {

        try {
            val preparedQuery = PreparedQuery(query, this)
            preparedQuery.setParams(*values)
            return select(tag, preparedQuery, columnsCount)
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(values)
            }
            throw RuntimeException(e)
        }

    }

    private fun select(tag: String, query: PreparedQuery, columnsCount: Int): ResultRows {
        try {
            val t = System.currentTimeMillis()
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
            statisticCollector.invoke(tag, System.currentTimeMillis() - t)
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

    fun update(tag: String, query: SqlQueryUpdate, vararg values: Any): Int {
        try {
            val t = System.currentTimeMillis()
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            val count = preparedQuery.statement.executeUpdate()
            preparedQuery.closeIfNeed()
            statisticCollector.invoke(tag, System.currentTimeMillis() - t)
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

    fun remove(tag: String, query: SqlQueryRemove, vararg values: Any) {
        try {
            val t = System.currentTimeMillis()
            val preparedQuery = PreparedQuery(query.getQuery(), this)
            preparedQuery.setParams(*values)
            preparedQuery.statement.execute()
            preparedQuery.closeIfNeed()
            statisticCollector.invoke(tag, System.currentTimeMillis() - t)
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

    fun execute(tag: String, query: String?, vararg values: Any?) {
        try {
            val t = System.currentTimeMillis()
            val preparedQuery = PreparedQuery(query!!, this)
            preparedQuery.setParams(*values)
            preparedQuery.statement.executeUpdate()
            preparedQuery.closeIfNeed()
            statisticCollector.invoke(tag, System.currentTimeMillis() - t)
        } catch (e: SQLException) {
            if (!SALIENT) {
                info(query)
                info(*values)
            }
            throw RuntimeException(e)
        }

    }
}

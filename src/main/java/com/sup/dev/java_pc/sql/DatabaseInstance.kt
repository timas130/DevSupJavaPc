package com.sup.dev.java_pc.sql

import com.sup.dev.java.classes.collections.AnyArray
import com.sup.dev.java.libs.debug.Debug
import com.sup.dev.java.libs.debug.info
import java.math.BigDecimal
import java.math.BigInteger
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException


class DatabaseInstance {

    var SALIENT = false

    var connection: Connection? = null
    private var selectLastInsert: PreparedQuery? = null

    @Throws(ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class, SQLException::class)
    constructor(login: String, pass: String, base: String, mysql_url: String) {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        connection = DriverManager.getConnection("jdbc:mysql://$mysql_url/$base", login, pass)
        selectLastInsert = PreparedQuery(false, Sql.SELECT_LAST_ID, this)
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
        execute(query.getQuery(), *values)
        val result = select(selectLastInsert!!, 1)
        return if (result.values.get<Any>(0) is BigInteger) (result.values.get<Any>(0) as BigInteger).toInt().toLong() else result.values.get<Any>(0) as Long
    }

    fun insert(tableName: String, vararg o: Any?): Long {
        val columns = ArrayList<String>()
        val values = ArrayList<Any?>()

        for (i in o.indices) {
            if (i % 2 == 1) values.add(o[i])
            else columns.add(o[i] as String)
        }


        val insert = SqlQueryInsert(tableName)
        for(i in columns) insert.put(i, "?")

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
            var rs = query.statement.executeQuery()             //  Try 1
            if(rs == null)rs = query.statement.executeQuery()   //  Try 2
            if(rs == null)rs = query.statement.executeQuery()   //  Try 3
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

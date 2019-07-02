package com.sup.dev.java_pc.sql

import java.sql.SQLException

object Database {

    var instance:DatabaseInstance? = null

    @Throws(ClassNotFoundException::class, InstantiationException::class, IllegalAccessException::class, SQLException::class)
    fun init(login: String, pass: String, base: String, mysql_url: String) {
        instance = DatabaseInstance(login, pass, base, mysql_url)
    }

    //
    //  Insert
    //

    fun insert(query: SqlQueryInsert): Long {
        return instance!!.insert(query, query.requestValues.toTypedArray())
    }

    fun insert(tableName: String, vararg o: Any?): Long {
        return instance!!.insert(tableName, *o)
    }

    //
    //  Select
    //

    fun select(query: SqlQuerySelect): ResultRows {
        return instance!!.select(query, query.requestValues.toTypedArray())

    }

    fun select(columnsCount: Int, query: String, vararg values: Any?): ResultRows {
        return instance!!.select(columnsCount, query, *values)
    }

    //
    //  Update
    //

    fun update(query: SqlQueryUpdate): Int {
        return instance!!.update(query, query.requestValues.toTypedArray())
    }

    //
    //  Delete
    //

    fun remove(query: SqlQueryRemove) {
        return instance!!.remove(query, query.requestValues.toTypedArray())
    }


    //
    //  Execute
    //

    fun execute(query: String?, vararg values: Any?) {
        return instance!!.execute(query, *values)
    }
}

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

    fun insert(query: SqlQueryInsert, vararg values: Any?) {
        instance!!.insert(query, values)
    }


    fun insertWithId(query: SqlQueryInsert, vararg values: Any?): Long {
        return instance!!.insertWithId(query, values)
    }

    fun insert(tableName: String, vararg o: Any?) {
        instance!!.insert(tableName, *o)
    }

    fun insertWithId(tableName: String, vararg o: Any?): Long {
        return instance!!.insertWithId(tableName, *o)
    }



    //
    //  Select
    //

    fun select(query: SqlQuerySelect, vararg values: Any?): ResultRows {
        return instance!!.select(query, values)

    }

    fun select(columnsCount: Int, query: String, vararg values: Any?): ResultRows {
        return instance!!.select(columnsCount, query, *values)
    }

    //
    //  Update
    //

    fun update(query: SqlQueryUpdate, vararg values: Any): Int {
        return instance!!.update(query, *values)
    }

    //
    //  Delete
    //

    fun remove(query: SqlQueryRemove, vararg values: Any) {
        return instance!!.remove(query, *values)
    }


    //
    //  Execute
    //

    fun execute(query: String?, vararg values: Any?) {
        return instance!!.execute(query, *values)
    }
}

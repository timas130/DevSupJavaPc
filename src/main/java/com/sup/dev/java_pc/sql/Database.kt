package com.sup.dev.java_pc.sql

object Database {

    var instance: DatabaseInstance? = null

    fun init(login: String, pass: String, base: String, mysql_url: String, statisticCollector: (String, Long) -> Unit) {

        instance = DatabaseInstance(login, pass, base, mysql_url, statisticCollector)
    }

    //
    //  Insert
    //

    fun insert(tag:String, query: SqlQueryInsert): Long {
        return instance!!.insert(tag, query, query.requestValues.toTypedArray())
    }

    fun insert(tag:String, tableName: String, vararg o: Any?): Long {
        return instance!!.insert(tag, tableName, *o)
    }

    //
    //  Select
    //

    fun select(tag:String, query: SqlQuerySelect): ResultRows {
        return instance!!.select(tag, query, query.requestValues.toTypedArray())

    }

    fun select(tag:String, columnsCount: Int, query: String, vararg values: Any?): ResultRows {
        return instance!!.select(tag, columnsCount, query, *values)
    }

    //
    //  Update
    //

    fun update(tag:String, query: SqlQueryUpdate): Int {
        return instance!!.update(tag, query, query.requestValues.toTypedArray())
    }

    //
    //  Delete
    //

    fun remove(tag:String, query: SqlQueryRemove) {
        return instance!!.remove(tag, query, query.requestValues.toTypedArray())
    }


    //
    //  Execute
    //

    fun execute(tag:String, query: String?, vararg values: Any?) {
        return instance!!.execute(tag, query, *values)
    }
}

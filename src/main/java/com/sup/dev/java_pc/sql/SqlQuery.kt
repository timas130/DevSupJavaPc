package com.sup.dev.java_pc.sql


abstract class SqlQuery{

    private var query: String? = null

    protected abstract fun createQuery(): String

    //
    //  Getters
    //

    override fun toString(): String {
        return getQuery()!!
    }

    fun getQuery(): String? {
        if (query == null)
            query = createQuery()
        return query
    }

}

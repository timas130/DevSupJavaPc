package com.sup.dev.java_pc.sql

import com.sup.dev.java.classes.collections.AnyArray


class ResultRows(val rowsCount: Int, var values: AnyArray) {
    val fieldsCount: Int

    val isEmpty: Boolean
        get() = values.isEmpty()

    init {
        this.fieldsCount = if (rowsCount == 0) 0 else values.size() / rowsCount
    }

    override fun toString(): String {
        val s = StringBuilder("ResultRows:")
        for (o in values.list)
            s.append(" ").append(o)
        return s.toString()
    }

    operator fun <K> next(): K? {
        return values.next<K>()
    }

    operator fun hasNext(): Boolean {
        return values.hasNext()
    }

    fun longOrZero(): Long {
        return if (hasNext()) next()!!
        else 0L
    }

    fun sumOrZero(): Long {
        return if (hasNext()) Sql.parseSum(next<Any>())
        else 0L
    }

}

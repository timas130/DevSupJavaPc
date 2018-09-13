package com.sup.dev.java_pc.sql

import java.util.*


class SqlWhere {

    private val wheres = ArrayList<Where>()
    private var link: String? = null
    private var used = false

    val wheresCount: Int
        get() = wheres.size

    internal constructor(link: String?) {
        this.link = link
    }

    internal constructor(link: String, vararg wheres: Where) {
        this.link = link
        for (where in wheres)
            addWhere(where)
    }

    internal fun addWhere(vararg wheres: Where) {
        used = true
        Collections.addAll(this.wheres, *wheres)
    }

    internal fun toQuery(useLink: Boolean): String {
        if (!used) return ""
        val s: StringBuilder
        if (useLink)
            s = StringBuilder(link!!)
        else
            s = StringBuilder()
        s.append("(").append(wheres[0].toQuery())
        for (i in 1 until wheres.size)
            s.append(" ").append(wheres[i - 1].link).append(" ").append(wheres[i].toQuery())
        return s.toString() + ")"
    }

    //
    //  Wheres
    //

    abstract class Where (val link: String) {

        abstract fun toQuery(): String

    }

    class WhereString constructor(var where: String, link: String = "AND") : Where(link) {

        override fun toQuery(): String {
            return where
        }
    }

    class WhereColumn constructor(var column: Any, var condition: String = "=", var value: Any = "?", link: String = "AND") : Where(link) {

        override fun toQuery(): String {
            return column.toString() + condition + value
        }
    }

    class WhereLIKE constructor(private val column: String, link: String = "AND") : Where(link) {

        override fun toQuery(): String {
            return "$column LIKE(?)"
        }
    }

    class WhereIN(private val column: String, link: String, private val not: Boolean, vararg values: Any?) : Where(link) {
        private val values: Array<out Any?> = values

        constructor(column: String, vararg values: Any?) : this(column, "AND", false, *values) {}

        constructor(column: String, not: Boolean, vararg values: Any?) : this(column, "AND", not, *values) {}

        override fun toQuery(): String {
            var s = "IN("
            if (not)
                s = "not $s"
            s = "$column $s"
            for (i in values.indices) {
                if (i > 0) s += ","
                s += values[i]
            }
            return "$s)"
        }
    }

    class WhereSelect @JvmOverloads constructor(private val column: String, private val condition: String, private val query: SqlQuerySelect, link: String = "AND") : Where(link) {

        constructor(column: String, query: SqlQuerySelect) : this(column, "=", query, "AND") {}

        override fun toQuery(): String {
            return column + condition + "(" + query.getQuery() + ")"
        }
    }
}

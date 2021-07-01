package com.sup.dev.java_pc.sql


object Sql {

    val INSERT = "INSERT INTO "
    val DELETE = "DELETE FROM "
    val UPDATE = "UPDATE "
    val SET = " SET "
    val VALUES = " VALUES "
    val WHERE = " WHERE "
    val DISTINCT = "DISTINCT "
    val LIKE = " LIKE(?) "
    val SELECT = "SELECT "
    val COUNT = " COUNT(*) "
    val FROM = " FROM "
    val ORDER = " ORDER BY "
    val GROUP = " GROUP BY "
    val LIMIT = " LIMIT "
    val ASC = " ASC "
    val DESC = " DESC "

    fun prepareColumns(vararg columns: String): String {
        if (columns.isEmpty())
            return ""
        var s = columns[0]
        for (i in 1 until columns.size)
            s += "," + columns[1]
        return s
    }

    fun SUM(column: String): String {
        return " SUM($column)"
    }

    fun MIN(column: String): String {
        return " MIN($column)"
    }

    fun MAX(column: String): String {
        return " MAX($column)"
    }

    fun AVG(column: String): String {
        return " AVG($column)"
    }

    fun COUNT(column: String): String {
        return " COUNT($column)"
    }

    fun IF(param: Any, value: Any, ret1: Any, ret2: Any): String {
        return IF(param, "=", value, ret1, ret2)
    }

    fun IF(param: Any, condition: Any, value: Any, ret1: Any, ret2: Any): String {
        return " IF(($param) $condition ($value),($ret1),($ret2))"
    }

    fun IFNULL(param: Any, ret: Any): String {
        return " IFNULL(($param),($ret))"
    }


    fun CONCAT(vararg params: Any): String {
        val s = StringBuilder(" CONCAT(" + params[0])
        for (i in 1 until params.size)
            s.append(",").append(params[i])
        return s.toString() + ")"
    }

    fun IN(vararg params: Any): String {
        val s = StringBuilder(" IN(" + params[0])
        for (i in 1 until params.size)
            s.append(",").append(params[i])
        return s.toString() + ")"
    }

    fun IN(vararg params: Long): String {
        val s = StringBuilder(" IN(" + params[0])
        for (i in 1 until params.size)
            s.append(",").append(params[i])
        return s.toString() + ")"
    }

    fun increment(column: String): String {
        return "$column=$column+1"
    }

    fun parseSum(o: Any?): Long {
        return if (o == null)
            0
        else
            java.lang.Long.parseLong(o.toString() + "")
    }

    fun mirror(v:String) = v.replace("_", "\\_").replace("%", "\\%")
}

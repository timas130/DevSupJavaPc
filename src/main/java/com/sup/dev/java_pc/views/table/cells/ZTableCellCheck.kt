package com.sup.dev.java_pc.views.table.cells

import com.sup.dev.java.libs.json.Json
import com.sup.dev.java_pc.views.table.ZTableCell
import com.sup.dev.java_pc.views.widgets.ZCheckBox
import java.awt.Component


class ZTableCellCheck(type: Int, size: Int, label: String) : ZTableCell(type, size, label) {

    private val checkBox: ZCheckBox

    //
    //  Getters
    //

    override val isEmpty: Boolean
        get() = checkBox.isChecked == originalValue as Boolean?

    override val cellValue: Any
        get() = checkBox.isChecked

    override val view: Component
        get() = checkBox

    override val isError: Boolean
        get() = false

    constructor(size: Int, label: String) : this(0, size, label) {}

    init {

        checkBox = ZCheckBox(size, label)

        setValue(false, true)
    }

    override fun toJson(key: String, json: Json) {
        json.put(key, checkBox.isChecked)
    }

    override fun fromJson(key: String, json: Json) {
        checkBox.isChecked = json.getBoolean(key)
    }

    override fun setShowChanges() {

    }

    //
    //  Setters
    //


    override fun setEnabled(b: Boolean) {
        checkBox.isEnabled = b
    }

    override fun setOnRightClick(onRightClick: ()->Unit) {

    }

    override fun setValue(value: Any) {
        checkBox.isChecked = value as Boolean
    }

    override fun showIfError() {

    }
}

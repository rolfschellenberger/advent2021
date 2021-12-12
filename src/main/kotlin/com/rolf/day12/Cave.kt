package com.rolf.day12

import java.util.*

data class Cave(val value: String) {
    val others = mutableSetOf<Cave>()

    fun addOther(cave: Cave) {
        others.add(cave)
    }

    fun isSmall(): Boolean {
        return value.lowercase(Locale.getDefault()) == value
    }

    fun isStart(): Boolean {
        return value == "start"
    }

    fun isEnd(): Boolean {
        return value == "end"
    }
}

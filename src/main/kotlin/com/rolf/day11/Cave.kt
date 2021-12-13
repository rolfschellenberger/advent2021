package com.rolf.day11

import com.rolf.Matrix
import com.rolf.Point


data class Cave(val input: MutableList<MutableList<Int>>) : Matrix<Int>(input) {

    fun increase(point: Point) {
        increase(point.x, point.y)
    }

    fun increase(x: Int, y: Int) {
        set(x, y, get(x, y) + 1)
    }

    override fun toString(): String {
        return toString("", "\n")
    }
}

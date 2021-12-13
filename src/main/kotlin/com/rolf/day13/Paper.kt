package com.rolf.day13

import com.rolf.Matrix
import com.rolf.Point

class Paper(input: MutableList<MutableList<String>>) : Matrix<String>(input) {

    fun count(): Int {
        return count("#")
    }

    fun foldX(position: Int) {
        for (y in 0 until height()) {
            val xDiff = width() - position - 1
            for (x in position - xDiff until position) {
                val value = get(x, y)
                val oppositeX = getOppositePointOverX(x, y, position)
                val newValue = getNewValue(value, get(oppositeX))
                set(x, y, newValue)
            }
        }
        removeX(position)
    }

    fun foldY(position: Int) {
        val yDiff = height() - position - 1
        for (y in position - yDiff until position) {
            for (x in 0 until width()) {
                val value = get(x, y)
                val oppositeY = getOppositePointOverY(x, y, position)
                val newValue = getNewValue(value, get(oppositeY))
                set(x, y, newValue)
            }
        }
        removeY(position)
    }

    private fun removeX(position: Int) {
        cutout(Point(0, 0), Point(position - 1, height() - 1))
    }

    private fun removeY(position: Int) {
        cutout(Point(0, 0), Point(width() - 1, position - 1))
    }

    private fun getNewValue(value: String, oppositeValue: String): String {
        if (value == "#" || oppositeValue == "#") {
            return "#"
        }
        return "."
    }

    override fun toString(): String {
        return super.toString("", "\n")
    }

    companion object {
        fun buildDefault(width: Int, height: Int, defaultValue: String): Paper {
            return Paper(Matrix.buildDefault(width, height, defaultValue).input)
        }
    }
}

package com.rolf.day20

import com.rolf.BinaryNumber
import com.rolf.Matrix

class Image(private val algorithm: String, input: MutableList<MutableList<String>>) : Matrix<String>(input) {

    fun getSurrounding(x: Int, y: Int, step: Int): List<String> {
        val result = mutableListOf<String>()
        for (yy in y - 1..y + 1) {
            for (xx in x - 1..x + 1) {
                val value = getSafe(xx, yy, step)
                result.add(value)
            }
        }
        return result
    }

    private fun getSafe(x: Int, y: Int, step: Int): String {
        if (isOutside(x, y)) {
            // When the empty cell is transformed into a non-empty, we need to alter the
            // possible value based on the step
            if (algorithm[0].toString() == "#") {
                // When the step is even, use the last value, otherwise the first
                val index = if (step % 2 == 0) 511 else 0
                return algorithm[index].toString()
            }
            // Empty cell
            return "."
        }
        return get(x, y)
    }

    fun calculateNewValue(surrounding: List<String>): String {
        val string = surrounding.map { if (it == "#") 1 else 0 }.joinToString("")
        val value = BinaryNumber(string).toInt()
        return algorithm[value].toString()
    }

    fun copy(): Image {
        val rows = mutableListOf<MutableList<String>>()
        for (y in 0 until height()) {
            val row = mutableListOf<String>()
            for (x in 0 until width()) {
                row.add(get(x, y))
            }
            rows.add(row)
        }
        return Image(algorithm, rows)
    }

    fun increase(size: Int): Image {
        val rows = mutableListOf<MutableList<String>>()
        for (i in 0 until size) {
            rows.add(emptyRow(size))
        }
        for (y in 0 until height()) {
            val row = mutableListOf<String>()
            for (i in 0 until size) {
                row.add(".")
            }
            for (x in 0 until width()) {
                row.add(get(x, y))
            }
            for (i in 0 until size) {
                row.add(".")
            }
            rows.add(row)
        }
        for (i in 0 until size) {
            rows.add(emptyRow(size))
        }
        return Image(algorithm, rows)
    }

    private fun emptyRow(size: Int): MutableList<String> {
        val row = mutableListOf<String>()
        for (x in 0 until width() + 2 * size) {
            row.add(".")
        }
        return row
    }

    override fun toString(): String {
        return toString("", "\n")
    }
}

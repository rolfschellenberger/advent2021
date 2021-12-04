package com.rolf.day04

import com.rolf.Matrix
import com.rolf.readLineToLong

class BingoBoard(input: List<MutableList<Long>>) : Matrix<Long>(input) {

    var hasWon: Boolean = false

    fun sum(): Long {
        return allElements().filter { it >= 0 }.sum()
    }

    fun remove(number: Long) {
        for (x in 0 until width()) {
            for (y in 0 until height()) {
                if (get(x, y) == number) {
                    set(x, y, -1)
                }
            }
        }
    }

    fun hasWon(): Boolean {
        if (hasWon) return false
        val width = width()
        val height = height()

        for (column in getColumns()) {
            if (column.sum() == -1 * width.toLong()) {
                hasWon = true
            }
        }
        for (row in getRows()) {
            if (row.sum() == -1 * height.toLong()) {
                hasWon = true
            }
        }
        return hasWon
    }

}

fun parse(lines: List<String>): BingoBoard {
    val numbers = mutableListOf<MutableList<Long>>()
    for (line in lines) {
        numbers.add(readLineToLong(line, " "))
    }
    return BingoBoard(numbers)
}

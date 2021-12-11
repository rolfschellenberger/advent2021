package com.rolf

import java.io.File

const val BASE_PATH = "src/main/resources"

fun readLines(name: String) = File(BASE_PATH, name).readLines()

fun readLongs(name: String) = readLines(name).map { it.toLong() }

fun readLine(line: String, delimiter: String): MutableList<String> {
    return line.trim().replace("  ", " ").split(delimiter).toMutableList()
}

fun readLineToLong(line: String, delimiter: String): MutableList<Long> {
    return line.trim().replace("  ", " ").split(delimiter).map { it.toLong() }.toMutableList()
}

fun readLineToInt(line: String, delimiter: String): MutableList<Int> {
    return line.trim().replace("  ", " ").split(delimiter).map { it.toInt() }.toMutableList()
}

fun groupLines(lines: List<String>, match: String): List<List<String>> {
    val groups = mutableListOf<MutableList<String>>()
    var group = mutableListOf<String>()

    for (line in lines) {
        if (line == match) {
            groups.add(group)
            group = mutableListOf()
        } else {
            group.add(line)
        }
    }
    groups.add(group)
    return groups
}

fun readMatrixString(lines: List<String>, delimiter: String): Matrix<String> {
    val numbers = mutableListOf<MutableList<String>>()
    for (line in lines) {
        numbers.add(readLine(line, delimiter))
    }
    return Matrix(numbers)
}

fun readMatrixLong(lines: List<String>, delimiter: String): Matrix<Long> {
    val numbers = mutableListOf<MutableList<Long>>()
    for (line in lines) {
        numbers.add(readLineToLong(line, delimiter))
    }
    return Matrix(numbers)
}

open class Matrix<T>(private val input: List<MutableList<T>>) {

    fun allElements(): List<T> {
        return input.flatten()
    }

    fun height(): Int {
        return input.size
    }

    fun width(): Int {
        if (height() == 0) return 0
        return input[0].size
    }

    fun getColumns(): List<List<T>> {
        val result = mutableListOf<List<T>>()
        for (x in 0 until width()) {
            result.add(getColumn(x))
        }
        return result
    }

    fun getColumn(x: Int): List<T> {
        val result = mutableListOf<T>()
        for (row in input) {
            result.add(row[x])
        }
        return result
    }

    fun getRows(): List<List<T>> {
        val result = mutableListOf<List<T>>()
        for (y in 0 until height()) {
            result.add(getRow(y))
        }
        return result
    }

    fun getRow(y: Int): List<T> {
        return input[y]
    }

    fun get(x: Int, y: Int): T {
        return input[y][x]
    }

    fun get(point: Point): T {
        return get(point.x, point.y)
    }

    fun set(x: Int, y: Int, value: T) {
        input[y][x] = value
    }

    fun getNeighbours(point: Point): Set<Point> {
        val result = mutableSetOf<Point>()
        if (point.x + 1 < width()) {
            val right = Point(point.x + 1, point.y)
            result.add(right)
        }
        if (point.x - 1 >= 0) {
            val left = Point(point.x - 1, point.y)
            result.add(left)
        }
        if (point.y + 1 < height()) {
            val up = Point(point.x, point.y + 1)
            result.add(up)
        }
        if (point.y - 1 >= 0) {
            val down = Point(point.x, point.y - 1)
            result.add(down)
        }
        return result
    }

    fun getNeighboursDiagonal(point: Point): Set<Point> {
        val result = getNeighbours(point).toMutableSet()
        if (point.x + 1 < width() && point.y + 1 < height()) {
            val right = Point(point.x + 1, point.y + 1)
            result.add(right)
        }
        if (point.x + 1 < width() && point.y - 1 >= 0) {
            val right = Point(point.x + 1, point.y - 1)
            result.add(right)
        }
        if (point.x - 1 >= 0 && point.y + 1 < height()) {
            val right = Point(point.x - 1, point.y + 1)
            result.add(right)
        }
        if (point.x - 1 >= 0 && point.y - 1 >= 0) {
            val right = Point(point.x - 1, point.y - 1)
            result.add(right)
        }

        return result
    }

    fun toString(separatorElement: String, separatorLine: String): String {
        val builder = StringBuilder()
        for (y in 0 until height()) {
            for (x in 0 until width()) {
                val value = get(x, y)
                builder.append(value)
                builder.append(separatorElement)
            }
            builder.append(separatorLine)
        }
        return builder.removeSuffix(separatorLine).toString()
    }
}

data class Point(val x: Int, val y: Int)

open class Box<T>(private val input: Array<Array<Array<T>>>) {

    fun x(): Int {
        return input.size
    }

    fun y(): Int {
        if (x() == 0) return 0
        return input[0].size
    }

    fun z(): Int {
        if (x() == 0) return 0
        if (y() == 0) return 0
        return input[0][0].size
    }

    fun allValues(): List<T> {
        val result = mutableListOf<T>()
        for (x in 0 until x()) {
            for (y in 0 until y()) {
                for (z in 0 until z()) {
                    result.add(get(x, y, z))
                }
            }
        }
        return result
    }

    fun get(x: Int, y: Int, z: Int): T {
        return input[x][y][z]
    }

    fun set(x: Int, y: Int, z: Int, value: T) {
        input[x][y][z] = value
    }
}

data class BinaryNumber(
    val input: String
) {
    fun get(i: Int): String {
        return input[i].toString()
    }

    fun toInt() = Integer.parseInt(input, 2)
}

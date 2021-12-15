package com.rolf.day15

import com.rolf.Matrix
import com.rolf.Point
import com.rolf.readLines
import com.rolf.readLinesToMatrixInt

const val DAY = "15"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val matrix = Matrix(readLinesToMatrixInt(lines, ""))

    println("-- Part 1 --")
    solve1(matrix)
    println("-- Part 2 --")
    val start = System.currentTimeMillis()
    solve2(matrix)
    println("done in ${System.currentTimeMillis() - start} ms")
}

fun solve1(matrix: Matrix<Int>) {
    // Starting at point 0,0 with a cost value of 0, since the start cost is not taken into account
    val costGrid = mapOf(Point(0, 0) to 0)
    println(solveRecursiveMatrix(matrix, costGrid))
}

fun solve2(matrix: Matrix<Int>) {
    // Starting at point 0,0 with a cost value of 0, since the start cost is not taken into account
    val costGrid = mapOf(Point(0, 0) to 0)
    println(solveRecursiveMatrix(growMatrix(matrix, 5), costGrid))
}

private tailrec fun solveRecursiveMatrix(matrix: Matrix<Int>, costGrid: Map<Point, Int>): Int {
    val newCostGrid = mutableListOf<Pair<Point, Int>>()
    for (element in costGrid) {
        val point = element.key
        val cost = element.value
        newCostGrid.add(point to cost)

        val neighbours = matrix.getNeighboursHorizontalVertical(point)
        for (neighbour in neighbours) {
            val currentCost = matrix.get(neighbour)
            val newCost = currentCost + cost
            newCostGrid.add(neighbour to newCost)
        }
    }

    // Group the costs per Point
    val groupedCosts = newCostGrid.groupBy { it.first }
    // Now map all values to one cost: the lowest cost
    val groupedToMinCost = groupedCosts.mapValues { (_, value) -> value.minOf { pair -> pair.second } }

    // When the grid didn't change: take the cost from the bottom right corner of the matrix
    if (costGrid == groupedToMinCost) {
        return costGrid[Point(matrix.width() - 1, matrix.height() - 1)]!!
    }

    return solveRecursiveMatrix(matrix, groupedToMinCost)
}

fun growMatrix(matrix: Matrix<Int>, times: Int): Matrix<Int> {
    // First grow in width
    val matrixWidth = growMatrixWidth(matrix, times)
    // Next grow in height
    return growMatrixHeight(matrixWidth, times)
}

fun growMatrixWidth(matrix: Matrix<Int>, times: Int): Matrix<Int> {
    val originalWidth = matrix.width()
    val originalHeight = matrix.height()

    val newMatrix = Matrix.buildDefault(originalWidth * times, originalHeight, 0)
    for (y in 0 until matrix.height()) {
        for (x in 0 until matrix.width()) {
            val value = matrix.get(x, y)
            for (i in 0 until times) {
                newMatrix.set(x + (i * originalWidth), y, nextValue(value, i))
            }
        }
    }
    return newMatrix
}

fun growMatrixHeight(matrix: Matrix<Int>, times: Int): Matrix<Int> {
    val originalWidth = matrix.width()
    val originalHeight = matrix.height()

    val newMatrix = Matrix.buildDefault(originalWidth, originalHeight * times, 0)
    for (y in 0 until matrix.height()) {
        for (x in 0 until matrix.width()) {
            val value = matrix.get(x, y)
            for (i in 0 until times) {
                newMatrix.set(x, y + (i * originalHeight), nextValue(value, i))
            }
        }
    }
    return newMatrix
}

fun nextValue(input: Int, increment: Int): Int {
    val newValue = input + increment
    if (newValue > 9) {
        return newValue % 9
    }
    return newValue
}

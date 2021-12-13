package com.rolf.day09

import com.rolf.Matrix
import com.rolf.Point
import com.rolf.readLines

const val DAY = "09"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val grid = mutableListOf<MutableList<Int>>()
    for (line in lines) {
        val row = mutableListOf<Int>()
        for (char in line) {
            val value = (char + "").toInt()
            row.add(value)
        }
        grid.add(row)
    }

    val floor = Floor(grid)

    println("-- Part 1 --")
    solve1(floor)
    println("-- Part 2 --")
    solve2(floor)
}

fun solve1(floor: Floor) {
    val lowest = mutableListOf<Int>()
    for (y in 0 until floor.height()) {
        for (x in 0 until floor.width()) {
            val value = floor.get(x, y)

            var lowestNeighbour = Int.MAX_VALUE
            if (x + 1 < floor.width()) {
                val right = floor.get(x + 1, y)
                lowestNeighbour = minOf(lowestNeighbour, right)
            }
            if (x - 1 >= 0) {
                val left = floor.get(x - 1, y)
                lowestNeighbour = minOf(lowestNeighbour, left)
            }
            if (y + 1 < floor.height()) {
                val up = floor.get(x, y + 1)
                lowestNeighbour = minOf(lowestNeighbour, up)
            }
            if (y - 1 >= 0) {
                val down = floor.get(x, y - 1)
                lowestNeighbour = minOf(lowestNeighbour, down)
            }
            if (value < lowestNeighbour) {
                lowest.add(value + 1)
            }
        }
    }
    println(lowest.sum())
}

fun solve2(floor: Floor) {
    val basins = mutableSetOf<Basin>()
    for (y in 0 until floor.height()) {
        for (x in 0 until floor.width()) {
            val basin = floor.getBasin(x, y)
            if (basin.size() > 0) {
                basins.add(basin)
            }
        }
    }

    val sizes = basins.map { it.size() }.sortedDescending().subList(0, 3).reduce(Int::times)
    println(sizes)
}

class Floor(input: MutableList<MutableList<Int>>) : Matrix<Int>(input) {

    fun getBasin(x: Int, y: Int): Basin {
        val startPoint = Point(x, y)
        val locations = mutableSetOf<Point>()
        iterateBasinPoints(startPoint, locations)
        return Basin(locations)
    }

    private fun iterateBasinPoints(point: Point, locations: MutableSet<Point>) {
        // Not part of a basin, return
        if (get(point) == 9) {
            return
        }

        // Already visited?
        if (locations.contains(point)) {
            return
        }
        locations.add(point)

        // Otherwise visit neighbours
        val neighbours = getNeighboursHorizontalVertical(point)
        for (neighbour in neighbours) {
            iterateBasinPoints(neighbour, locations)
        }
    }
}

data class Basin(val locations: Set<Point>) {

    fun size(): Int {
        return locations.size
    }
}

package com.rolf.day11

import com.rolf.Point
import com.rolf.readLines

const val DAY = "11"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve1(buildCave(lines))
    println("-- Part 2 --")
    solve2(buildCave(lines))
}

fun buildCave(lines: List<String>): Cave {
    val rows = mutableListOf<MutableList<Int>>()
    for (line in lines) {
        val row = mutableListOf<Int>()
        for (char in line) {
            row.add((char.toString()).toInt())
        }
        rows.add(row)
    }
    return Cave(rows)
}

fun solve1(cave: Cave) {
    var flashes = 0L
    for (i in 0 until 100) {
        flashes += step(cave)
    }
    println(cave)
    println(flashes)
}

fun solve2(cave: Cave) {
    val caveSize = cave.allElements().size.toLong()
    for (i in 0 until 1000) {
        val f = step(cave)
        // First moment they all flashed
        if (f == caveSize) {
            println(cave)
            println(i + 1)
            return
        }
    }
}

fun step(cave: Cave): Long {
    // First, the energy level of each octopus increases by 1.
    for (y in 0 until cave.height()) {
        for (x in 0 until cave.width()) {
            cave.increase(x, y)
        }
    }

    // Then, any octopus with an energy level greater than 9 flashes.
    // This increases the energy level of all adjacent octopuses by 1, including octopuses that are diagonally adjacent.
    // If this causes an octopus to have an energy level greater than 9, it also flashes.
    // This process continues as long as new octopuses keep having their energy level increased
    // beyond 9. (An octopus can only flash at most once per step.)
    var flashes = 0L
    for (y in 0 until cave.height()) {
        for (x in 0 until cave.width()) {
            flashes += flash(cave, x, y)
        }
    }
    return flashes
}

fun flash(cave: Cave, x: Int, y: Int): Long {
    var flashes = 0L
    val octopusValue = cave.get(x, y)
    if (octopusValue > 9) {
        flashes += 1L
        cave.set(x, y, 0)
        val neighbours = cave.getNeighboursDiagonal(Point(x, y))
        for (neighbour in neighbours) {
            // Only increase octopus that didn't flash this step
            if (cave.get(neighbour) != 0) {
                cave.increase(neighbour)
            }
            flashes += flash(cave, neighbour.x, neighbour.y)
        }
    }
    return flashes
}

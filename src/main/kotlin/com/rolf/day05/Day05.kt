package com.rolf.day05

import com.rolf.readLines

const val DAY = "05"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

fun getPipes(lines: List<String>): List<Pipe> {
    return lines
        .map { Pipe(parseStart(it), parseEnd(it)) }
}

fun parseStart(line: String): Point {
    return parsePoint(line.split(" -> ")[0])
}

fun parseEnd(line: String): Point {
    return parsePoint(line.split(" -> ")[1])
}

fun parsePoint(input: String): Point {
    val (x, y) = input.split(",").map { it.toInt() }
    return Point(x, y)
}

fun getOceanFloor(pipes: List<Pipe>): OceanFloor {
    val maxX = pipes.map { maxOf(it.start.x, it.end.x) }
        .maxOrNull()!!
    val maxY = pipes.map { maxOf(it.start.y, it.end.y) }
        .maxOrNull()!!

    val grid = mutableListOf<MutableList<Int>>()

    for (y in 0 until maxY + 1) {
        val row = mutableListOf<Int>()
        for (x in 0 until maxX + 1) {
            row.add(0)
        }
        grid.add(row)
    }

    val oceanFloor = OceanFloor(grid)
    pipes.forEach { oceanFloor.place(it) }
    return oceanFloor
}

fun solve1(lines: List<String>) {
    val pipes = getPipes(lines)
        .filter { it.start.x == it.end.x || it.start.y == it.end.y }
    val oceanFloor = getOceanFloor(pipes)

    println(oceanFloor.allElements().filter { it > 1 }.count())
}

fun solve2(lines: List<String>) {
    val pipes = getPipes(lines)
    val oceanFloor = getOceanFloor(pipes)

    println(oceanFloor.allElements().filter { it > 1 }.count())
}

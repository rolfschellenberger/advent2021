package com.rolf.day25

import com.rolf.readLines

const val DAY = "25"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
}

fun solve1(lines: List<String>) {
    val floor = parseFloor(lines)
    val steps = floor.move()
    println(steps)
}

private fun parseFloor(lines: List<String>): Floor {
    val rows = mutableListOf<MutableList<String>>()
    for (line in lines) {
        val row = mutableListOf<String>()
        for (char in line) {
            row.add(char.toString())
        }
        rows.add(row)
    }
    return Floor(rows)
}

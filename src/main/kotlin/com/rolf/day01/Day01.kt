package com.rolf.day01

import com.rolf.readLongs

const val DAY = "01"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLongs("$DAY.txt")

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

fun solve1(lines: List<Long>) {
    var increases = 0
    var previous = Long.MAX_VALUE
    for (value in lines) {
        if (value > previous) {
            increases++
        }
        previous = value
    }
    println(increases)
}

fun solve2(lines: List<Long>) {
    var increases = 0
    var previous = Long.MAX_VALUE
    for (i in lines.indices) {
        val value = sum(lines, i, 3)
        if (value > previous) {
            increases++
        }
        previous = value
    }
    println(increases)
}

fun sum(lines: List<Long>, index: Int, windowSize: Int): Long {
    if (index + windowSize > lines.size) {
        return -1
    }
    var sum: Long = 0
    for (i in index until index + windowSize) {
        sum += lines[i]
    }
    return sum
}

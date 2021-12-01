package com.rolf.dayXX

import com.rolf.readLongs

const val DAY = "02"

fun main(args: Array<String>) {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLongs("$DAY.txt");

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

fun solve1(lines: List<Long>) {
}

fun solve2(lines: List<Long>) {
}
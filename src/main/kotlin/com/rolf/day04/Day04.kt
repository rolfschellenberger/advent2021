package com.rolf.day04

import com.rolf.readLines
import com.rolf.readLongs

const val DAY = "04"

fun main(args: Array<String>) {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt");
    val longs = readLongs("$DAY.txt");

    println("-- Part 1 --")
    solve1(lines, longs)
    println("-- Part 2 --")
    solve2(lines, longs)
}

fun solve1(lines: List<String>, longs: List<Long>) {
}

fun solve2(lines: List<String>, longs: List<Long>) {
}

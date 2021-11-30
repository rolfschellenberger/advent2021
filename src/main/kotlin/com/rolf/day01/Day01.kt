package com.rolf.day01

import com.rolf.readLines

const val DAY = "01"

fun main(args: Array<String>) {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt");

    println("-- Part 1 --")
    solve1(lines)
    println("-- Part 2 --")
    solve2(lines)
}

fun solve1(lines: List<String>) {
    val a = MyObject("test")
    println(a)
}

fun solve2(lines: List<String>) {
}

package com.rolf.day02

import com.rolf.readLines

const val DAY = "02"

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

fun solve1(lines: List<String>) {
    var horizontal: Long = 0
    var depth: Long = 0
    for (line in lines) {
        val elements = line.split(" ")
        val commando = elements[0]
        val value = elements[1].toLong()
        when (commando) {
            "forward" -> horizontal += value
            "up" -> depth -= value
            "down" -> depth += value
        }
    }
    println(horizontal * depth)
}

fun solve2(lines: List<String>) {
    var horizontal: Long = 0
    var depth: Long = 0
    var aim: Long = 0
    for (line in lines) {
        val elements = line.split(" ")
        val commando = elements[0]
        val value = elements[1].toLong()
        when (commando) {
            "forward" -> {
                horizontal += value
                depth += aim * value
            }
            "up" -> aim -= value
            "down" -> aim += value
        }
    }
    println(horizontal * depth)
}

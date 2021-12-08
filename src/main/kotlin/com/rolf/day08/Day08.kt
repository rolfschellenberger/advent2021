package com.rolf.day08

import com.rolf.readLines

const val DAY = "08"

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
    var count = 0
    for (line in lines) {
        val last = line.split(" | ")[1]
        val digits = last.split(" ")
        for (digit in digits) {
            if (digit.length == 2 ||
                digit.length == 4 ||
                digit.length == 3 ||
                digit.length == 7
            ) {
                count++
            }
        }
    }
    println(count)
}

fun solve2(lines: List<String>) {
    var sum = 0L
    for (line in lines) {
        val (input, output) = line.split(" | ")
        val digitCollection = determineDigitCollection(input)
        sum += digitCollection.getNumber(output)
    }
    println(sum)
}

fun determineDigitCollection(first: String): DigitCollection {
    val inputs = first.split(" ")
    return DigitCollection(inputs.map { Digit(it, null) })
}

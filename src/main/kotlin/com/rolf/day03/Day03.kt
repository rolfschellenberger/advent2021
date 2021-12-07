package com.rolf.day03

import com.rolf.BinaryNumber
import com.rolf.readLines
import kotlin.math.roundToInt

const val DAY = "03"

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
    val list = mutableListOf<MutableList<Int>>()
    var first = true
    for (line in lines) {
        if (first) {
            for (i in line.indices) {
                list.add(mutableListOf())
            }
            first = false
        }
        for (i in line.indices) {
            val char = Integer.parseInt(line[i] + "")
            list[i].add(char)
        }
    }

    var mostCommonString = ""
    var leastCommonString = ""
    for (s in list) {
        val mostCommon = s.average().roundToInt()
        val leastCommon = 1 - mostCommon

        mostCommonString += mostCommon
        leastCommonString += leastCommon
    }

    val gamma = Integer.parseInt(mostCommonString, 2)
    val epsilon = Integer.parseInt(leastCommonString, 2)
    println(gamma * epsilon)
}

fun solve2(lines: List<String>) {
    val binaryCollection = BinaryCollection()
    val binaryCollection2 = BinaryCollection()
    var lineLength = 0
    for (line in lines) {
        lineLength = line.length
        binaryCollection.add(BinaryNumber(line))
        binaryCollection2.add(BinaryNumber(line))
    }
    for (i in 0 until lineLength) {
        binaryCollection.filterMostCommon(i)
        binaryCollection2.filterLeastCommon(i)
    }

    val oxygen = binaryCollection.elements[0].toInt()
    val scrubber = binaryCollection2.elements[0].toInt()
    println(oxygen * scrubber)
}

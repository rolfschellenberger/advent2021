package com.rolf.day03

import com.rolf.readLines
import com.rolf.readLongs
import kotlin.math.roundToInt

const val DAY = "03"

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
        val leastCommon = 1 - mostCommon;

        mostCommonString += mostCommon
        leastCommonString += leastCommon
    }
    println(mostCommonString)
    println(leastCommonString)

    val gamma = Integer.parseInt(mostCommonString, 2);
    val epsilon = Integer.parseInt(leastCommonString, 2);
    println(gamma * epsilon)
}

fun solve2(lines: List<String>, longs: List<Long>) {
    val binaryCollection = BinaryCollection()
    val binaryCollection2 = BinaryCollection()
    var lineLength = 0
    for (line in lines) {
        lineLength = line.length
        binaryCollection.add(BinaryNumber(line))
        binaryCollection2.add(BinaryNumber(line))
    }
    for (i in 0 until lineLength) {
        binaryCollection.filterMostCommon(i);
        binaryCollection2.filterLeastCommon(i);
    }
    println(binaryCollection)
    println(binaryCollection2)

    val oxygen = Integer.parseInt(binaryCollection.elements[0].input, 2);
    val scrubber = Integer.parseInt(binaryCollection2.elements[0].input, 2);
    println(oxygen * scrubber)
}

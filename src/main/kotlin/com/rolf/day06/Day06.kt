package com.rolf.day06

import com.rolf.readLineToLong
import com.rolf.readLines

const val DAY = "06"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLineToLong(readLines("$DAY.txt")[0], ",").map { it.toInt() }

    println("-- Part 1 --")
    solve1(lines.toMutableList())
    println("-- Part 2 --")
    solve2(lines.toMutableList())
}

fun solve1(longs: MutableList<Int>) {
    // Should work too!
    countDays(longs.toMutableList(), 80)

    // Brute force works till about 200 days....
    for (i in 0 until 80) {
        val new = mutableListOf<Int>()
        for (index in longs.indices) {
            if (longs[index] == 0) {
                longs[index] = 6
                new.add(8)
            } else {
                longs[index]--
            }
        }
        longs.addAll(new)
    }
    println(longs.size)
}

fun solve2(longs: MutableList<Int>) {
    countDays(longs, 256)
}

fun countDays(longs: MutableList<Int>, days: Int) {
    // Group the fish per 'days', since they all show the same behaviour of reproducing
    val fishPerDay: MutableMap<Int, Long> = mutableMapOf()

    // Make sure every day exists with the fish count
    for (age in 0..8) {
        fishPerDay[age] = longs.filter { it == age }.count().toLong()
    }

    for (day in 0 until days) {
        // Fish on day 0 will produce offspring (same amount as these fish)
        val offspringAmount = fishPerDay[0]!!

        // Lower the day count for all other fish
        for (age in 0..7) {
            fishPerDay[age] = fishPerDay[age + 1]!!
        }
        // Create offspring at day 8
        fishPerDay[8] = offspringAmount

        // Place the fish that produced offspring back on day 6
        fishPerDay[6] = fishPerDay[6]!! + offspringAmount
    }
    println(fishPerDay.values.sum())
}

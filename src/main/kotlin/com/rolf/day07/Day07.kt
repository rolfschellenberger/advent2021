package com.rolf.day07

import com.rolf.readLineToInt
import com.rolf.readLines
import kotlin.math.abs

const val DAY = "07"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val positions = readLineToInt(lines[0], ",")

    println("-- Part 1 --")
    solve1(positions)
    println("-- Part 2 --")
    solve2(positions)
}

fun solve1(positions: List<Int>) {
    val fuelUsages = calculateAllFuelUsage(positions, ::calculateFuelUsage)
    println(fuelUsages.minOrNull())
}

fun solve2(positions: List<Int>) {
    val fuelUsages = calculateAllFuelUsage(positions, ::calculateFuelUsage2)
    println(fuelUsages.minOrNull())
}

fun calculateAllFuelUsage(
    positions: List<Int>,
    calculate: (toPosition: Int, positions: List<Int>) -> Long
): List<Long> {
    val result = mutableListOf<Long>()
    for (position in positions.minOrNull()!!..positions.maxOrNull()!!) {
        result.add(calculate(position, positions))
    }
    return result
}

fun calculateFuelUsage(toPosition: Int, positions: List<Int>): Long {
    var fuel = 0L
    for (position in positions) {
        fuel += abs(position - toPosition)
    }
    return fuel
}

fun calculateFuelUsage2(toPosition: Int, positions: List<Int>): Long {
    var fuel = 0L
    for (position in positions) {
        val steps = abs(position - toPosition)
        for (step in 1..steps) {
            fuel += step
        }
    }
    return fuel
}

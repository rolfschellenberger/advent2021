package com.rolf.day23

import com.rolf.readLines

const val DAY = "23"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")

    println("-- Part 2 --")
    solve(readLines("$DAY.txt"))
}

fun parseRooms(lines: List<String>): List<Room> {
    val rooms = mutableMapOf<Int, MutableList<String>>()
    // Skip first 2 rows
    for (i in 2 until lines.size - 1) {
        // Now skip first and last character on the line to know the room index
        val line = lines[i].substring(1, lines[i].length - 1)

        for ((index, char) in line.withIndex()) {
            if (char.isLetter()) {
                val room = rooms.getOrPut(index) { mutableListOf() }
                room.add(char.toString())
            }
        }
    }

    val result = mutableListOf<Room>()
    var resultValue = 'A'.code
    for ((index, values) in rooms) {
        result.add(Room(index, values, values.size, Char(resultValue).toString()))
        resultValue++
    }
    return result
}

fun solve(lines: List<String>, maxMoves: Int = Integer.MAX_VALUE) {
    val rooms = parseRooms(lines)
    val hallway = mutableListOf<String>()
    for (i in 0 until (2 * rooms.size + 3)) {
        hallway.add(Burrow.EMPTY)
    }
    val burrow = Burrow(rooms, hallway)

    val steps = burrow.solve(maxMoves)
    var lowestScore = Integer.MAX_VALUE
    var lowestPath = steps.first()
    for (step in steps) {
        val lastState = step.last()
        if (lastState.isDone()) {
            val score = step.map { it.cost }.sum()
            if (score < lowestScore) {
                lowestScore = score
                lowestPath = step
            }
        }
    }
    for (step in lowestPath) {
        println(step)
        println()
    }
    println("${steps.size} possible solutions found")
    println("steps: ${lowestPath.size}")
    println("lowest score: $lowestScore")
}

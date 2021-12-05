package com.rolf.day04

import com.rolf.groupLines
import com.rolf.readLineToLong
import com.rolf.readLines

const val DAY = "04"

fun main(args: Array<String>) {
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
    val numbers = readLineToLong(lines[0], ",")
    val bingoBoards = groupLines(lines.subList(2, lines.size), "")
        .map { parse(it) }

    // Play bingo!
    for (number in numbers) {
        bingoBoards.forEach { it.remove(number) }

        // Winner?
        bingoBoards.filter { it.hasWon() }.forEach {
            println(number)
            println(it.sum())
            println(it.sum() * number)
            return
        }
    }
}

fun solve2(lines: List<String>) {
    val numbers = readLineToLong(lines[0], ",")
    val bingoBoards = groupLines(lines.subList(2, lines.size), "")
        .map { parse(it) }

    // Play bingo!
    var winners = 0
    for (number in numbers) {
        bingoBoards.forEach { it.remove(number) }

        // Winner?
        for (bingoBoard in bingoBoards) {
            if (bingoBoard.hasWon()) {
                winners++
                if (winners == bingoBoards.size) {
                    println(number)
                    println(bingoBoard.sum())
                    println(bingoBoard.sum() * number)
                    return
                }
            }
        }
    }
}

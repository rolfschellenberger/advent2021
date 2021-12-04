package com.rolf.day04

import com.rolf.groupLines
import com.rolf.readLineToLong
import com.rolf.readLines

const val DAY = "04"

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
    val lineGroups = groupLines(lines.subList(2, lines.size), "")

    val bingoBoards: MutableList<BingoBoard> = mutableListOf()
    for (boardLines in lineGroups) {
        bingoBoards.add(parse(boardLines))
    }

    // Play bingo!
    for (number in readLineToLong(lines[0], ",")) {
        for (bingoBoard in bingoBoards) {
            bingoBoard.remove(number)
        }

        // Winner?
        for (bingoBoard in bingoBoards) {
            if (bingoBoard.hasWon()) {
                println(number)
                println(bingoBoard.sum())
                println(bingoBoard.sum() * number)
                return
            }
        }
    }
}

fun solve2(lines: List<String>) {
    val lineGroups = groupLines(lines.subList(2, lines.size), "")

    val bingoBoards: MutableList<BingoBoard> = mutableListOf()
    for (boardLines in lineGroups) {
        bingoBoards.add(parse(boardLines))
    }

    // Play bingo!
    var winners = 0
    for (number in readLineToLong(lines[0], ",")) {
        for (bingoBoard in bingoBoards) {
            bingoBoard.remove(number)
        }

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

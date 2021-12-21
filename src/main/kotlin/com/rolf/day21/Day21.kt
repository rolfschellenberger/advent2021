package com.rolf.day21

import com.rolf.readLines

const val DAY = "21"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val (player1Position, player2Position) = parsePositions(lines)

    println("-- Part 1 --")
    solve1(player1Position, player2Position)

    println("-- Part 2 --")
    solve2(player1Position, player2Position)
}

fun parsePositions(lines: List<String>): List<Int> {
    return listOf(
        lines[0].split(" ").last().toInt(),
        lines[1].split(" ").last().toInt()
    )
}

fun solve1(player1Position: Int, player2Position: Int) {
    val dice = DeterministicDice()
    val player1 = Player("1", player1Position)
    val player2 = Player("2", player2Position)
    while (true) {
        player1.play(dice)
        if (player1.score >= 1000) {
            println(player2.score * dice.rolls)
            return
        }

        player2.play(dice)
        if (player2.score >= 1000) {
            println(player1.score * dice.rolls)
            return
        }
    }
}

fun solve2(player1Position: Int, player2Position: Int) {
    val wins = move(player1Position, player2Position)
    println(maxOf(wins.first, wins.second))
}

// Cache input of function to number of wins for each of the players
val cache = mutableMapOf<String, Pair<Long, Long>>()

fun move(
    positionPlayer1: Int,
    positionPlayer2: Int,
    scorePlayer1: Int = 0,
    scorePlayer2: Int = 0,
    activePlayer: Int = 1,
    maxScore: Int = 21
): Pair<Long, Long> {
    val key = "$positionPlayer1-$positionPlayer2-$scorePlayer1-$scorePlayer2-$activePlayer"
    if (cache.containsKey(key)) {
        return cache[key]!!
    }

    var player1Wins = 0L
    var player2Wins = 0L
    // Throw 3 dice
    for (dice1 in 1..3) {
        for (dice2 in 1..3) {
            for (dice3 in 1..3) {
                when (activePlayer) {
                    1 -> {
                        var position = positionPlayer1 + dice1 + dice2 + dice3
                        if (position > 10) {
                            position %= 10
                        }
                        val score = scorePlayer1 + position
                        if (score >= maxScore) {
                            player1Wins++
                        } else {
                            val subWins = move(position, positionPlayer2, score, scorePlayer2, 2)
                            player1Wins += subWins.first
                            player2Wins += subWins.second
                        }
                    }
                    2 -> {
                        var position = positionPlayer2 + dice1 + dice2 + dice3
                        if (position > 10) {
                            position %= 10
                        }
                        val score = scorePlayer2 + position
                        if (score >= maxScore) {
                            player2Wins++
                        } else {
                            val subWins = move(positionPlayer1, position, scorePlayer1, score, 1)
                            player1Wins += subWins.first
                            player2Wins += subWins.second
                        }
                    }
                }
            }
        }
    }
    val result = player1Wins to player2Wins
    cache[key] = result
    return result
}

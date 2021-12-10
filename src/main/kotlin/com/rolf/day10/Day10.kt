package com.rolf.day10

import com.rolf.readLines
import java.util.*

const val DAY = "10"
val charMap = mapOf(
    Pair('(', ')'),
    Pair('[', ']'),
    Pair('{', '}'),
    Pair('<', '>')
)
val part1Score = mapOf(
    Pair(')', 3L),
    Pair(']', 57L),
    Pair('}', 1197L),
    Pair('>', 25137L)
)
val part2Score = mapOf(
    Pair(')', 1L),
    Pair(']', 2L),
    Pair('}', 3L),
    Pair('>', 4L)
)

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
    val sum = lines.mapNotNull { getWrongChar(it) }
        .map { getWrongCharScore(it) }
        .sum()
    println(sum)
}

fun getWrongChar(line: String): Char? {
    val stack = Stack<Char>()
    for (char in line) {
        if (isOpenChar(char)) {
            stack.push(char)
        } else {
            val expected = getCloseChar(stack.pop())
            if (char != expected) {
//                println("Expected ${expected}, but found ${char} instead.")
                return char
            }
        }
    }
    return null
}

fun isOpenChar(char: Char): Boolean {
    return charMap.containsKey(char)
}

fun getCloseChar(char: Char): Char {
    return charMap[char]!!
}

fun getWrongCharScore(char: Char): Long {
    return part1Score.getOrDefault(char, 0L)
}

fun solve2(lines: List<String>) {
    val scores = lines.filter { getWrongChar(it) == null }
        .map { getMissingChars(it) }
        .map { getMissingCharsScore(it) }
        .sorted()
    println(scores[scores.size / 2])
}

fun getMissingChars(line: String): List<Char> {
    val stack = Stack<Char>()
    for (char in line) {
        if (isOpenChar(char)) {
            stack.push(char)
        } else {
            stack.pop()
        }
    }

    // Inverse stack
    val missingChars = mutableListOf<Char>()
    while (!stack.isEmpty()) {
        val openChar = stack.pop()
        missingChars.add(getCloseChar(openChar))
    }
    return missingChars
}

fun getMissingCharsScore(missingChars: List<Char>): Long {
    var sum = 0L
    for (char in missingChars) {
        sum *= 5L
        sum += part2Score.getOrDefault(char, 0L)
    }
    return sum
}

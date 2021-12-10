package com.rolf.day10

import com.rolf.readLines
import java.util.*

const val DAY = "10"

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
        when (char) {
            '(', '[', '{', '<' -> stack.push(char)
            ')' -> {
                if (stack.pop() != '(') {
                    return char
                }
            }
            ']' -> {
                if (stack.pop() != '[') {
                    return char
                }
            }
            '}' -> {
                if (stack.pop() != '{') {
                    return char
                }
            }
            '>' -> {
                if (stack.pop() != '<') {
                    return char
                }
            }
        }
    }
    return null
}

fun getWrongCharScore(char: Char): Long {
    return when (char) {
        ')' -> 3L
        ']' -> 57L
        '}' -> 1197L
        '>' -> 25137L
        else -> 0L
    }
}

fun solve2(lines: List<String>) {
    val scores = lines.filter { isCorrectLine(it) }
        .map { getMissingChars(it) }
        .map { getScore(it) }
        .sorted()
    println(scores[scores.size / 2])
}

fun isCorrectLine(line: String): Boolean {
    val stack = Stack<Char>()
    for (char in line) {
        when (char) {
            '(', '[', '{', '<' -> stack.push(char)
            ')' -> {
                if (stack.pop() != '(') {
                    return false
                }
            }
            ']' -> {
                if (stack.pop() != '[') {
                    return false
                }
            }
            '}' -> {
                if (stack.pop() != '{') {
                    return false
                }
            }
            '>' -> {
                if (stack.pop() != '<') {
                    return false
                }
            }
        }
    }
    return true
}

fun getMissingChars(line: String): List<Char> {
    val result = mutableListOf<Char>()
    val stack = Stack<Char>()
    for (char in line) {
        when (char) {
            '(', '[', '{', '<' -> stack.push(char)
            ')', ']', '}', '>' -> stack.pop()
        }
    }

    // Inverse stack
    while (!stack.isEmpty()) {
        when (stack.pop()) {
            '(' -> result.add(')')
            '[' -> result.add(']')
            '{' -> result.add('}')
            '<' -> result.add('>')
        }
    }
    return result
}

fun getScore(missingChars: List<Char>): Long {
    var sum = 0L
    for (char in missingChars) {
        sum *= 5L
        when (char) {
            ')' -> sum += 1L
            ']' -> sum += 2L
            '}' -> sum += 3L
            '>' -> sum += 4L
        }
    }
    return sum
}

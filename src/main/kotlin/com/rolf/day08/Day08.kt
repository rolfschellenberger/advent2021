package com.rolf.day08

import com.rolf.readLines

const val DAY = "08"

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
    var count = 0
    for (line in lines) {
        val last = line.split(" | ")[1]
        val digits = last.split(" ")
        for (digit in digits) {
            if (digit.length == 2 ||
                digit.length == 4 ||
                digit.length == 3 ||
                digit.length == 7
            ) {
                count++
            }
        }
    }
    println(count)
}

fun solve2(lines: List<String>) {
    // 1 = 2
    // 2 = 5
    // 3 = 5
    // 4 = 4
    // 5 = 5
    // 6 = 6
    // 7 = 3
    // 8 = 7
    // 9 = 6
    // 0 = 6

    // 2 = 1
    // 3 = 7
    // 4 = 4
    // 5 = 2,3,5
    // 6 = 6,9,0
    // 7 = 8

    // vind 1,4,7 en 8
    // 7 zit in 3, 9 en 0 -> element van 5 is dan de 3
    // 4 zit in 9
    // lengte 6 waar 7 ook nog inpast is dan de 0
    // laatste van lengte 6 is dan de 6
    // 2 en 5 nog over
    // ontbrekende stuk van 9 = e
    // welke e heeft is de 2
    // laatste de 5

    var sum = 0L
    for (line in lines) {
        val (first, last) = line.split(" | ")
        val digitCollection = handleFirst(first)
        sum += solve(last, digitCollection)
    }
    println(sum)
}

fun handleFirst(first: String): DigitCollection {
    val inputs = first.split(" ")
    val digitCollection = DigitCollection()
    for (input in inputs) {
        val digit = Digit(input)
        digitCollection.add(digit)
    }
    println(digitCollection)
    println(digitCollection.isDone())
    digitCollection.determine()
    println(digitCollection.isDone())

    return digitCollection
}

fun solve(last: String, digitCollection: DigitCollection): Long {
    var output = ""
    for (n in last.split(" ")) {
        for (digit in digitCollection.digits) {
            if (digit.containsAll(n)) {
                output += digit.number
            }
        }
    }
    return output.toLong()
}

class DigitCollection {
    val digits: MutableList<Digit> = mutableListOf()
    val length: MutableMap<Int, MutableList<Digit>> = mutableMapOf()
    val done: MutableMap<Int, Digit> = mutableMapOf()

    fun add(digit: Digit) {
        digits.add(digit)
        if (!length.containsKey(digit.input.length)) {
            length[digit.input.length] = mutableListOf()
        }
        length[digit.input.length]!!.add(digit)
    }

    fun determine() {

        for (digit in digits) {
            digit.autoDetect()
            if (digit.isDone()) {
                done[digit.number] = digit
            }
        }

        val one = done[1]!!
        val four = done[4]!!
        val seven = done[7]!!
        val eight = done[8]!!
        println(one)
        println(four)
        println(seven)
        println(eight)

        // 2 sides of 1,4,7 are same
        // 7 - 1 = a
        // 7 zit in 3, 9 en 0 -> element van 5 is dan de 3
        for (digit in length[5]!!) {
            if (!digit.isDone() && digit.contains(seven)) {
                digit.number = 3
                done[digit.number] = digit
                println(digit)
            }
        }

        // 4 zit in 9
        for (digit in digits) {
            if (!digit.isDone() && digit.contains(four)) {
                digit.number = 9
                done[digit.number] = digit
                println(digit)
            }
        }

        // lengte 6 waar 7 ook nog inpast is dan de 0
        for (digit in length[6]!!) {
            if (!digit.isDone() && digit.contains(seven)) {
                digit.number = 0
                done[digit.number] = digit
                println(digit)
            }
        }

        // laatste van lengte 6 is dan de 6
        for (digit in length[6]!!) {
            if (!digit.isDone()) {
                digit.number = 6
                done[digit.number] = digit
                println(digit)
            }
        }

        // 2 en 5 nog over
        // ontbrekende stuk van 9 = e
        // welke e heeft is de 2
        val nine = done[9]!!
        var missing = "wrong"
        for (char in "abcdefg") {
            if (!nine.input.contains(char)) {
                missing = char.toString()
                break
            }
        }
        for (digit in digits) {
            if (!digit.isDone() && digit.input.contains(missing)) {
                digit.number = 2
                done[digit.number] = digit
                println(digit)
            }
        }

        // laatste de 5
        for (digit in digits) {
            if (!digit.isDone()) {
                digit.number = 5
                done[digit.number] = digit
                println(digit)
            }
        }
    }

    fun get(number: Int): Digit? {
        return done[number]
    }

    fun isDone(): Boolean {
        for (digit in digits) {
            if (!digit.isDone()) {
                return false
            }
        }
        return true
    }

    override fun toString(): String {
        return "DigitCollection(digits=$digits)"
    }
}

data class Digit(val input: String) {
    var number: Int = -1

    fun autoDetect() {
        if (input.length == 2) {
            number = 1
        }
        if (input.length == 3) {
            number = 7
        }
        if (input.length == 4) {
            number = 4
        }
        if (input.length == 7) {
            number = 8
        }
    }

    fun isDone(): Boolean {
        return number >= 0
    }

    override fun toString(): String {
        return "Digit(input='$input', number=$number)"
    }

    fun contains(other: Digit): Boolean {
        for (char in other.input) {
            if (!input.contains(char)) {
                return false
            }
        }
        return true
    }

    fun containsAll(last: String): Boolean {
        return last.length == input.length &&
                input.toSortedSet().containsAll(last.toSortedSet())
    }
}

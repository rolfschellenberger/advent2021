package com.rolf.day18

import com.rolf.readLines

const val DAY = "18"

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

fun parse(line: String): Pair {
    // Find , for the outer pair: read [ and ] until we find a , when our counter is at 1
    var counter = 0
    for (i in line.indices) {
        val char = line[i]
        if (char == '[') counter++
        if (char == ']') counter--
        if (char == ',' && counter == 1) {
            // Center found, so parse left and right (remove opening and closing brackets)
            val left = line.substring(1, i)
            val right = line.substring(i + 1, line.length - 1)
            return Pair(parse(left), parse(right))
        }
    }

    // No comma found in the center? We hit a number!
    return Pair(line.toInt())
}

fun solve1(lines: List<String>) {
    var snail: Pair? = null
    for (line in lines) {
        if (snail == null) {
            snail = parse(line)
        } else {
            val next = parse(line)
            snail = Pair(snail, next)
//            println("input:$snail")
            snail.solve()
//            println("output:$snail")
        }
    }
    println(snail!!.magnitude())
}

fun solve2(lines: List<String>) {
    var highestMagnitude = 0
    val combinations = combinations(lines)
    for (combination in combinations) {
        combination.solve()
        highestMagnitude = maxOf(highestMagnitude, combination.magnitude())
    }
    println(highestMagnitude)
}

fun combinations(lines: List<String>): List<Pair> {
    val result = mutableListOf<Pair>()
    for (a in lines.indices) {
        for (b in lines.indices) {
            if (a != b) {
                result.add(Pair(parse(lines[a]), parse(lines[b])))
            }
        }
    }
    return result
}

package com.rolf.day12

import com.rolf.readLines

const val DAY = "12"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val map = parseMap(lines)

    println("-- Part 1 --")
    solve1(map["start"]!!)
    println("-- Part 2 --")
    solve2(map["start"]!!)
}

fun parseMap(lines: List<String>): Map<String, Cave> {
    val map = mutableMapOf<String, Cave>()

    for (line in lines) {
        val (from, to) = line.split("-")
        val fc = map.getOrDefault(from, Cave(from))
        val tc = map.getOrDefault(to, Cave(to))
        fc.addOther(tc)
        tc.addOther(fc)

        map[from] = fc
        map[to] = tc
    }
    return map
}

fun solve1(start: Cave) {
    var sum = 0
    for (node in start.others) {
        sum += walk(node, mutableListOf(start))
    }
    println(sum)
}

fun walk(node: Cave, route: MutableList<Cave>): Int {
    // Reached the end?
    if (node.isEnd()) {
        route.add(node)
        return 1
    }

    // Can only visit small caves once and big caves as many times as needed
    val occurrences = route.filter { it.value == node.value }.count()
    if (node.isSmall() && occurrences == 1) {
        return 0
    }

    var sum = 0
    route.add(node)
    for (other in node.others) {
        sum += walk(other, route.toMutableList())
    }
    return sum
}

fun solve2(start: Cave) {
    var sum = 0
    for (node in start.others) {
        sum += walk2(node, mutableListOf(start))
    }
    println(sum)
}

fun walk2(node: Cave, route: MutableList<Cave>): Int {
    // Reached the end?
    if (node.isEnd()) {
        route.add(node)
        return 1
    }

    // Cannot visit start anymore
    if (node.isStart()) {
        return 0
    }

    // Limitations on small caves only
    if (node.isSmall()) {

        // When visited a small cave more than 2 times, abort
        val occurrences = route.filter { it.value == node.value }.count()
        if (occurrences > 1) {
            return 0
        }

        // When visiting a small cave for the second time and there is another cave visited twice, abort
        if (occurrences == 1) {
            var smallVisitTwice = false
            val map = route.groupingBy { it }.eachCount()
            for ((key, value) in map) {
                if (key.isSmall() && value > 1) {
                    smallVisitTwice = true
                }
            }
            if (smallVisitTwice) {
                return 0
            }
        }
    }

    var sum = 0
    route.add(node)
    for (other in node.others) {
        sum += walk2(other, route.toMutableList())
    }
    return sum
}

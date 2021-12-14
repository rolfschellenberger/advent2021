package com.rolf.day14

import com.rolf.groupLines
import com.rolf.readLines

const val DAY = "14"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")

    println("-- Part 1 --")
    solve(lines, 10)
    println("-- Part 2 --")
    solve(lines, 40)
}

fun solve(lines: List<String>, loopCount: Int) {
    val (templates, pairs) = groupLines(lines, "")
    val template = getTemplate(templates)
    val changeMap = createChangeMap(pairs)

    // Keep a map of distinct 2 letter combinations from the original template input
    var map = createCombinationMap(template)

    // Now iterate the map N times
    repeat(loopCount) {
        val newMap = mutableMapOf<String, Long>()
        for ((key, value) in map) {
            val change = change(key, changeMap[key]!!)
            for (c in change) {
                newMap.computeIfAbsent(c) { 0 }
                newMap.computeIfPresent(c) { _, v -> v + value }
            }
        }
        map = newMap
    }

    // Count the number of characters
    val countMap = countCharacters(map, template.last())

    // Max count - min count
    val counts = countMap.values.sorted()
    println(counts.last() - counts.first())
}

fun getTemplate(templates: List<String>): String {
    return templates[0]
}

fun createChangeMap(pairs: List<String>): Map<String, Char> {
    val changeMap = mutableMapOf<String, Char>()
    for (pair in pairs) {
        val (from, to) = pair.split(" -> ")
        changeMap[from] = to[0]
    }
    return changeMap
}

fun createCombinationMap(template: String): MutableMap<String, Long> {
    val map = mutableMapOf<String, Long>()
    for (i in 0 until template.length - 1) {
        val segment = template.substring(i, i + 2)
        val count = map.getOrDefault(segment, 0)
        map[segment] = count + 1
    }
    return map
}

fun change(from: String, to: Char): List<String> {
    val result = mutableListOf<String>()
    result.add("${from[0]}${to}")
    result.add("${to}${from[1]}")
    return result
}

fun countCharacters(map: MutableMap<String, Long>, last: Char): Map<Char, Long> {
    val countMap = mutableMapOf<Char, Long>()
    for (element in map) {
        val char = element.key[0]
        val count = countMap.getOrDefault(char, 0)
        countMap[char] = count + element.value
    }

    // Add the last character of the input, since this letter stayed the last char of the output
    countMap.computeIfAbsent(last) { 1 }
    countMap.computeIfPresent(last) { _, v -> v + 1 }
    return countMap
}

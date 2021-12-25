package com.rolf.day24

import com.rolf.readLines

const val DAY = "24"

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
    solve(lines, false)
}

fun solve2(lines: List<String>) {
    solve(lines, true)
}

/*
Inspired by:

with open('input') as fp:
    lines = fp.read().split('\n')
data = list(zip(
    [int(x.split()[-1]) for x in lines[4::18]],
    [int(x.split()[-1]) for x in lines[5::18]],
    [int(x.split()[-1]) for x in lines[15::18]]))

def recursive(params, order=lambda x: x, z=0, number=()):
    if not params:
        return number if z == 0 else None
    a, b, c = params[0]
    if a == 26:
        if not (1 <= (z%26)+b <= 9): return None
        return recursive(params[1:], order, z//a, number + ((z%26)+b,))
    for i in order(range(1, 10)):
        result = recursive(params[1:], order, z//a*26+i+c, number+(i,))
        if result is not None: return result

print('Part 1:', recursive(data, order=reversed))
print('Part 2:', recursive(data))
 */

fun solve(lines: List<String>, increment: Boolean) {
    // Construct pairs of div, add, add, starting at lines 4,5 and 15, repeating every 18 lines
    val lists = mutableListOf<List<Int>>()
    for (s in listOf(4, 5, 15)) {
        val list = mutableListOf<Int>()
        for (i in s until lines.size step 18) {
            val line = lines[i]
            val value = line.split(" ").last().toInt()
            list.add(value)
        }
        lists.add(list)
    }
    val zipped = lists[0].zip(lists[1]).zip(lists[2]) { (a, b), c -> listOf(a, b, c) }

    val result = recursive(zipped, increment)
    println(result!!.joinToString(""))
}

fun recursive(
    params: List<List<Int>>,
    increment: Boolean = true,
    z: Int = 0,
    number: List<Int> = listOf()
): List<Int>? {
    if (params.isEmpty()) {
        return if (z == 0) number else null
    }
    val (a, b, c) = params[0]
    // Modulo
    if (a == 26) {
        val v = (z % 26) + b
        if (v < 1 || v > 9) return null
        return recursive(params.subList(1, params.size), increment, z / a, number + listOf((z % 26) + b))
    }
    val range = if (increment) 1..9 else 9 downTo 1
    for (i in range) {
        val result = recursive(params.subList(1, params.size), increment, z / a * 26 + i + c, number + listOf(i))
        if (result != null) return result
    }
    return null
}
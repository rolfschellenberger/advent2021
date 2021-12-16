package com.rolf.day16

import com.rolf.readLines
import java.math.BigInteger

const val DAY = "16"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val line = lines[0]
    val binary = hexToBinaryList(line)

    println("-- Part 1 --")
    solve1(InputStream(binary))
    println("-- Part 2 --")
    solve2(InputStream(binary))
}

fun hexToBinaryList(hex: String): List<Char> {
    val sb = StringBuilder()
    for (char in hex) {
        val value = BigInteger(char.toString(), 16).toString(2)
        sb.append(value.padStart(4, '0'))
    }
    return sb.toString().toCharArray().toList()
}

var versionSum = 0L

fun solve1(inputStream: InputStream) {
    inputStream.getValue()
    println(versionSum)
}

fun solve2(inputStream: InputStream) {
    println(inputStream.getValue())
}


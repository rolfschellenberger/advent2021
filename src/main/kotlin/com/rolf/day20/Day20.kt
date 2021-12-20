package com.rolf.day20

import com.rolf.groupLines
import com.rolf.readLines

const val DAY = "20"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val image = parseImage(lines)

    println("-- Part 1 --")
    solve1(image, 2)
    println("-- Part 2 --")
    solve2(image, 50)
}

fun parseImage(lines: List<String>): Image {
    val (algorithms, imageLines) = groupLines(lines, "")
    val algorithm = algorithms[0]

    val rows = mutableListOf<MutableList<String>>()
    for (imageLine in imageLines) {
        val row = mutableListOf<String>()
        for (char in imageLine) {
            row.add(char.toString())
        }
        rows.add(row)
    }
    return Image(algorithm, rows)
}

fun solve1(image: Image, steps: Int) {
    val outputImage = evolve(image, steps)
    println(outputImage.count("#"))
}

fun solve2(image: Image, steps: Int) {
    val outputImage = evolve(image, steps)
    println(outputImage.count("#"))
}

fun evolve(image: Image, steps: Int): Image {
    var outputImage = image.increase(steps)

    for (step in 0 until steps) {
        val newImage = outputImage.copy()
        for (y in 0 until outputImage.height()) {
            for (x in 0 until outputImage.width()) {
                val surrounding = outputImage.getSurrounding(x, y, step)
                val newValue = outputImage.calculateNewValue(surrounding)
                newImage.set(x, y, newValue)
            }
        }
        outputImage = newImage
    }

    return outputImage
}

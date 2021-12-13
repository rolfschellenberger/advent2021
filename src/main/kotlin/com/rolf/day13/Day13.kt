package com.rolf.day13

import com.rolf.groupLines
import com.rolf.readLines

const val DAY = "13"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val (coordinates, folds) = groupLines(lines, "")

    println("-- Part 1 --")
    solve1(parsePaper(coordinates), folds)
    println("-- Part 2 --")
    solve2(parsePaper(coordinates), folds)
}

fun parsePaper(coordinates: List<String>): Paper {
    var maxX = 0
    var maxY = 0
    for (coordinate in coordinates) {
        val (x, y) = coordinate.split(",").map { it.toInt() }
        maxX = maxOf(maxX, x)
        maxY = maxOf(maxY, y)
    }

    val paper = Paper.buildDefault(maxX + 1, maxY + 1, ".")
    for (coordinate in coordinates) {
        val (x, y) = coordinate.split(",").map { it.toInt() }
        paper.set(x, y, "#")
    }
    return paper
}

fun solve1(paper: Paper, folds: List<String>) {
    val fold = folds.first()
    val (direction, value) = fold.split("=")
    if (direction.endsWith("x")) {
        paper.foldX(value.toInt())
    } else {
        paper.foldY(value.toInt())
    }
    println(paper.count())
}

fun solve2(paper: Paper, folds: List<String>) {
    for (fold in folds) {
        val (direction, value) = fold.split("=")
        if (direction.endsWith("x")) {
            paper.foldX(value.toInt())
        } else {
            paper.foldY(value.toInt())
        }
    }
    println(paper.toString().replace('.', ' '))
}

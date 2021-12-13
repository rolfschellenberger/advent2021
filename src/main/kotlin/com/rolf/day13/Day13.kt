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
    val paper = parsePaper(coordinates)

    println("-- Part 1 --")
    solve1(paper, folds)
    println("-- Part 2 --")
    solve2(paper, folds)
}

fun parsePaper(coordinates: List<String>): Paper {
    var maxX = 0
    var maxY = 0
    for (coordinate in coordinates) {
        val (x, y) = coordinate.split(",").map { it.toInt() }
        maxX = maxOf(maxX, x)
        maxY = maxOf(maxY, y)
    }

    val rows = mutableListOf<MutableList<String>>()
    for (y in 0..maxY) {
        val row = mutableListOf<String>()
        for (x in 0..maxX) {
            row.add(".")
        }
        rows.add(row)
    }

    val paper = Paper(rows)
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

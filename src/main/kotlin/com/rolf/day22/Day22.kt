package com.rolf.day22

import com.rolf.readLines

const val DAY = "22"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val steps = parseSteps(lines)

    println("-- Part 1 --")
    solve1(steps)
    println("-- Part 2 --")
    solve2(steps)
}

fun parseSteps(lines: List<String>): List<Step> {
    val steps = mutableListOf<Step>()
    for (line in lines) {
        val (onOff, xyz) = line.split(" ")
        val (x, y, z) = xyz.split(",")
        val xRange = parseRange(x)
        val yRange = parseRange(y)
        val zRange = parseRange(z)
        steps.add(Step(onOff == "on", xRange, yRange, zRange))
    }
    return steps
}

fun parseRange(valueRange: String): IntRange {
    val (_, range) = valueRange.split("=")
    val (from, till) = range.split("..").map { it.toInt() }
    return from..till
}

fun solve1(steps: List<Step>) {
    val space = mutableSetOf<Cube>()
    val allowedRegion = -50..50

    for (step in steps) {
        if (allowedRegion.contains(step.xRange.first) &&
            allowedRegion.contains(step.yRange.first) &&
            allowedRegion.contains(step.zRange.first)
        ) {
            val cubes = step.toCubes()
            if (step.on) {
                space.addAll(cubes)
            } else {
                space.removeAll(cubes)
            }
        }
    }
    println(space.size)
}

fun solve2(steps: List<Step>) {
    // New idea: remove an area from all areas
    // So when a new area is added, remove it first from all existing areas in space
    // When removing an area from an area, see if they overlap. If so:
    // - remove this area from all existing voids
    // - create a new void
    val space = mutableListOf<Area>()
    for (step in steps) {
        val area = step.toArea()
        for (cube in space) {
            cube.remove(area)
        }
        if (step.on) {
            space.add(area)
        }
    }

    // Now calculate the volume of the space
    println(space.map { it.volume() }.sum())
}

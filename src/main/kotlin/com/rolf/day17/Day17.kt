package com.rolf.day17

import com.rolf.Point
import com.rolf.readLines

const val DAY = "17"

fun main() {
    println("+--------+")
    println("| Day $DAY |")
    println("+--------+")
    val lines = readLines("$DAY.txt")
    val line = lines[0]

    val (_, coordinates) = line.split(": ")
    val (xa, ya) = coordinates.split(", ")
    val (_, xrange) = xa.split("=")
    val (_, yrange) = ya.split("=")
    val (xstart, xend) = xrange.split("..").map { it.toInt() }
    val (ystart, yend) = yrange.split("..").map { it.toInt() }

    println("-- Part 1 & 2 --")
    solve(xstart..xend, ystart..yend)
}

fun solve(xRange: IntRange, yRange: IntRange) {

    var highestY = 0
    var successfulFlights = 0
    for (xVelocity in 0..xRange.last) {
        // randomly picked this range
        for (yVelocity in -1000..1000) {
            var step = State(Point(0, 0), xVelocity, yVelocity)

            // Keep track of the highest point we reach during the flight
            var highestPoint = 0
            while (!step.isPassed(xRange, yRange)) {
                // Keep measuring the highest point during the flight
                highestPoint = maxOf(highestPoint, step.point.y)
                if (step.isInArea(xRange, yRange)) {
                    // How many flights made it into the area
                    successfulFlights++
                    highestY = maxOf(highestY, highestPoint)
                    break
                }
                step = step.calculateNextStep()
            }
        }
    }
    println("highest y=$highestY")
    println("successful flights=$successfulFlights")
}

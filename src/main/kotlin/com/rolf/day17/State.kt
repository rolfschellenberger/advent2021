package com.rolf.day17

import com.rolf.Point

data class State(val point: Point, val xVelocity: Int, val yVelocity: Int) {

    fun calculateNextStep(): State {
        val newX = point.x + xVelocity
        val newY = point.y + yVelocity
        val newXVelocity = if (xVelocity > 0) xVelocity - 1 else if (xVelocity < 0) xVelocity + 1 else 0
        val newYVelocity = yVelocity - 1
        return State(Point(newX, newY), newXVelocity, newYVelocity)
    }

    fun isPassed(xRange: IntRange, yRange: IntRange): Boolean {
        // Right (higher) of xend or below (lower) ystart
        return point.x > xRange.last || point.y < yRange.first
    }

    fun isInArea(xRange: IntRange, yRange: IntRange): Boolean {
        return point.x in xRange
                && point.y in yRange
    }
}

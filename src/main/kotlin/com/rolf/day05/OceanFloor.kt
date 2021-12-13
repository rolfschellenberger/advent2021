package com.rolf.day05

import com.rolf.Matrix
import com.rolf.Point
import java.lang.Math.abs

class OceanFloor(input: MutableList<MutableList<Int>>) : Matrix<Int>(input) {
    fun place(pipe: Pipe) {
        val points = getPointsBetween(pipe.start, pipe.end)

        for (point in points) {
            set(point.x, point.y, get(point.x, point.y) + 1)
        }
    }

    private fun getPointsBetween(start: Point, end: Point): List<Point> {
        val points = mutableListOf<Point>()
        val yDelta = if (end.y > start.y) 1 else if (end.y == start.y) 0 else -1
        val xDelta = if (end.x > start.x) 1 else if (end.x == start.x) 0 else -1
        for (i in 0..getRange(start, end)) {
            points.add(Point(start.x + (i * xDelta), start.y + (i * yDelta)))
        }
        return points
    }

    private fun getRange(start: Point, end: Point): Int {
        val xRange = abs(start.x - end.x)
        val yRange = abs(start.y - end.y)
        return maxOf(xRange, yRange)
    }
}
